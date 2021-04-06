package com.tuya.hardware.symphony.dtlog.core.service;

import com.tuya.hardware.symphony.dtlog.client.LogFacadeService;
import com.tuya.hardware.symphony.dtlog.cmd.LogDataQueryCmd;
import com.tuya.hardware.symphony.dtlog.cmd.LogHeaderQueryCmd;
import com.tuya.hardware.symphony.dtlog.core.disruptor.LogConfiguration;
import com.tuya.hardware.symphony.dtlog.core.processor.LogProcessor;
import com.tuya.hardware.symphony.dtlog.dto.EsLogPagination;
import com.tuya.hardware.symphony.dtlog.dto.LogTableDTO;
import com.tuya.hardware.symphony.dtlog.dto.LogTableHeaderDTO;
import com.tuya.hardware.symphony.dtlog.dto.LogTableSearchDTO;
import com.tuya.hardware.symphony.dtlog.event.LogEventModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LogFacadeServiceImpl<T extends LogEventModel> implements LogFacadeService<T> {

    @Resource
    public LogConfiguration logConfiguration;

    @Resource
    public LogProcessor logProcessor;

    @Override
    public void publishEvent(T logEventModel) {
        logConfiguration.getRingBuffer().publishEvent(logConfiguration.getLogEventProducerWithTranslator().getTranslator(),
                logEventModel);
    }

    @Override
    public boolean persistLog(T logEventModel) {
        return logProcessor.persistLog(logEventModel);
    }

    @Override
    public EsLogPagination data(LogDataQueryCmd logDataQueryCmd) {
        return logProcessor.queryPageLog(logDataQueryCmd.getParams().getFormKey(),
                logDataQueryCmd.getParams().getFormDataMap(),
                logDataQueryCmd.getLimit(), logDataQueryCmd.getOffset());
    }

    @Override
    public List<LogTableHeaderDTO> header(LogHeaderQueryCmd logHeaderQueryCmd) {
        return logProcessor.queryHeader(logHeaderQueryCmd.getFormKey());
    }

    @Override
    public List<LogTableDTO> templates() {
        return logProcessor.queryTemplates();
    }

    @Override
    public List<LogTableSearchDTO> searchItems(String templateKey) {
        return logProcessor.querySearchItems(templateKey);
    }
}