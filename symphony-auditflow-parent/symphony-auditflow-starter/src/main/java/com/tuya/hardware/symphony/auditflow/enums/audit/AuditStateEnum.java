package com.tuya.hardware.symphony.auditflow.enums.audit;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/10/26 3:52 下午
 * @description：审核状态
 */
@Getter
@AllArgsConstructor
public enum AuditStateEnum {

    NOT_AUDIT("-1", "未审核"),
    IN_AUDIT("0", "审核中"),
    PASS("1", "通过"),
    REJECT("2", "驳回"),
    ;

    private String value;

    private String desc;

    public static AuditStateEnum of(String value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(AuditStateEnum.values())
                .filter(em -> value.equals(em.getValue()))
                .findAny()
                .orElse(null);
    }
}
