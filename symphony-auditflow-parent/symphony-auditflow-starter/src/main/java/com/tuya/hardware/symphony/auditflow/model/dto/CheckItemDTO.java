package com.tuya.hardware.symphony.auditflow.model.dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@ToString(callSuper = true)
public class CheckItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 校验关键字
     */
    private String checkKey;

    /**
     * 校验项的值
     */
    private String checkContent;

    /**
     * 校验的状态
     */
    private Integer checkValue;
}
