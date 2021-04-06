package com.tuya.hardware.symphony.auditflow.statemachine.builder;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/9/12 4:28 下午
 * @description：外部状态机构造器
 */
public interface ExternalTransitionsBuilder<S, E, C> {
    /**
     * 构建transition的初始状态
     * @param stateId
     * @return
     */
    From<S, E, C> fromAmong(S... stateId);
}
