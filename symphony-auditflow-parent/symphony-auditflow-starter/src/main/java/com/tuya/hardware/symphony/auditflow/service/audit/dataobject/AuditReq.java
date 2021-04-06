package com.tuya.hardware.symphony.auditflow.service.audit.dataobject;

import com.tuya.hardware.symphony.auditflow.service.base.BaseProcessReq;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class AuditReq extends BaseProcessReq {

    /**
     * 审核类型
     */
    private String auditType;

    /**
     * 关联主键id
     */
    private Long refId;

    /**
     * 审核人工号
     */
    private String auditNo;

    /**
     * 关联类型
     */
    private String refType;

    /**
     * 审批流key
     */
    private String auditKey;

    /**
     * 审批内容
     */
    private String auditContent;

    /**
     * 审批内容
     */
    private String auditReason;
}