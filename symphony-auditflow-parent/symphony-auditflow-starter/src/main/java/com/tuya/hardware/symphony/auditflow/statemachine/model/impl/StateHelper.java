package com.tuya.hardware.symphony.auditflow.statemachine.model.impl;

import com.tuya.hardware.symphony.auditflow.statemachine.model.State;

import java.util.Map;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/9/12 5:25 下午
 * @description：State工具类
 */
public class StateHelper {
    public static <S, E, C> State<S, E, C> getState(Map<S, State<S, E, C>> stateMap, S stateId) {
        State<S, E, C> state = stateMap.get(stateId);
        if (null == state) {
            state = new StateImpl<>(stateId);
            stateMap.put(stateId, state);
        }
        return state;
    }
}
