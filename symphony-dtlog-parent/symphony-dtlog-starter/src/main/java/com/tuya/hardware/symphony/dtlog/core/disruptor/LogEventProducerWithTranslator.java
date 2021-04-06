package com.tuya.hardware.symphony.dtlog.core.disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.tuya.hardware.symphony.dtlog.event.LogEvent;
import com.tuya.hardware.symphony.dtlog.event.LogEventModel;

public class LogEventProducerWithTranslator {
    private static final EventTranslatorOneArg<LogEvent, LogEventModel> TRANSLATOR =
            new EventTranslatorOneArg<LogEvent, LogEventModel>() {
                public void translateTo(LogEvent event, long sequence, LogEventModel logEventModel) {
                    event.setLogEventModel(logEventModel);
                }
            };

    public EventTranslatorOneArg getTranslator() {
        return TRANSLATOR;
    }
}