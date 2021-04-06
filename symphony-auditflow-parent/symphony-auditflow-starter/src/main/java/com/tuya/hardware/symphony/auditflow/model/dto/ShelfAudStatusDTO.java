package com.tuya.hardware.symphony.auditflow.model.dto;

import com.tuya.hardware.symphony.auditflow.enums.audit.AuditStateEnum;
import com.tuya.hardware.symphony.auditflow.enums.shelf.ShelfStateEnum;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@ToString(callSuper = true)
public class ShelfAudStatusDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 上架状态
     * @see ShelfStateEnum
     */
    private Integer shelfStatus;

    /**
     * 审核状态
     * @see AuditStateEnum
     */
    private String auditStatus;

    /**
     * 审核人
     */
    private String auditUsername;

    /**
     * 下架时间
     */
    private Long stopUsingTime;

    /**
     * 审批实例id
     */
    private String traceId;
}
