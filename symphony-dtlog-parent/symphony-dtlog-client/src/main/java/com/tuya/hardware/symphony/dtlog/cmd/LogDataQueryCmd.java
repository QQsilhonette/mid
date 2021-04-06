package com.tuya.hardware.symphony.dtlog.cmd;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
public class LogDataQueryCmd implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private LogDynamicFormParams params;
    private Integer limit;
    private Integer offset;
    protected Integer platform;
    protected String tenantId;
}
