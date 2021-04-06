package com.tuya.hardware.symphony.dtlog.cmd;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author 一兮
 * @Date 2021/01/07
 * @Description
 */
@Getter
@Setter
@Accessors(chain = true)
public class LogHeaderQueryCmd implements Serializable {
    private String formKey;
    protected Integer platform;
    protected String tenantId;
}
