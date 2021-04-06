package com.tuya.hardware.symphony.dtlog.core.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @mbg.generated
 * 表名: t_log_template_content_field
 * @date 2021/01/07
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TLogTemplateContentFieldDO extends LogAbstractDataObject {
    /**
     * @mbg.generated
     * 模版id
     * 表字段: template_id
     */
    private Long templateId;

    /**
     * @mbg.generated
     * 表格字段名称
     * 表字段: field_name
     */
    private String fieldName;

    /**
     * @mbg.generated
     * 表格字段key
     * 表字段: field_key
     */
    private String fieldKey;
}