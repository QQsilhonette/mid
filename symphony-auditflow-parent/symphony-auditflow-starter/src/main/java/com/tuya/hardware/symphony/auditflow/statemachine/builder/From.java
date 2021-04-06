package com.tuya.hardware.symphony.auditflow.statemachine.builder;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/9/12 4:28 下午
 * @description：transition的初始状态
 */
public interface From<S, E, C> {
    /**
     * 构建transition的目标状态
     * @param stateId
     * @return
     */
    To<S, E, C> to(S stateId);
}
