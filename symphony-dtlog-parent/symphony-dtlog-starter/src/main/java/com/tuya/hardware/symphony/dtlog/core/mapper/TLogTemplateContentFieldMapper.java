package com.tuya.hardware.symphony.dtlog.core.mapper;

import com.tuya.hardware.symphony.dtlog.core.dataobject.TLogTemplateContentFieldDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @mbg.generated 表名: t_log_template_content_field
 * @date 2021/01/07
 */
public interface TLogTemplateContentFieldMapper {

    List<TLogTemplateContentFieldDO> queryByTemplateId(@Param("templateId") Long templateId);
}