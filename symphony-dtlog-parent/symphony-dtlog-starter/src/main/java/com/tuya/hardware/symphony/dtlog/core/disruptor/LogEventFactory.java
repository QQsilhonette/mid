package com.tuya.hardware.symphony.dtlog.core.disruptor;

import com.lmax.disruptor.EventFactory;
import com.tuya.hardware.symphony.dtlog.event.LogEvent;

/**
 * @author 一兮
 * @Date 2021/03/15
 * @Description
 */
public class LogEventFactory implements EventFactory<LogEvent> {
    @Override
    public LogEvent newInstance() {
        return new LogEvent();
    }
}
