package com.tuya.hardware.symphony.auditflow.service.base;

import com.tuya.themis.client.enums.AuditStatusEnum;
import com.tuya.themis.client.message.EventMessage;
import com.tuya.themis.client.result.ThemisResult;
import com.tuya.themis.client.service.process.IProcessService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * @author 旋影
 * @create 2020/10/28
 */
@Slf4j
public abstract class AbstractProcessService<C extends BaseProcessReq> {

    @Resource
    IProcessService processService;
    /**
     * 审批流开始
     *
     * @param submitUser 申请人staffId
     * @return 返回的是审批流实例的 processInstanceId
     */
    public abstract String startProcessInstance(C req, String submitUser);

    /**
     * 审批kafka消息回调接口
     *
     * @param eventMessage
     * @return
     */
    public abstract boolean callBack(EventMessage eventMessage);

    /**
     * 取消流程
     *
     * @param processInstanceId 实例id
     * @param comment
     * @return isSuccess
     */
    public boolean cancelProcess(String processInstanceId, String comment) {
        ThemisResult<Boolean> themisResult = processService.cancelProcess(processInstanceId, comment);
        if (!themisResult.isSuccess() || !themisResult.getData()) {
            log.info("process {} cancel fail", processInstanceId);
            return false;
        }
        return true;
    }

    /**
     * 主动查询审核结果
     *
     * @param processInstanceId
     * @return
     */
    public ThemisResult<AuditStatusEnum> queryProcessStatus(String processInstanceId) {
        ThemisResult<AuditStatusEnum> themisResult = processService.queryProcessStatusByProcessInstanceId(processInstanceId);
        if (!themisResult.isSuccess()) {
            return null;
        }
        return themisResult;
    }
}
