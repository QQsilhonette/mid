package com.tuya.hardware.symphony.dtlog.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author 一兮
 * @Date 2021/01/07
 * @Description
 */
@Getter
@Setter
@Accessors(chain = true)
public class QueryFieldEsMappingDTO {

    /**
     * @mbg.generated 真实db字段名称
     * 表字段: field_name
     */
    private String fieldName;

    /**
     * @mbg.generated es字段名称
     * 表字段: es_field_name
     */
    private String esFieldName;

    /**
     * @mbg.generated es字段类型
     * 表字段: es_field_type
     */
    private String esFieldType;
}
