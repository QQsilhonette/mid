package com.tuya.hardware.symphony.auditflow.statemachine.model.impl;

import com.tuya.hardware.symphony.auditflow.statemachine.model.State;
import com.tuya.hardware.symphony.auditflow.statemachine.model.Transition;
import com.tuya.hardware.symphony.auditflow.statemachine.model.Visitor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/9/12 5:43 下午
 * @description：state实现类
 */
@Slf4j
public class StateImpl<S, E, C> implements State<S, E, C> {

    protected final S stateId;
    private HashMap<E, Transition<S, E, C>> transitions = new HashMap<>();

    StateImpl(S stateId) {
        this.stateId = stateId;
    }

    @Override
    public S getId() {
        return stateId;
    }

    @Override
    public Transition<S, E, C> addTransition(E event, State<S, E, C> target, TransitionType transitionType) {
        TransitionImpl<S, E, C> newTransition = new TransitionImpl<>();
        newTransition.setSource(this);
        newTransition.setTarget(target);
        newTransition.setEvent(event);
        newTransition.setType(transitionType);

        log.debug("Begin to add new transition: "+ newTransition);
        transitions.put(event, newTransition);
        return newTransition;
    }

    @Override
    public Optional<Transition<S, E, C>> getTransition(E event) {
        return Optional.ofNullable(transitions.get(event));
    }

    @Override
    public Collection<Transition<S, E, C>> getTransitions() {
        return transitions.values();
    }

    @Override
    public String accept(Visitor visitor) {
        String entry = visitor.visitOnEntry(this);
        String exit = visitor.visitOnExit(this);
        return entry + exit;
    }
}
