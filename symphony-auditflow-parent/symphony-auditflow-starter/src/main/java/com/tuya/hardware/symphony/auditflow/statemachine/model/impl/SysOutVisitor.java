package com.tuya.hardware.symphony.auditflow.statemachine.model.impl;


import com.tuya.hardware.symphony.auditflow.statemachine.model.State;
import com.tuya.hardware.symphony.auditflow.statemachine.model.StateMachine;
import com.tuya.hardware.symphony.auditflow.statemachine.model.Transition;
import com.tuya.hardware.symphony.auditflow.statemachine.model.Visitor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/9/12 5:25 下午
 * @description：状态机流程生成类
 */
@Slf4j
public class SysOutVisitor implements Visitor {

    @Override
    public String visitOnEntry(StateMachine<?, ?, ?> stateMachine) {
        String entry = "-----StateMachine:" + stateMachine.getMachineId() + "-------";
        log.debug(entry);
        return entry;
    }

    @Override
    public String visitOnExit(StateMachine<?, ?, ?> stateMachine) {
        String exit = "------------------------";
        log.debug(exit);
        return exit;
    }

    @Override
    public String visitOnEntry(State<?, ?, ?> state) {
        StringBuilder sb = new StringBuilder();
        String stateStr = "State:" + state.getId();
        sb.append(stateStr).append(LF);
        System.out.println(stateStr);
        for (Transition transition : state.getTransitions()) {
            String transitionStr = "    Transition:" + transition;
            sb.append(transitionStr).append(LF);
            System.out.println(transitionStr);
        }
        return sb.toString();
    }

    @Override
    public String visitOnExit(State<?, ?, ?> visitable) {
        return "";
    }
}
