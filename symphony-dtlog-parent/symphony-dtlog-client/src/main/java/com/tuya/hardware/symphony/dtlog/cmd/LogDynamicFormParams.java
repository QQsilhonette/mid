package com.tuya.hardware.symphony.dtlog.cmd;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author 一兮
 * @Date 2020/10/01
 * @Description
 */
@Getter
@Setter
@Accessors(chain = true)
public class LogDynamicFormParams {
    @NotNull
    private Map<String, Object> formDataMap;
    @NotNull
    private String formKey;
}
