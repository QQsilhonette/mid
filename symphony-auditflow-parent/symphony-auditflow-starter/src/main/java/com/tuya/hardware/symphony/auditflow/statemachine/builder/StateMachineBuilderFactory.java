package com.tuya.hardware.symphony.auditflow.statemachine.builder;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/9/12 4:28 下午
 * @description：状态机构造器工厂类
 */
public class StateMachineBuilderFactory {
    public static<S, E, C> StateMachineBuilder<S, E, C> create() {
        return new StateMachineBuilderImpl<>();
    }
}
