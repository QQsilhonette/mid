package com.tuya.hardware.symphony.auditflow.service.check.impl;

import com.tuya.hardware.symphony.auditflow.service.check.CheckStrategy;
import com.tuya.hardware.symphony.framework.utils.MidSpringContextUtil;

public class CheckStrategyFactory {
    /**
     * 通过校验项类型策略具体实现类
     */
    public static CheckStrategy getCheckStrategy(String beanName) {
        return MidSpringContextUtil.getBean(beanName, CheckStrategy.class);
    }
}