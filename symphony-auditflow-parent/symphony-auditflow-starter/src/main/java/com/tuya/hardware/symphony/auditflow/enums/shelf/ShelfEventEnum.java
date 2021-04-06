package com.tuya.hardware.symphony.auditflow.enums.shelf;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ShelfEventEnum {

    CHANGE_STATE(-1,"修改状态"),
    OFF_SHELF(0, "下架"),
    ON_SHELF(1, "上架"),
    BEING_OFF_SHELF(2, "即将下架"),
    ;

    private Integer value;
    private String desc;

    public static ShelfEventEnum of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(ShelfEventEnum.values())
                .filter(em -> value.equals(em.getValue()))
                .findAny()
                .orElse(null);
    }
}
