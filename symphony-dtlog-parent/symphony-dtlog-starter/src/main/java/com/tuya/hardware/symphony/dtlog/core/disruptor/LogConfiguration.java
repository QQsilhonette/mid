package com.tuya.hardware.symphony.dtlog.core.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.tuya.hardware.symphony.dtlog.event.LogEvent;

/**
 * @author 一兮
 * @Date 2021/03/15
 * @Description
 */
public class LogConfiguration {
    private RingBuffer<LogEvent> ringBuffer;
    private LogEventProducerWithTranslator logEventProducerWithTranslator;

    private LogConfiguration() {
    }

    public LogConfiguration(RingBuffer<LogEvent> ringBuffer, LogEventProducerWithTranslator logEventProducerWithTranslator) {
        this.ringBuffer = ringBuffer;
        this.logEventProducerWithTranslator = logEventProducerWithTranslator;
    }

    public RingBuffer<LogEvent> getRingBuffer() {
        return ringBuffer;
    }

    public LogEventProducerWithTranslator getLogEventProducerWithTranslator() {
        return logEventProducerWithTranslator;
    }
}
