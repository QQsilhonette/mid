package com.tuya.hardware.symphony.dtlog.core.dataobject;

import com.tuya.hardware.symphony.framework.base.AbstractDataObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @mbg.generated
 * 表名: t_log_template
 * @date 2021/01/07
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TLogTemplateDO extends AbstractDataObject {
    /**
     * @mbg.generated
     * 日志模版名称
     * 表字段: name
     */
    private String name;

    /**
     * @mbg.generated
     * 日志类型
     * 表字段: log_type
     */
    private String logType;

    /**
     * @mbg.generated
     * 模版内容解析规则
     * 表字段: content_analysis_rule
     */
    private String contentAnalysisRule;
}