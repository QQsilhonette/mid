package com.tuya.hardware.symphony.auditflow.service.audit.impl;

import com.alibaba.fastjson.JSONObject;
import com.tuya.hardware.symphony.auditflow.repository.audit.AuditRepository;
import com.tuya.hardware.symphony.auditflow.infrastructure.dataobject.AuditDO;
import com.tuya.hardware.symphony.auditflow.model.context.AudContext;
import com.tuya.hardware.symphony.auditflow.service.audit.dataobject.AuditReq;
import com.tuya.hardware.symphony.auditflow.enums.audit.AuditEventEnum;
import com.tuya.hardware.symphony.auditflow.enums.audit.AuditStateEnum;
import com.tuya.hardware.symphony.auditflow.enums.common.Bool2IntEnum;
import com.tuya.hardware.symphony.auditflow.repository.check.CheckRepository;
import com.tuya.hardware.symphony.auditflow.statemachine.AbstractSingleExternalMachine;
import com.tuya.hardware.symphony.auditflow.statemachine.model.Action;
import com.tuya.hardware.symphony.auditflow.statemachine.model.Condition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/10/27 11:40 上午
 * @description：审核状态机
 */
@Slf4j
@Service
public class AudStateMachine extends AbstractSingleExternalMachine<AuditStateEnum, AuditEventEnum, AudContext> {

    @Resource
    private AuditRepository auditRepository;
    @Resource
    private CheckRepository checkRepository;
    @Resource
    private AuditProcessService auditProcessService;

    private static final String MACHINE_PREFIX = "changeAudState_";
    private static final String CANCEL_COMMENT = "ng后台取消审批流";

    public AuditStateEnum trigger(AuditStateEnum start, AuditStateEnum end, AuditEventEnum event,
                                  AudContext context) {
        StringBuilder machineId = new StringBuilder();
        machineId.append(MACHINE_PREFIX)
                .append(start.getValue())
                .append("_")
                .append(end.getValue())
                .append("_")
                .append(event.getValue())
                .append("_")
                .append(context.getAuditType());
        return this.start(start, end, event, context, checkCondition(), doAction(), machineId.toString());
    }

    private Condition<AudContext> checkCondition() {
        return (ctx) -> {
            return true;
        };
    }

    private Action<AuditStateEnum, AuditEventEnum, AudContext> doAction() {
        return (from, to, event, ctx) -> {
            log.info("AudStateMachine doAction, ctx:{}", JSONObject.toJSONString(ctx));
            AuditDO audit = null;
            switch (event) {
                case INIT:
                    audit = new AuditDO();
                    audit.setRefId(ctx.getRefId())
                            .setRefType(ctx.getRefType())
                            .setAuditType(ctx.getAuditType())
                            .setAuditStatus(AuditStateEnum.IN_AUDIT.getValue())
                            .setAuditUser(ctx.getAuditUserStaffId())
                            .setSubmitUser(ctx.getSubmitUser());
                    // 发起审批流
                    AuditReq auditReq = new AuditReq()
                            .setAuditNo(ctx.getAuditUserNo())
                            .setRefId(ctx.getRefId())
                            .setRefType(ctx.getRefType())
                            .setAuditType(ctx.getAuditType())
                            .setAuditContent(ctx.getAuditContent())
                            .setAuditKey(ctx.getAuditKey())
                            .setAuditReason(ctx.getAuditReason());
                    String traceId = auditProcessService
                            .startProcessInstance(auditReq, ctx.getSubmitUser());
                    audit.setTraceId(traceId);
                    auditRepository.add(audit);
                    break;
                case CANCEL:
                    audit = auditRepository.getByRefAndType(ctx.getRefId(), ctx.getRefType(),
                            ctx.getAuditType());
                    if (null != audit) {
                        auditProcessService.cancelProcess(audit.getTraceId(), CANCEL_COMMENT);
                        auditRepository.deleteByRefAndType(ctx.getRefId(), ctx.getRefType(),
                                ctx.getAuditType());
                    }
                    checkRepository.updateValByRefAndCheckKeys(ctx.getRefId(), ctx.getRefType(),
                            ctx.getOnShelfCheckKeys(), Bool2IntEnum.NO.getValue());
                    break;
                case AUTO_CANCEL:
                    auditRepository.deleteByRefAndType(ctx.getRefId(), ctx.getRefType(),
                            ctx.getAuditType());
                    checkRepository.updateValByRefAndCheckKeys(ctx.getRefId(), ctx.getRefType(),
                            ctx.getOnShelfCheckKeys(), Bool2IntEnum.NO.getValue());
                    break;
                case REFUSE:
                    auditRepository.updateStatusByRefAndType(ctx.getRefId(), ctx.getRefType(),
                            ctx.getAuditType(), to.getValue());
                    checkRepository.updateValByRefAndCheckKeys(ctx.getRefId(), ctx.getRefType(),
                            ctx.getOnShelfCheckKeys(), Bool2IntEnum.NO.getValue());
                    break;
                default:
                    auditRepository.updateStatusByRefAndType(ctx.getRefId(), ctx.getRefType(),
                            ctx.getAuditType(), to.getValue());
                    break;
            }
            log.info(ctx.getRefId() + " dev audit state has changed from:" + from + " to:" + to + " on:" + event);
        };
    }
}
