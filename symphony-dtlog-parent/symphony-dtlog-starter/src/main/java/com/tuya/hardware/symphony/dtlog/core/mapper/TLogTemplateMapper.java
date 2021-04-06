package com.tuya.hardware.symphony.dtlog.core.mapper;

import com.tuya.hardware.symphony.dtlog.core.dataobject.TLogTemplateDO;
import org.apache.ibatis.annotations.Param;

/**
 * @mbg.generated 表名: t_log_template
 * @date 2021/01/07
 */
public interface TLogTemplateMapper {

    TLogTemplateDO queryByLogType(@Param("logType") String logType);
}