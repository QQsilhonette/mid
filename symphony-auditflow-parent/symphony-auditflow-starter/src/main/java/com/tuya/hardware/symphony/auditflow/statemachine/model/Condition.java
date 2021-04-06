package com.tuya.hardware.symphony.auditflow.statemachine.model;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/9/12 4:28 下午
 * @description：触发条件
 */
public interface Condition<C> {
    /**
     * 用户实现的条件判断
     * @param context
     * @return
     */
    boolean isSatisfied(C context);
}
