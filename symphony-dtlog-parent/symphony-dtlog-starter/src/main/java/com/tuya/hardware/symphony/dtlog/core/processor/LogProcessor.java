package com.tuya.hardware.symphony.dtlog.core.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageRowBounds;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tuya.basic.client.domain.exception.BaseException;
import com.tuya.hardware.symphony.dtlog.core.aspect.LogField;
import com.tuya.hardware.symphony.dtlog.core.aspect.LogTemplate;
import com.tuya.hardware.symphony.dtlog.core.constants.LogEsConstant;
import com.tuya.hardware.symphony.dtlog.core.enums.LogEsFieldTypeEnum;
import com.tuya.hardware.symphony.dtlog.core.enums.LogSearchTypeEnum;
import com.tuya.hardware.symphony.dtlog.core.repository.LogEsRepository;
import com.tuya.hardware.symphony.dtlog.dto.EsLogPagination;
import com.tuya.hardware.symphony.dtlog.dto.LogTableDTO;
import com.tuya.hardware.symphony.dtlog.dto.LogTableHeaderDTO;
import com.tuya.hardware.symphony.dtlog.dto.LogTableSearchDTO;
import com.tuya.hardware.symphony.dtlog.event.LogEventModel;
import com.tuya.hardware.symphony.framework.utils.MidSpringContextUtil;
import com.tuya.middleware.dubai.utils.SnowFlakeUuId;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 一兮
 * @Date 2021/01/07
 * @Description
 */
@DependsOn("midSpringContextUtil")
@Slf4j
@Component
public class LogProcessor<T extends LogEventModel> {

    @Resource
    private LogEsRepository logEsRepository;

    @Value("${log.index.name}")
    private String logIndexName;

    private List<LogTableDTO> logTableList = new ArrayList<>();
    private Map<String, List<LogTableHeaderDTO>> logTemplateMap = new HashMap<>();
    private Map<String, List<LogTableSearchDTO>> logSearchFieldMap = new HashMap<>();

    private static final List<String> systemKey = new ArrayList<>();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static {
        systemKey.add(LogEsConstant.SystemFieldConstant.form_key);
        systemKey.add(LogEsConstant.SystemFieldConstant.operator);
        systemKey.add(LogEsConstant.SystemFieldConstant.platform);
        systemKey.add(LogEsConstant.SystemFieldConstant.tenantId);
        systemKey.add(LogEsConstant.SystemFieldConstant.content);
        systemKey.add(LogEsConstant.SystemFieldConstant.date);
    }

