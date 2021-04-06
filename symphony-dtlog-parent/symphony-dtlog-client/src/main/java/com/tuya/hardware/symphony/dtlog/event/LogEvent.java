package com.tuya.hardware.symphony.dtlog.event;

import com.tuya.hardware.symphony.dtlog.base.DTO;

/**
 * @author 一兮
 * @Date 2021/03/15
 * @Description
 */
public class LogEvent extends DTO {
    private LogEventModel logEventModel;

    public LogEventModel getLogEventModel() {
        return logEventModel;
    }

    public void setLogEventModel(LogEventModel logEventModel) {
        this.logEventModel = logEventModel;
    }
}
