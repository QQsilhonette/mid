package com.tuya.hardware.symphony.auditflow.enums.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 一兮
 * @Date 2020/10/31
 * @Description
 */
@Getter
@AllArgsConstructor
public enum SupportEnum {
    NOT_SUPPORT(0, "不支持"),
    SUPPORT(1, "支持");

    private Integer value;
    private String desc;

    public static SupportEnum of(Integer value) {
        return Arrays.stream(SupportEnum.values())
                .filter(em -> em.getValue().equals(value))
                .findAny()
                .orElse(null);
    }
}
