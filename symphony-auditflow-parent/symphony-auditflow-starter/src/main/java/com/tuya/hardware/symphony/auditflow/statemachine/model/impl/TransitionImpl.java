package com.tuya.hardware.symphony.auditflow.statemachine.model.impl;

import com.tuya.basic.client.domain.exception.BaseException;
import com.tuya.hardware.symphony.auditflow.statemachine.model.Action;
import com.tuya.hardware.symphony.auditflow.statemachine.model.Condition;
import com.tuya.hardware.symphony.auditflow.statemachine.model.State;
import com.tuya.hardware.symphony.auditflow.statemachine.model.Transition;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/9/12 5:33 下午
 * @description：transition实现类
 */

@Slf4j
public class TransitionImpl<S, E, C> implements Transition<S, E, C> {

    private State<S, E, C> source;

    private State<S, E, C> target;

    private E event;

    private Condition<C> condition;

    private Action<S, E, C> action;

    private TransitionType type = TransitionType.EXTERNAL;

    @Override
    public State<S, E, C> getSource() {
        return source;
    }

    @Override
    public void setSource(State<S, E, C> source) {
        this.source = source;
    }

    @Override
    public State<S, E, C> getTarget() {
        return target;
    }

    @Override
    public void setTarget(State<S, E, C> target) {
        this.target = target;
    }

    @Override
    public E getEvent() {
        return event;
    }

    @Override
    public void setEvent(E event) {
        this.event = event;
    }

    @Override
    public Condition<C> getCondition() {
        return condition;
    }

    @Override
    public void setCondition(Condition<C> condition) {
        this.condition = condition;
    }

    @Override
    public Action<S, E, C> getAction() {
        return action;
    }

    @Override
    public void setAction(Action<S, E, C> action) {
        this.action = action;
    }

    @Override
    public TransitionType getType() {
        return type;
    }

    @Override
    public void setType(TransitionType type) {
        this.type = type;
    }

    @Override
    public State<S, E, C> transit(C ctx) {
        log.debug("Do transition: " + this);
        this.verify();
        if (null == condition || condition.isSatisfied(ctx)) {
            if (null != action) {
                action.execute(source.getId(), target.getId(), event, ctx);
            }
            return target;
        }
        log.debug("Condition is not satisfied, stay at the " + source + " state ");
        return source;
    }

    private void verify() {
        if (type == TransitionType.INTERNAL && source != target) {
            throw new BaseException(String.format("Internal transition source state '%s' " +
                    "and target state '%s' must be same.", source, target));
        }
    }
}
