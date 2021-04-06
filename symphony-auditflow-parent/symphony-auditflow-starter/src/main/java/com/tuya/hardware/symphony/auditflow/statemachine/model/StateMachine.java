package com.tuya.hardware.symphony.auditflow.statemachine.model;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/9/12 4:28 下午
 * @description：状态机
 */
public interface StateMachine<S, E, C> {

    /**
     * 触发状态机
     * @param sourceState
     * @param event
     * @param ctx
     * @return
     */
    S fireEvent(S sourceState, E event, C ctx);

    String getMachineId();

    /**
     * 打印状态机全流程
     * @return
     */
    void showStateMachine();

    /**
     * 生成状态puml
     * @return
     */
    String generatePlantUML();

    /**
     * 流程字符串拼接
     * @param visitor
     * @return
     */
    String accept(Visitor visitor);
}
