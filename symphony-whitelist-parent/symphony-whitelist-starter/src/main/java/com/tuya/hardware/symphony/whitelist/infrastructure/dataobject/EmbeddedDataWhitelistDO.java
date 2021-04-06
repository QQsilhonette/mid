package com.tuya.hardware.symphony.whitelist.infrastructure.dataobject;

import com.tuya.hardware.symphony.framework.base.AbstractDataObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @mbg.generated
 * 表名: t_embedded_data_whitelist
 * @date 2020/09/26
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EmbeddedDataWhitelistDO extends AbstractDataObject {

    private String refType;

    /**
     * @mbg.generated
     * 白名单类型 0、账号维度 1、pid维度
     * 表字段: type
     */
    private Integer type;

    /**
     * @mbg.generated
     * 元器件ID 固件版本ID等
     * 表字段: ref_id
     */
    private Long refId;

    /**
     * @mbg.generated
     * 账号|pid
     * 表字段: ref_value
     */
    private String refValue;

    /**
     * @mbg.generated
     * 操作人
     * 表字段: modifier
     */
    private String modifier;
}