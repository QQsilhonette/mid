package com.tuya.hardware.symphony.auditflow.model.context;

import com.tuya.hardware.symphony.auditflow.enums.audit.AuditEventEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data
public class AudContext {

    /**
     * 关联id
     */
    private Long refId;

    /**
     * 关联类型
     */
    private String refType;

    /**
     * 审核类型
     */
    private String auditType;

    /**
     * 上架校验项
     */
    private List<String> onShelfCheckKeys;

    /**
     * 审核事件类型
     */
    private AuditEventEnum eventType;

    /**
     * 审核人工号
     */
    private String auditUserNo;

    /**
     * 审核人staffId
     */
    private String auditUserStaffId;

    /**
     * 提交人
     */
    private String submitUser;

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