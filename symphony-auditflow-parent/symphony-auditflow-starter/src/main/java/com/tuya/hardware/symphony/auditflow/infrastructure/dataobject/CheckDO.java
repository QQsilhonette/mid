package com.tuya.hardware.symphony.auditflow.infrastructure.dataobject;

import com.tuya.hardware.symphony.framework.base.AbstractDataObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @mbg.generated
 * 表名: t_check
 * @date 2020/10/26
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CheckDO extends AbstractDataObject {
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
     * 确认项
     * 表字段: check_key
     */
    private String checkKey;

    /**
     * @mbg.generated
     * 确认状态
     * 表字段: check_value
     */
    private Integer checkValue;
}