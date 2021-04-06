package com.tuya.hardware.symphony.auditflow.statemachine;

import com.tuya.hardware.symphony.auditflow.statemachine.builder.StateMachineBuilder;
import com.tuya.hardware.symphony.auditflow.statemachine.builder.StateMachineBuilderFactory;
import com.tuya.hardware.symphony.auditflow.statemachine.model.Action;
import com.tuya.hardware.symphony.auditflow.statemachine.model.Condition;
import com.tuya.hardware.symphony.auditflow.statemachine.model.StateMachine;
import com.tuya.hardware.symphony.auditflow.statemachine.model.impl.StateMachineFactory;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/11/13 2:34 下午
 * @description：单步状态转换状态机
 */
public abstract class AbstractSingleExternalMachine<S, E, C> {

    private StateMachine<S, E, C> buildStateMachine(S start, S end, E event, Condition condition, Action action, String machineId) {
        StateMachineBuilder<S, E, C> builder = StateMachineBuilderFactory.create();
        builder.externalTransition()
                .from(start)
                .to(end)
                .on(event)
                .when(condition)
                .perform(action);

        StateMachine<S, E, C> stateMachine = builder.build(machineId);
        return stateMachine;
    }

    public S start(S start, S end, E event, C context, Condition condition, Action action, String machineId) {
        StateMachine<S, E, C> stateMachine;
        if (StateMachineFactory.contains(machineId)) {
            stateMachine = StateMachineFactory.get(machineId);
        } else {
            stateMachine = buildStateMachine(start, end, event, condition, action, machineId);
        }
        return stateMachine.fireEvent(start, event, context);
    }
}
