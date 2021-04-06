package com.tuya.hardware.symphony.auditflow.statemachine.model.impl;

import com.tuya.basic.client.domain.exception.BaseException;
import com.tuya.hardware.symphony.auditflow.statemachine.model.StateMachine;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/9/12 6:01 下午
 * @description：状态机工厂类
 */
public class StateMachineFactory {

    static Map<String, StateMachine> stateMachineMap = new ConcurrentHashMap<>();

    public static <S, E, C> void register(StateMachine<S, E, C> stateMachine) {
        String machineId = stateMachine.getMachineId();
        if (null != stateMachineMap.get(machineId)) {

            throw new BaseException("The state machine with id [" + machineId + "] is already built, no need to build again");
        }
        stateMachineMap.put(stateMachine.getMachineId(), stateMachine);
    }

    public static <S, E, C> StateMachine<S, E, C> get(String machineId) {
        StateMachine stateMachine = stateMachineMap.get(machineId);
        if (stateMachine == null) {
            throw new BaseException("There is no stateMachine instance for " + machineId + ", please build it first");
        }
        return stateMachine;
    }

    public static boolean contains(String machineId) {
        return stateMachineMap.containsKey(machineId);
    }
}
