package com.tuya.hardware.symphony.dtlog.core.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @mbg.generated
 * 表名: t_log_es_search_field
 * @date 2021/01/07
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TLogEsSearchFieldDO extends LogAbstractDataObject {
    /**
     * @mbg.generated
     * es字段名称
     * 表字段: es_field_name
     */
    private String esFieldName;

    /**
     * @mbg.generated
     * es字段类型
     * 表字段: es_field_type
     */
    private String esFieldType;
}