package com.tuya.hardware.symphony.auditflow.service.audit.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tuya.basic.client.domain.exception.BaseException;
import com.tuya.hardware.symphony.auditflow.model.cmd.AudDeleteCmd;
import com.tuya.hardware.symphony.auditflow.repository.audit.AuditRepository;
import com.tuya.hardware.symphony.auditflow.infrastructure.dataobject.AuditDO;
import com.tuya.hardware.symphony.auditflow.service.audit.IAuditService;
import com.tuya.hardware.symphony.auditflow.model.context.AudContext;
import com.tuya.hardware.symphony.auditflow.model.cmd.AudStatusUpdateCmd;
import com.tuya.hardware.symphony.auditflow.service.audit.dataobject.AuditSMParam;
import com.tuya.hardware.symphony.auditflow.model.dto.ShelfAudStatusDTO;
import com.tuya.hardware.symphony.auditflow.enums.audit.AuditEventEnum;
import com.tuya.hardware.symphony.auditflow.enums.audit.AuditStateEnum;
import com.tuya.sparta.client.v2.IOpenStaffQueryService;
import com.tuya.sparta.client.v2.domain.staff.OpenStaffOrganizationVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/11/12 9:02 下午
 * @description：审核领域服务
 */
@Service
public class AuditService implements IAuditService {

    @Resource
    private AuditRepository auditRepository;
    @Resource
    private AudStateMachine audStateMachine;
    @Resource
    private IOpenStaffQueryService openStaffQueryService;

    /**
     * 更新固件版本开发审核状态
     * @param audStatusUpdateCmd
     */
    @Override
    public void updateAudStatus(AudStatusUpdateCmd audStatusUpdateCmd, String refType, String auditType) {
        if (null == AuditStateEnum.of(audStatusUpdateCmd.getAuditStatus())) {
            throw new BaseException("审核状态值有误");
        }
        AuditSMParam smParam = getAuditStateMachineParam(audStatusUpdateCmd, refType, auditType);
        changeAudState(smParam, audStatusUpdateCmd.getSubmitUser(), audStatusUpdateCmd.getId(), refType, auditType);
    }

    /**
     * 获取
     * @param shelfAudStatusDTO
     * @param refId
     * @param refType
     * @param auditType
     */
    @Override
    public void getAudStatusDTOAud(ShelfAudStatusDTO shelfAudStatusDTO, Long refId, String refType, String auditType) {
        AuditDO audit = auditRepository.getByRefAndType(refId, refType, auditType);
        if (null != audit) {
            shelfAudStatusDTO.setAuditStatus(audit.getAuditStatus());
            if (StringUtils.isNotBlank((audit.getAuditUser()))) {
                OpenStaffOrganizationVO staff = openStaffQueryService.queryStaffByStaffId(audit.getAuditUser());
                shelfAudStatusDTO.setAuditUsername(getWholeName(staff.getTuyaName(), staff.getName()));
            }
            shelfAudStatusDTO.setTraceId(audit.getTraceId());
        }
    }

    @Override
    public void deleteAudit(AudDeleteCmd cmd, String refType, String auditType) {
        auditRepository.deleteByRefAndType(cmd.getId(), refType, auditType);
    }

    private AuditSMParam getAuditStateMachineParam(AudStatusUpdateCmd cmd, String refType, String type) {
        AuditSMParam result = new AuditSMParam();
        AuditDO audit = auditRepository.getByRefAndType(cmd.getId(), refType, type);
        AuditStateEnum startState = AuditStateEnum.NOT_AUDIT;
        if (null != audit) {
            startState = AuditStateEnum.of(audit.getAuditStatus());
        }
        AuditStateEnum endState = AuditStateEnum.of(cmd.getAuditStatus());
        if (startState.equals(endState)) {
            throw new BaseException("当前审核状态已处于：" + "" + endState.getDesc());
        }

        AuditEventEnum event = AuditEventEnum.DEFAULT;
        if (AuditStateEnum.NOT_AUDIT.equals(endState)) {
            event = AuditEventEnum.CANCEL;
        } else if (AuditStateEnum.IN_AUDIT.equals(endState)) {
            event = AuditEventEnum.INIT;
        }

        OpenStaffOrganizationVO staff = null;
        if (AuditStateEnum.IN_AUDIT.getValue().equals(cmd.getAuditStatus())) {
            // 根据staffId获取用户信息
            if (StringUtils.isNotBlank(cmd.getExtension())) {
                JSONObject extension = JSON.parseObject(cmd.getExtension());
                String auditUserStaffId = extension.getString("auditUser");
                if (StringUtils.isNotBlank(auditUserStaffId)) {
                    staff = openStaffQueryService.queryStaffByStaffId(auditUserStaffId);
                }
            }
        }
        result.setStartState(startState)
                .setEndState(endState)
                .setEvent(event)
                .setAuditUser(staff)
                .setAuditKey(cmd.getAuditKey())
                .setAuditContent(cmd.getAuditContent())
                .setAuditReason(cmd.getAuditReason())
                .setCheckKeys(cmd.getCheckKeys());
        return result;
    }

    /**
     * 修改审核状态
     * @param smParam
     * @param submitUser
     * @param refId
     * @param refType
     * @param auditType
     */
    public void changeAudState(AuditSMParam smParam, String submitUser, Long refId, String refType, String auditType) {
        AudContext audContext = new AudContext()
            .setRefId(refId)
            .setRefType(refType)
            .setEventType(smParam.getEvent())
            .setAuditType(auditType)
            .setSubmitUser(submitUser)
            .setAuditKey(smParam.getAuditKey())
            .setAuditContent(smParam.getAuditContent())
            .setAuditReason(smParam.getAuditReason())
                .setOnShelfCheckKeys(smParam.getCheckKeys());
        if (null != smParam.getAuditUser()) {
            audContext.setAuditUserNo(smParam.getAuditUser().getStaffNo());
            audContext.setAuditUserStaffId(smParam.getAuditUser().getStaffId());
        }

        audStateMachine.trigger(smParam.getStartState(), smParam.getEndState(), smParam.getEvent(), audContext);
    }

    private String getWholeName(String tuyaName, String name) {
        StringBuilder result = new StringBuilder()
                .append(tuyaName)
                .append("(")
                .append(name)
                .append(")");
        return result.toString();
    }
}
