package com.tuya.hardware.symphony.auditflow.service.shelf;

import com.tuya.hardware.symphony.auditflow.enums.common.Bool2IntEnum;
import com.tuya.hardware.symphony.auditflow.enums.shelf.ShelfEventEnum;
import com.tuya.hardware.symphony.auditflow.model.context.ShelfCommonContext;
import com.tuya.hardware.symphony.auditflow.infrastructure.dataobject.AuditDO;
import com.tuya.hardware.symphony.auditflow.enums.audit.AuditStateEnum;
import com.tuya.hardware.symphony.auditflow.enums.shelf.ShelfStateEnum;
import com.tuya.hardware.symphony.auditflow.repository.audit.AuditRepository;
import com.tuya.hardware.symphony.auditflow.repository.check.CheckRepository;
import com.tuya.hardware.symphony.auditflow.service.common.WxMessageManage;
import com.tuya.hardware.symphony.auditflow.statemachine.AbstractSingleExternalMachine;
import com.tuya.hardware.symphony.auditflow.statemachine.model.Action;
import com.tuya.hardware.symphony.auditflow.statemachine.model.Condition;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/10/27 11:40 上午
 * @description：上架状态机公共方法
 */
@Slf4j
public abstract class AbstractShelfStateMachineService<C extends ShelfCommonContext> extends AbstractSingleExternalMachine<ShelfStateEnum, ShelfEventEnum, C> {

    @Resource
    private AuditRepository auditRepository;
    @Resource
    private CheckRepository checkRepository;
    @Resource
    private WxMessageManage wxMessageManage;

    @Data
    public static class PreResult {
        /**
         * 上架事件类型
         */
        ShelfEventEnum event;
        /**
         * 状态机id
         */
        String machineId;
    }

    private static final String MACHINE_PREFIX = "ShelfState_";

    public ShelfStateEnum trigger(ShelfStateEnum start, ShelfStateEnum end, C context) {
        PreResult preResult = preTrigger(start, end, context.getRefType());
        context.setEventType(preResult.getEvent());
        return this.start(start, end, preResult.getEvent(), context, checkCondition(), doAction(), preResult.getMachineId());
    }

    protected Condition<C> checkCondition() {
        return (ctx) -> {
            if (ShelfEventEnum.ON_SHELF.equals(ctx.getEventType())) {
                AuditDO audit = auditRepository.getByRefAndType(ctx.getRefId(),
                        ctx.getRefType(), ctx.getAuditType());
                return (null != audit && AuditStateEnum.PASS.getValue().equals(audit.getAuditStatus()));
            } else {
                return true;
            }
        };
    }

    protected Action<ShelfStateEnum, ShelfEventEnum, C> doAction() {
        return (from, to, event, ctx) -> {
            execute(from, to, event, ctx);
            updateAuditAndCheck(event, ctx);
            sendWxMessage(event, ctx);
            postExecute(from, to, event, ctx);
            log.info(ctx.getRefId() + " shelf state has changed from:" + from + " to:" + to + " on:" + event);
        };
    }

    protected void execute(ShelfStateEnum from, ShelfStateEnum to, ShelfEventEnum event, C ctx) {

    }

    protected void postExecute(ShelfStateEnum from, ShelfStateEnum to, ShelfEventEnum event, C ctx) {

    }

    protected void updateAuditAndCheck(ShelfEventEnum event, C ctx) {
        switch(event) {
            case OFF_SHELF:
                executeOffShelf(ctx);
            case ON_SHELF:
                executeOnShelf(ctx);
                break;
            default:
                break;
        }
    }

    protected void sendWxMessage(ShelfEventEnum event, C ctx) {
        if (!ctx.isNeedWxNotify()) {
            return;
        }
        switch(event) {
            case OFF_SHELF:
                wxMessageManage.wxSendMsg(ctx.getDialogue(), ctx.getToUser(), ctx.getOffShelfReason());
                break;
            case BEING_OFF_SHELF:
                wxMessageManage.wxSendMsg(ctx.getDialogue(), ctx.getToUser(), ctx.getOffShelfReason());
                break;
            default:
                break;
        }
    }

    private PreResult preTrigger(ShelfStateEnum start, ShelfStateEnum end, String refType) {
        PreResult result = new PreResult();
        ShelfEventEnum event = ShelfEventEnum.CHANGE_STATE;
        switch(end) {
            case OFF_SHELF:
                event = ShelfEventEnum.OFF_SHELF;
                break;
            case ON_SHELF:
                event = ShelfEventEnum.ON_SHELF;
                break;
            case BEING_OFF_SHELF:
                event = ShelfEventEnum.BEING_OFF_SHELF;
                break;
            default:
                break;
        }

        StringBuilder machineId = new StringBuilder();
        machineId.append(refType)
                .append(MACHINE_PREFIX)
                .append(start.getValue())
                .append("_")
                .append(end.getValue())
                .append("_")
                .append(event.getValue());
        result.setEvent(event);
        result.setMachineId(machineId.toString());
        return result;
    }

    /**
     * 元器件物料上架的后续操作
     */
    private void executeOnShelf(C ctx) {
        // 上架要删除之前的下架确认项
        checkRepository.updateValByRefAndCheckKeys(ctx.getRefId(), ctx.getRefType(),
                ctx.getOffShelfCheckTypes(), Bool2IntEnum.NO.getValue());
    }

    /**
     * 元器件物料下架的后续操作
     */
    private void executeOffShelf(C ctx) {
        // 下架要删除之前的审核记录和上架确认项
        auditRepository.deleteByRefAndType(ctx.getRefId(), ctx.getRefType(),
                ctx.getAuditType());
        checkRepository.updateValByRefAndCheckKeys(ctx.getRefId(), ctx.getRefType(),
                ctx.getOnShelfCheckTypes(), Bool2IntEnum.NO.getValue());
    }
}
