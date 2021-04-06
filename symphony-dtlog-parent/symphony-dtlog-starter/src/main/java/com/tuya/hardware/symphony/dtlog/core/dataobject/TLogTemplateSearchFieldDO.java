package com.tuya.hardware.symphony.dtlog.core.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @mbg.generated
 * 表名: t_log_template_search_field
 * @date 2021/01/07
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TLogTemplateSearchFieldDO extends LogAbstractDataObject {
    /**
     * @mbg.generated
     * 模版id
     * 表字段: template_id
     */
    private Long templateId;

    /**
     * @mbg.generated
     * 真实db字段名称
     * 表字段: field_name
     */
    private String fieldName;

    /**
     * @mbg.generated
     * es的字段id
     * 表字段: es_field_id
     */
    private Long esFieldId;
}