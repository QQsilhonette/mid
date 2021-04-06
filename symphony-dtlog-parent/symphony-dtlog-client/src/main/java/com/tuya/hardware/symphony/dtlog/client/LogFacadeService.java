package com.tuya.hardware.symphony.dtlog.client;

import com.tuya.hardware.symphony.dtlog.cmd.LogDataQueryCmd;
import com.tuya.hardware.symphony.dtlog.cmd.LogHeaderQueryCmd;
import com.tuya.hardware.symphony.dtlog.dto.EsLogPagination;
import com.tuya.hardware.symphony.dtlog.dto.LogTableDTO;
import com.tuya.hardware.symphony.dtlog.dto.LogTableHeaderDTO;
import com.tuya.hardware.symphony.dtlog.dto.LogTableSearchDTO;

import java.util.List;

/**
 * @author 一兮
 * @Date 2021/03/15
 * @Description
 */
public interface LogFacadeService<T> {

    void publishEvent(T logEventModel);

    boolean persistLog(T logEventModel);

    EsLogPagination data(LogDataQueryCmd logDataQueryCmd);

    List<LogTableHeaderDTO> header(LogHeaderQueryCmd logHeaderQueryCmd);

    List<LogTableDTO> templates();

    List<LogTableSearchDTO> searchItems(String templateKey);
}
