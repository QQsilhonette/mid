package com.tuya.hardware.symphony.auditflow.statemachine.builder;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/9/12 4:28 下午
 * @description：transition的目标状态
 */
public interface To<S, E, C> {
    /**
     * 构建transition的触发事件
     * @param event
     * @return
     */
    On<S, E, C> on(E event);
}
