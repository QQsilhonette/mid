package com.tuya.hardware.symphony.auditflow.enums.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 通用Integer表示bool的枚举
 */
@Getter
@AllArgsConstructor
public enum Bool2IntEnum {
    NO(0, "否"),
    YES(1, "是");

    private Integer value;
    private String desc;

    public static Bool2IntEnum of(Integer value) {
        return Arrays.stream(Bool2IntEnum.values())
                .filter(em -> em.getValue().equals(value))
                .findAny()
                .orElse(null);
    }
}
