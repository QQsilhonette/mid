package com.tuya.hardware.symphony.dtlog.core.disruptor;

import com.lmax.disruptor.EventHandler;

public interface LogEventHandler<LogEvent> extends EventHandler<LogEvent> {
}