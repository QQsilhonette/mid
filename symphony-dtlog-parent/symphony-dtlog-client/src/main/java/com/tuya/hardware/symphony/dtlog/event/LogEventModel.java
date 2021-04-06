package com.tuya.hardware.symphony.dtlog.event;

import com.tuya.hardware.symphony.dtlog.base.DTO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author 一兮
 * @Date 2021/03/15
 * @Description
 */
@Getter
@Setter
@Accessors(chain = true)
public class LogEventModel extends DTO {
    private static final long serialVersionUID = 1L;

    public LogEventModel() {
    }

    private String operator;
    private Integer platform;
    private String tenantId;
}
