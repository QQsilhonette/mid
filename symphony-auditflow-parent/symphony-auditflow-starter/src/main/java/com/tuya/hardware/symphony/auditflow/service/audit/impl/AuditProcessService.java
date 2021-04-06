package com.tuya.hardware.symphony.auditflow.service.audit.impl;

import com.alibaba.fastjson.JSON;
import com.tuya.basic.client.domain.exception.BaseException;
import com.tuya.hardware.symphony.auditflow.repository.audit.AuditRepository;
import com.tuya.hardware.symphony.auditflow.infrastructure.dataobject.AuditDO;
import com.tuya.hardware.symphony.auditflow.service.audit.dataobject.AuditReq;
import com.tuya.hardware.symphony.auditflow.service.audit.dataobject.AuditSMParam;
import com.tuya.hardware.symphony.auditflow.enums.audit.AuditEventEnum;
import com.tuya.hardware.symphony.auditflow.enums.audit.AuditStateEnum;
import com.tuya.hardware.symphony.auditflow.service.base.AbstractProcessService;
import com.tuya.sparta.client.v2.IOpenStaffQueryService;
import com.tuya.sparta.client.v2.domain.staff.OpenStaffOrganizationVO;
import com.tuya.themis.client.constants.ProcessConstant;
import com.tuya.themis.client.enums.AuditStatusEnum;
import com.tuya.themis.client.message.EventMessage;
import com.tuya.themis.client.result.ThemisResult;
import com.tuya.themis.client.service.process.IProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author 旋影
 * @create 2020/10/29
 */
@Service
@Slf4j
public class AuditProcessService extends AbstractProcessService<AuditReq> {

    @Resource
    IProcessService processService;
    @Resource
    private AuditRepository auditRepository;
    @Resource
    private AuditService auditService;
    @Resource
    private IOpenStaffQueryService openStaffQueryService;

    private static final Map<AuditStatusEnum, AuditStateEnum> stateConvMap = new HashMap();
    static {
        stateConvMap.put(AuditStatusEnum.AUDIT_AGREE, AuditStateEnum.PASS);
        stateConvMap.put(AuditStatusEnum.AUDIT_REFUSE, AuditStateEnum.REJECT);
        stateConvMap.put(AuditStatusEnum.AUDIT_CANCEL, AuditStateEnum.NOT_AUDIT);
        stateConvMap.put(AuditStatusEnum.AUDIT_AUDIT, AuditStateEnum.IN_AUDIT);
    }

    @Override
    public String startProcessInstance(AuditReq req, String submitUser) {
        ThemisResult<String> themisResult = this.startProcessOfFwDevelopAudit(req, submitUser);
        if (!themisResult.isSuccess()) {
            log.error("audit failed, error = {}", JSON.toJSONString(themisResult));
            throw new BaseException("审批申请失败");
        }
        return themisResult.getData();
    }

    @Override
    public boolean callBack(EventMessage eventMessage) {
        try {
            AuditDO audit = auditRepository.getByTraceId(eventMessage.getProcessInstanceId());
            if (null == audit) {
                log.error("审核记录不存在");
                return true;
            }

            // 审核状态机入参赋值
            AuditStateEnum startState = AuditStateEnum.of(audit.getAuditStatus());
            AuditStatusEnum auditStatusEnum = eventMessage.getAuditStatus();
            AuditStateEnum endState = stateConvMap.get(auditStatusEnum);
            AuditEventEnum event = AuditEventEnum.DEFAULT;
            if (endState.equals(AuditStateEnum.NOT_AUDIT)) {
                event = AuditEventEnum.AUTO_CANCEL;
            } else if (endState.equals(AuditStateEnum.REJECT)) {
                event = AuditEventEnum.REFUSE;
            }

            auditService.changeAudState(new AuditSMParam(startState, endState, event, null, null, null, null, null),
                    "system", audit.getRefId(), audit.getRefType(), audit.getAuditType());
            log.info("AuditProcessService success : {}", eventMessage.getProcessInstanceId());
        } catch (Exception e) {
            log.error("AuditProcessService consumer failed error = ", e);
        }
        return true;
    }

    private ThemisResult<String> startProcessOfFwDevelopAudit(AuditReq req, String submitUser) {
        Map<String, Object> paramMap = new HashMap<>();
        OpenStaffOrganizationVO staff = openStaffQueryService.queryStaffByStaffId(submitUser);
        if (Objects.isNull(staff)) {
            throw new BaseException("审核人不存在: " + submitUser);
        }
        log.info("startProcessOfFwDevelopAudit staff: {}", staff);
        // 申请人
        paramMap.put(ProcessConstant.PROCESS_VARIABLE_KEY_APPLY_NUM, staff.getStaffNo());
        // 审批人
        if (Objects.nonNull(req.getAuditNo())) {
            paramMap.put("auditor", deleteZero(req.getAuditNo()));
        }
        log.info("startProcess paramMap = {}, auditor={}", paramMap, req.getAuditNo());

        //申请内容
        paramMap.put(ProcessConstant.PROCESS_VARIABLE_KEY_APPLY_CONTENT, req.getAuditContent());

        //申请原因
        paramMap.put(ProcessConstant.PROCESS_VARIABLE_KEY_APPLY_REASON, req.getAuditReason());

        ThemisResult<String> themisResult = processService.startInstanceByKey(req.getAuditKey(), staff.getStaffNo(), paramMap);
        if (Objects.isNull(themisResult) || !themisResult.isSuccess()) {
            throw new BaseException("RefId: " + req.getRefId() + "审批流发起失败");
        }
        return themisResult;
    }

    private String deleteZero(String auditNo) {
        int cur = 0;
        while (auditNo.charAt(cur) == '0') {
            cur++;
        }
        return auditNo.substring(cur);
    }
}
