package com.tuya.hardware.symphony.dtlog.core.disruptor;

import java.util.concurrent.ThreadFactory;

/**
 * @author 一兮
 * @Date 2021/03/15
 * @Description
 */
public class LogProducerThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, "dtlog-log-thread");
    }
}
