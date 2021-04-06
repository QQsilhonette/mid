package com.tuya.hardware.symphony.auditflow.statemachine.model;

import com.tuya.hardware.symphony.auditflow.statemachine.model.impl.TransitionType;

import java.util.Collection;
import java.util.Optional;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/9/12 4:28 下午
 * @description：状态
 */
public interface State<S, E, C> {
    S getId();

    /**
     * 将transition绑定到state(起始的state)
     *
     * @param event
     * @param target
     * @param transitionType
     * @return
     */
    Transition<S, E, C> addTransition(E event, State<S, E, C> target, TransitionType transitionType);

    /**
     * 根据event获取transition
     *
     * @param event
     * @return
     */
    Optional<Transition<S, E, C>> getTransition(E event);

    Collection<Transition<S, E, C>> getTransitions();

    String accept(Visitor visitor);
}
