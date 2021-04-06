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
public enum LogSearchTypeEnum {
    TEXT("Text"),
    SELECT("Select"),
    DATE("Date"),
    ;

    private String fieldType;

    public static LogSearchTypeEnum of(String esFieldName) {
        return Arrays.stream(LogSearchTypeEnum.values())
                .filter(esFieldNameEnum -> esFieldNameEnum.fieldType.equals(esFieldName))
                .findAny()
                .orElse(null);
    }
}