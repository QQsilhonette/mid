//package com.tuya.hardware.symphony.dtlog;
//
//import com.lmax.disruptor.dsl.Disruptor;
//import com.tuya.hardware.symphony.dtlog.core.disruptor.*;
//import com.tuya.hardware.symphony.dtlog.core.service.LogFacadeServiceImpl;
//import com.tuya.hardware.symphony.dtlog.event.LogEventModel;
//import com.tuya.hardware.symphony.dtlog.spring.EsDtLogAutoConfiguration;
//
//public class LogDisruptorTest {
//
//    public static void main(String[] args) throws Exception {
//        EsDtLogAutoConfiguration esDtLogConfiguration = new EsDtLogAutoConfiguration();
//        LogEventFactory logEventFactory = esDtLogConfiguration.logEventFactory();
//        LogEventHandler logEventHandler = esDtLogConfiguration.logEventHandler();
//        LogEventProducerWithTranslator logEventProducerWithTranslator = esDtLogConfiguration.logEventProducerWithTranslator();
//        LogProducerThreadFactory logProducerThreadFactory = esDtLogConfiguration.logProducerThreadFactory();
//        Disruptor disruptor = esDtLogConfiguration.disruptor(logEventFactory, logProducerThreadFactory, logEventHandler);
//        LogConfiguration logConfiguration = esDtLogConfiguration.logConfiguration(disruptor, logEventProducerWithTranslator);
//
//        LogFacadeServiceImpl logProducerFacadeService = new LogFacadeServiceImpl();
//        logProducerFacadeService.logConfiguration = logConfiguration;
//        for (long l = 0; true; l++) {
//            LogEventModel logEventModel = new LogEventModel();
//            logProducerFacadeService.publishEvent(logEventModel);
//            Thread.sleep(1000);
//        }
//    }
//}
