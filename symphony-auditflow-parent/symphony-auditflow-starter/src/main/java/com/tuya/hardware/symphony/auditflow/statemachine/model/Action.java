package com.tuya.hardware.symphony.auditflow.statemachine.model;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/9/12 4:28 下午
 * @description：动作
 */
public interface Action<S, E, C> {
    /**
     * 用户实现的状态转移成功时的动作
     */
    void execute(S from, S to, E event, C context);
}
