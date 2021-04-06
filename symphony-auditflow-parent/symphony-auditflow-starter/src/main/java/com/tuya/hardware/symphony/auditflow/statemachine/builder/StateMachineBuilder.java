package com.tuya.hardware.symphony.auditflow.statemachine.builder;

import com.tuya.hardware.symphony.auditflow.statemachine.model.StateMachine;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/9/12 4:28 下午
 * @description：状态机构造器
 */
public interface StateMachineBuilder<S, E, C> {
    /**
     * 外部状态机构造器
     *
     * @return
     */
    ExternalTransitionBuilder<S, E, C> externalTransition();

    /**
     * 多source外部状态机构造器
     * @return
     */
    ExternalTransitionsBuilder<S, E, C> externalTransitions();

    /**
     * 内部状态机构造器
     * @return
     */
    InternalTransitionBuilder<S, E, C> internalTransition();

    /**
     * 构建一个状态机
     * @param machineId
     * @return
     */
    StateMachine<S, E, C> build(String machineId);
}
