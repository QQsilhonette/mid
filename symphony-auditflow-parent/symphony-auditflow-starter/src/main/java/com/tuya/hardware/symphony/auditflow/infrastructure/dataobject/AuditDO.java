package com.tuya.hardware.symphony.auditflow.infrastructure.dataobject;

import com.tuya.hardware.symphony.framework.base.AbstractDataObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @mbg.generated
 * 表名: t_audit
 * @date 2020/10/26
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AuditDO extends AbstractDataObject {
    /**
     * @mbg.generated
     * 关联类型
     * 表字段: ref_type
     */
    private String refType;

    /**
     * @mbg.generated
     * 关联id
     * 表字段: ref_id
     */
    private Long refId;

    /**
     * @mbg.generated
     * 审批流id
     * 表字段: trace_id
     */
    private String traceId;

    /**
     * @mbg.generated
     * 审批类型
     * 表字段: audit_type
     */
    private String auditType;

    /**
     * @mbg.generated
     * 审批状态
     * 表字段: audit_status
     */
    private String auditStatus;

    /**
     * @mbg.generated
     * 审批人
     * 表字段: audit_user
     */
    private String auditUser;

    /**
     * @mbg.generated
     * 提交人
     * 表字段: submit_user
     */
    private String submitUser;
}