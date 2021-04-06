package com.tuya.hardware.symphony.auditflow.statemachine.builder;

import com.tuya.hardware.symphony.auditflow.statemachine.model.Condition;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/9/12 4:28 下午
 * @description：transition的触发事件
 */
public interface On<S, E, C> {
    /**
     * 构建transition的触发条件
     * @param condition
     * @return
     */
    When<S, E, C> when(Condition<C> condition);
}
