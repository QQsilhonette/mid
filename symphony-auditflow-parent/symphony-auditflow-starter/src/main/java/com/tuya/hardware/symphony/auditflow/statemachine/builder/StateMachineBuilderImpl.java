package com.tuya.hardware.symphony.auditflow.statemachine.builder;

import com.tuya.hardware.symphony.auditflow.statemachine.model.State;
import com.tuya.hardware.symphony.auditflow.statemachine.model.StateMachine;
import com.tuya.hardware.symphony.auditflow.statemachine.model.impl.StateMachineFactory;
import com.tuya.hardware.symphony.auditflow.statemachine.model.impl.StateMachineImpl;
import com.tuya.hardware.symphony.auditflow.statemachine.model.impl.TransitionType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/9/12 4:30 下午
 * @description：状态机构造器的实现
 */
public class StateMachineBuilderImpl<S, E, C> implements StateMachineBuilder<S, E, C> {

    private final Map<S, State<S, E, C>> stateMap = new ConcurrentHashMap<>();

    private final StateMachineImpl<S, E, C> stateMachine = new StateMachineImpl<>(stateMap);

    @Override
    public ExternalTransitionBuilder<S, E, C> externalTransition() {
        return new TransitionBuilderImpl<>(stateMap, TransitionType.EXTERNAL);
    }

    @Override
    public ExternalTransitionsBuilder<S, E, C> externalTransitions() {
        return new TransitionsBuilderImpl<>(stateMap, TransitionType.EXTERNAL);
    }

    @Override
    public InternalTransitionBuilder<S, E, C> internalTransition() {
        return new TransitionBuilderImpl<>(stateMap, TransitionType.EXTERNAL);
    }

    @Override
    public StateMachine<S, E, C> build(String machineId) {
        stateMachine.setMachineId(machineId);
        stateMachine.setReady(true);
        StateMachineFactory.register(stateMachine);
        return stateMachine;
    }
}
