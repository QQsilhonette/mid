package com.tuya.hardware.symphony.dtlog.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;

import java.util.Arrays;

/**
 * @author 一兮
 * @Date 2021/03/15
 * @Description
 */
@Slf4j
@Getter
@AllArgsConstructor
public enum LogEsFieldTypeEnum {
    FIELD_DATE("date"),
    FIELD_KEY_WORD("keyword"),
    FIELD_INTEGER("integer"),
    FIELD_TEXT_IK("text_ik"),
    FIELD_TEXT("text"),
    FIELD_LONG("long"),
    FIELD_FLOAT("float"),
    ;

    private String fieldType;

    public static LogEsFieldTypeEnum of(String esFieldName) {
        return Arrays.stream(LogEsFieldTypeEnum.values())
                .filter(esFieldNameEnum -> esFieldNameEnum.fieldType.equals(esFieldName))
                .findAny()
                .orElse(null);
    }

    public static void buildDateQuery(BoolQueryBuilder queryBuilder, String esFieldName, String fromDate, String toDate) {
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(esFieldName);
        if (StringUtils.isNotEmpty(fromDate)) {
            rangeQueryBuilder.gte(fromDate);
        }
        if (StringUtils.isNotEmpty(toDate)) {
            rangeQueryBuilder.lte(toDate);
        }
        if (StringUtils.isNotEmpty(fromDate) || StringUtils.isNotEmpty(toDate)) {
            queryBuilder.must(rangeQueryBuilder);
        }
    }

    public static void buildQuery(BoolQueryBuilder queryBuilder, String esFieldType, String esFieldName, Object value) {
        LogEsFieldTypeEnum esFieldTypeEnum = of(esFieldType);
        if (esFieldTypeEnum == null || esFieldTypeEnum == FIELD_DATE) {
            log.debug("【unKnow esFieldName or data field ignore】-【esFieldType={}】-【esFieldName={}】-【value={}】", esFieldType, esFieldName, value);
            return;
        }
        if (value == null || ((value instanceof String) && StringUtils.isEmpty(value.toString()))) {
            log.debug("【value empty field ignore】-【esFieldType={}】-【esFieldName={}】-【value={}】", esFieldType, esFieldName, value);
            return;
        }
        switch (esFieldTypeEnum) {
            case FIELD_LONG:
                queryBuilder.filter(QueryBuilders.termQuery(esFieldName, value));
                break;
            case FIELD_TEXT:
                queryBuilder.must(QueryBuilders.matchPhraseQuery(esFieldName, value));
                break;
            case FIELD_FLOAT:
                queryBuilder.filter(QueryBuilders.termQuery(esFieldName, value));
                break;
            case FIELD_INTEGER:
                queryBuilder.filter(QueryBuilders.termQuery(esFieldName, value));
                break;
            case FIELD_TEXT_IK:
                queryBuilder.must(QueryBuilders.matchQuery(esFieldName, value));
                break;
            case FIELD_KEY_WORD:
                queryBuilder.filter(QueryBuilders.termQuery(esFieldName, value));
                break;
            default:
                log.warn("【unKnow esFieldName】-【esFieldType={}】-【esFieldName={}】-【value={}】", esFieldType, esFieldName, value);
        }
    }
}