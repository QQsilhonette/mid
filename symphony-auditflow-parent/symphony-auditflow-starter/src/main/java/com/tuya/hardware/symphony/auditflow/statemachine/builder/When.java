package com.tuya.hardware.symphony.auditflow.statemachine.builder;

import com.tuya.hardware.symphony.auditflow.statemachine.model.Action;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/9/12 4:28 下午
 * @description：transition的触发条件
 */
public interface When<S, E, C> {
    /**
     * 构建transition的执行
     * @param action
     */
    void perform(Action<S, E, C> action);
}
