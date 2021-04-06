package com.tuya.hardware.symphony.auditflow.enums.audit;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/10/26 3:52 下午
 * @description：审核状态变更event
 */
@Getter
@AllArgsConstructor
public enum AuditEventEnum {

    DEFAULT(0, "默认事件"),
    INIT(1, "初始化审核记录"),
    CANCEL(2, "取消审核记录"),
    AUTO_CANCEL(3, "审批流自动取消审核记录"),
    REFUSE(4, "驳回审核记录"),
    ;

    private Integer value;

    private String desc;

    public static AuditEventEnum of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(AuditEventEnum.values())
                .filter(em -> value.equals(em.getValue()))
                .findAny()
                .orElse(null);
    }
}
