package com.tuya.hardware.symphony.whitelist.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum WhitelistTypeEnum {
    IOT_ACCOUNT(0, "账号"),
    PRODUCT_ID(1, "pid"),
    DEBUG_IOT_ACCOUNT(2, "调试账号"),
    DEBUG_PRODUCT_ID(3, "调试pid");

    private Integer value;
    private String desc;

    public static WhitelistTypeEnum of(Integer value) {
        return Arrays.stream(WhitelistTypeEnum.values())
                .filter(em -> em.getValue().equals(value))
                .findAny()
                .orElse(null);
    }

    public static boolean isIotAccount(Integer type) {
        return IOT_ACCOUNT.getValue().equals(type) || DEBUG_IOT_ACCOUNT.getValue().equals(type);
    }

    public static boolean isProductId(Integer type) {
        return PRODUCT_ID.getValue().equals(type) || DEBUG_PRODUCT_ID.getValue().equals(type);
    }

    public static boolean isDebug(Integer type) {
        return DEBUG_PRODUCT_ID.getValue().equals(type) || DEBUG_IOT_ACCOUNT.getValue().equals(type);
    }
}
