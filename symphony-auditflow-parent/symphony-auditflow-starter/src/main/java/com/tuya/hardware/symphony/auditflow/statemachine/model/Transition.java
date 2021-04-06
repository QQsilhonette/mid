package com.tuya.hardware.symphony.auditflow.statemachine.model;

import com.tuya.hardware.symphony.auditflow.statemachine.model.impl.TransitionType;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/9/12 4:28 下午
 * @description：流转
 */
public interface Transition<S, E, C> {

    State<S, E, C> getSource();

    void setSource(State<S, E, C> source);

    State<S, E, C> getTarget();

    void setTarget(State<S, E, C> target);

    E getEvent();

    void setEvent(E event);

    Condition<C> getCondition();

    void setCondition(Condition<C> condition);

    Action<S, E, C> getAction();

    void setAction(Action<S, E, C> action);

    TransitionType getType();

    void setType(TransitionType type);

    /**
     * 状态转移执行
     * @param ctx
     * @return
     */
    State<S, E, C> transit(C ctx);
}