    @PostConstruct
    public void initLogTemplate() {
        ApplicationContext applicationContext = MidSpringContextUtil.getApplicationContext();
        Map<String, Object> beanList = applicationContext.getBeansWithAnnotation(LogTemplate.class);
        Class<? extends Object> clazz = null;
        for (Map.Entry<String, Object> entry : beanList.entrySet()) {
            clazz = entry.getValue().getClass();
            List<LogTableHeaderDTO> headerList = Lists.newLinkedList();
            List<LogTableSearchDTO> searchList = Lists.newLinkedList();
            initLogHeader(headerList);
            String templateKey = clazz.getAnnotation(LogTemplate.class).key();
            String templateName = clazz.getAnnotation(LogTemplate.class).name();
            LogTableDTO logTableDTO = new LogTableDTO()
                    .setKey(templateKey)
                    .setName(templateName);
            logTableList.add(logTableDTO);
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                LogField logField = field.getAnnotation(LogField.class);
                if (Objects.nonNull(logField)) {
                    if (StringUtils.isNotEmpty(logField.name())) {
                        LogTableHeaderDTO logTableHeaderDTO = new LogTableHeaderDTO()
                                .setTitle(logField.name())
                                .setKey(field.getName())
                                .setDataIndex(field.getName());
                        headerList.add(logTableHeaderDTO);
                    }
                    if (StringUtils.isNotEmpty(logField.searchKey())) {
                        LogTableSearchDTO logTableSearchDTO = new LogTableSearchDTO()
                                .setFieldName(field.getName())
                                .setEsField(logField.searchKey())
                                .setSearchName(logField.searchName())
                                .setSearchType(logField.searchType())
                                .setOptions(parseOptions(logField.options()));
                        searchList.add(logTableSearchDTO);
                    }
                }
            }
            logTemplateMap.put(templateKey, headerList);
            logSearchFieldMap.put(templateKey, searchList);
        }
    }

    private void initLogHeader(List<LogTableHeaderDTO> headerList) {
        LogTableHeaderDTO logTableHeaderDTO1 = new LogTableHeaderDTO()
                .setTitle("操作时间")
                .setKey(LogEsConstant.SystemFieldConstant.date)
                .setDataIndex(LogEsConstant.SystemFieldConstant.date);
        LogTableHeaderDTO logTableHeaderDTO2 = new LogTableHeaderDTO()
                .setTitle("操作人")
                .setKey(LogEsConstant.SystemFieldConstant.operator)
                .setDataIndex(LogEsConstant.SystemFieldConstant.operator);
        headerList.add(logTableHeaderDTO1);
        headerList.add(logTableHeaderDTO2);
    }

    public boolean persistLog(T logEventModel) {
        log.debug("persistLog: {}", logEventModel);
        if (Objects.isNull(logEventModel)) {
            log.error("persistLog failed, input param is null");
            return false;
        }

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put(LogEsConstant.SystemFieldConstant.operator,
                Objects.nonNull(logEventModel.getOperator()) ? logEventModel.getOperator() : "");
        jsonMap.put(LogEsConstant.SystemFieldConstant.platform,
                Objects.nonNull(logEventModel.getPlatform()) ? logEventModel.getPlatform() : 0);
        jsonMap.put(LogEsConstant.SystemFieldConstant.tenantId,
                Objects.nonNull(logEventModel.getTenantId()) ? logEventModel.getTenantId() : "");
        jsonMap.put(LogEsConstant.SystemFieldConstant.date, System.currentTimeMillis());

        LogTemplate logTemplate = logEventModel.getClass().getAnnotation(LogTemplate.class);
        if (Objects.isNull(logTemplate)) {
            log.error("persistLog failed, LogTemplate annotation is null");
            return false;
        }
        String templateKey = logTemplate.key();
        jsonMap.put(LogEsConstant.SystemFieldConstant.form_key, templateKey);
        JSONObject content = new JSONObject();
        for (Field field : logEventModel.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            LogField logField = field.getAnnotation(LogField.class);
            if (Objects.nonNull(logField)) {
                Object val = null;
                try {
                    val = field.get(logEventModel);
                } catch (IllegalAccessException e) {
                    log.error("field:{} value access illegal");
                }
                if (Objects.isNull(val)) {
                    continue;
                }
                if (StringUtils.isNotEmpty(logField.searchKey())) {
                    jsonMap.put(logField.searchKey(), val);
                }
                if (StringUtils.isNotEmpty(logField.name())) {
                    content.put(field.getName(), val);
                }
            }
        }
        jsonMap.put(LogEsConstant.SystemFieldConstant.content, JSONObject.toJSONString(content));
        log.debug("【保存信息到es】-【writeIndex={}】-【jsonMap={}】", logIndexName, jsonMap);
        logEsRepository.addOrUpdate(logIndexName, SnowFlakeUuId.next16RadixId(), jsonMap);
        return true;
    }

    public List<LogTableHeaderDTO> queryHeader(String formKey) {
        if (logTemplateMap.isEmpty()) {
            throw new BaseException("模板初始化为空");
        }
        if (!logTemplateMap.containsKey(formKey)) {
            throw new BaseException("表单不存在:" + formKey);
        }
        return logTemplateMap.get(formKey);
    }

    public List<LogTableSearchDTO> querySearchField(String formKey) {
        if (logSearchFieldMap.isEmpty()) {
            throw new BaseException("模板初始化为空");
        }
        if (!logSearchFieldMap.containsKey(formKey)) {
            throw new BaseException("表单不存在:" + formKey);
        }
        return logSearchFieldMap.get(formKey);
    }

    public List<LogTableDTO> queryTemplates() {
        return logTableList;
    }

    public List<LogTableSearchDTO> querySearchItems(String templateKey) {
        if (StringUtils.isEmpty(templateKey) || !logSearchFieldMap.containsKey(templateKey)) {
            return new ArrayList<>();
        }
        List<LogTableSearchDTO> result = Lists.newArrayList();
        getSystemSearchItems(result);
        result.addAll(logSearchFieldMap.get(templateKey));
        return result;
    }

    public EsLogPagination queryPageLog(String formKey, Map<String, Object> queryDataMap, Integer limit, Integer offset) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        PageRowBounds pageRowBounds = new PageRowBounds(offset, limit);
        SortBuilder sortBuilder = SortBuilders.fieldSort(LogEsConstant.SystemFieldConstant.date).order(SortOrder.DESC);

        List<LogTableSearchDTO> esSearchFieldKey2Field = querySearchField(formKey);
        for (LogTableSearchDTO searchDTO : esSearchFieldKey2Field) {
            String esFieldName = searchDTO.getEsField();
            if (!LogEsConstant.DateFieldConstant.fieldTypeMap.containsKey(esFieldName)) {
                continue;
            }
            String esFieldType = LogEsConstant.DateFieldConstant.fieldTypeMap.get(esFieldName);
            LogEsFieldTypeEnum.buildQuery(queryBuilder, esFieldType, esFieldName, queryDataMap.get(searchDTO.getFieldName()));
        }
        LogEsFieldTypeEnum.buildQuery(queryBuilder, LogEsFieldTypeEnum.FIELD_KEY_WORD.getFieldType(), LogEsConstant.SystemFieldConstant.form_key, formKey);

        String fromDate = queryDataMap.get(LogEsConstant.DateFieldConstant.FROM_DATE) == null ? null : queryDataMap.get(LogEsConstant.DateFieldConstant.FROM_DATE).toString();
        String toDate = queryDataMap.get(LogEsConstant.DateFieldConstant.TO_DATE) == null ? null : queryDataMap.get(LogEsConstant.DateFieldConstant.TO_DATE).toString();
        String operator = queryDataMap.get(LogEsConstant.SystemFieldConstant.operator) == null ? null : queryDataMap.get(LogEsConstant.SystemFieldConstant.operator).toString();
        LogEsFieldTypeEnum.buildDateQuery(queryBuilder, LogEsConstant.SystemFieldConstant.date, fromDate, toDate);
        LogEsFieldTypeEnum.buildQuery(queryBuilder, LogEsFieldTypeEnum.FIELD_TEXT.getFieldType(), LogEsConstant.SystemFieldConstant.operator, operator);

        log.debug("【es查询参数】-【query={}】", queryBuilder.toString());
        List<JSONObject> searchResult = logEsRepository.search(logIndexName, queryBuilder, sortBuilder, pageRowBounds);

        List<JSONObject> analysisJsonList = searchResult.stream()
                .map(json -> analysisJsonField(formKey, json))
                .collect(Collectors.toList());

        Long totalCount = pageRowBounds.getTotal();
        EsLogPagination esLogPagination = new EsLogPagination();
        esLogPagination.setDatas(analysisJsonList);
        esLogPagination.setTotalCount(totalCount);
        esLogPagination.setPageNo(offset / limit + 1);
        return esLogPagination;
    }

    private JSONObject analysisJsonField(String formKey, JSONObject json) {
        Map<String, String> searchKeyToBizKey = querySearchField(formKey).stream()
                .collect(Collectors.toMap(LogTableSearchDTO::getEsField, LogTableSearchDTO::getFieldName));
        Set<String> contentKeys = queryHeader(formKey).stream().map(LogTableHeaderDTO::getKey).collect(Collectors.toSet());

        JSONObject analysisJson = new JSONObject();
        json.keySet().forEach((key) -> {
            Object value = json.get(key);
            if (systemKey.contains(key)) {
                analysisSystemField(formKey, contentKeys, key, value, analysisJson);
            } else {
                analysisOtherSearchField(formKey, searchKeyToBizKey, key, value, analysisJson);
            }
        });
        return analysisJson;
    }

    private void analysisOtherSearchField(String formKey, Map<String, String> searchKeyToBizKey, String key, Object value, JSONObject analysisJson) {
        String bizKey = searchKeyToBizKey.get(key);
        if (StringUtils.isEmpty(bizKey)) {
            log.debug("【未配置的搜索key】-【忽略】-【formKey={}】-【key={}】", formKey, key);
            return;
        }
        analysisJson.put(bizKey, value);
    }

    private void analysisSystemField(String formKey, Set<String> contentKeys, String key, Object value, JSONObject analysisJson) {
        if (LogEsConstant.SystemFieldConstant.content.equals(key)) {
            JSONObject contentJson = parseContentJson(value.toString());
            contentJson.keySet()
                    .forEach(contentKey -> {
                        Object contentValue = contentJson.get(contentKey);
                        if (!contentKeys.contains(contentKey)) {
                            log.debug("【未配置的内容key】-【忽略】-【formKey={}】-【key={}】", formKey, key);
                            return;
                        }
                        analysisJson.put(contentKey, contentValue);
                    });
        } else {
            if (LogEsConstant.SystemFieldConstant.date.equals(key)) {
                analysisJson.put(key, sdf.format(value));
            } else {
                analysisJson.put(key, value);
            }
        }
    }

    private JSONObject parseContentJson(String content) {
        if (StringUtils.isEmpty(content)) {
            return new JSONObject();
        }
        try {
            return JSON.parseObject(content);
        } catch (Exception e) {
            log.debug("解析json发生错误", e);
        }
        return new JSONObject();
    }

    private void getSystemSearchItems(List<LogTableSearchDTO> searchList) {
        LogTableSearchDTO logTableHeaderDTO1 = new LogTableSearchDTO()
                .setSearchName("开始时间")
                .setSearchType(LogSearchTypeEnum.DATE.getFieldType());
        LogTableSearchDTO logTableHeaderDTO2 = new LogTableSearchDTO()
                .setSearchName("结束时间")
                .setSearchType(LogSearchTypeEnum.DATE.getFieldType());
        LogTableSearchDTO logTableHeaderDTO3 = new LogTableSearchDTO()
                .setSearchName("操作人")
                .setSearchType(LogSearchTypeEnum.TEXT.getFieldType());
        searchList.add(logTableHeaderDTO1);
        searchList.add(logTableHeaderDTO2);
        searchList.add(logTableHeaderDTO3);
    }

    private Map<String, String> parseOptions(String options) {
        Map<String, String> result = Maps.newLinkedHashMap();
        JSONObject optionsJSON = parseContentJson(options);
        optionsJSON.keySet()
                .forEach(key -> {
                    Object contentValue = optionsJSON.get(key);
                    result.put(key, contentValue.toString());
                });
        return result;
    }
}
