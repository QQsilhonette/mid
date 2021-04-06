package com.tuya.hardware.symphony.dtlog.core.mapper;

import com.tuya.hardware.symphony.dtlog.core.dataobject.TLogTemplateSearchFieldDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @mbg.generated 表名: t_log_template_search_field
 * @date 2021/01/07
 */
public interface TLogTemplateSearchFieldMapper {

    List<TLogTemplateSearchFieldDO> queryByTemplateId(@Param("templateId") Long templateId);
}