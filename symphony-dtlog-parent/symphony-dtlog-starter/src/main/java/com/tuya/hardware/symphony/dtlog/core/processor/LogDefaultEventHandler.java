package com.tuya.hardware.symphony.dtlog.core.processor;

import com.alibaba.fastjson.JSON;
import com.tuya.hardware.symphony.dtlog.core.disruptor.LogEventHandler;
import com.tuya.hardware.symphony.dtlog.event.LogEvent;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * @author 一兮
 * @Date 2021/03/15
 * @Description
 */
@Slf4j
public class LogDefaultEventHandler implements LogEventHandler<LogEvent> {

    @Resource
    private LogProcessor logProcessor;

    @Override
    public void onEvent(LogEvent logEvent, long sequence, boolean endOfBatch) {
        log.info("【默认handler】-【日志信息为={}】", JSON.toJSONString(logEvent));
        logProcessor.persistLog(logEvent.getLogEventModel());
    }
}
