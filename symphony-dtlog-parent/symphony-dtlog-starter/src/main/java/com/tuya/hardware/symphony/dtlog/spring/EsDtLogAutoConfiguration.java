package com.tuya.hardware.symphony.dtlog.spring;

import com.lmax.disruptor.dsl.Disruptor;
import com.tuya.hardware.symphony.dtlog.core.disruptor.*;
import com.tuya.hardware.symphony.dtlog.core.mapper.TLogEsSearchFieldMapper;
import com.tuya.hardware.symphony.dtlog.core.mapper.TLogTemplateContentFieldMapper;
import com.tuya.hardware.symphony.dtlog.core.mapper.TLogTemplateMapper;
import com.tuya.hardware.symphony.dtlog.core.mapper.TLogTemplateSearchFieldMapper;
import com.tuya.hardware.symphony.dtlog.core.processor.LogDefaultEventHandler;
import com.tuya.hardware.symphony.dtlog.event.LogEvent;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author 一兮
 * @Date 2021/03/15
 * @Description es动态表单日志
 */
@Configuration
@ComponentScan("com.tuya.hardware.symphony.dtlog.core")
public class EsDtLogAutoConfiguration {
    private static final int bufferSize = 1024;

    @Bean
    public Disruptor disruptor(LogEventFactory logEventFactory, LogProducerThreadFactory logProducerThreadFactory,
                               LogEventHandler logEventHandler) {
        Disruptor<LogEvent> disruptor = new Disruptor<>(logEventFactory, bufferSize, logProducerThreadFactory);
        // Connect the handler
        disruptor.handleEventsWith(logEventHandler);
        // Start the Disruptor, starts all threads running
        disruptor.start();
        return disruptor;
    }

    @Bean
    public LogConfiguration logConfiguration(Disruptor disruptor, LogEventProducerWithTranslator logEventProducerWithTranslator) {
        return new LogConfiguration(disruptor.getRingBuffer(), logEventProducerWithTranslator);
    }

    @Bean
    public LogEventFactory logEventFactory() {
        return new LogEventFactory();
    }

    @Bean
    @ConditionalOnMissingBean(LogEventHandler.class)
    public LogEventHandler logEventHandler() {
        return new LogDefaultEventHandler();
    }

    @Bean
    public LogEventProducerWithTranslator logEventProducerWithTranslator() {
        return new LogEventProducerWithTranslator();
    }

    @Bean
    public LogProducerThreadFactory logProducerThreadFactory() {
        return new LogProducerThreadFactory();
    }

    @Bean
    public MapperFactoryBean<TLogEsSearchFieldMapper> tLogEsSearchFieldMapper(SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<TLogEsSearchFieldMapper> factoryBean = new MapperFactoryBean<>();
        factoryBean.setMapperInterface(TLogEsSearchFieldMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }

    @Bean
    public MapperFactoryBean<TLogTemplateContentFieldMapper> tLogTemplateContentFieldMapper(SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<TLogTemplateContentFieldMapper> factoryBean = new MapperFactoryBean<>();
        factoryBean.setMapperInterface(TLogTemplateContentFieldMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }

    @Bean
    public MapperFactoryBean<TLogTemplateMapper> tLogTemplateMapper(SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<TLogTemplateMapper> factoryBean = new MapperFactoryBean<>();
        factoryBean.setMapperInterface(TLogTemplateMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }

    @Bean
    public MapperFactoryBean<TLogTemplateSearchFieldMapper> tLogTemplateSearchFieldMapper(SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<TLogTemplateSearchFieldMapper> factoryBean = new MapperFactoryBean<>();
        factoryBean.setMapperInterface(TLogTemplateSearchFieldMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }
}
