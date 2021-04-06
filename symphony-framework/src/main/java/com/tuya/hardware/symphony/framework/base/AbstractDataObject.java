package com.tuya.hardware.symphony.framework.base;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class AbstractDataObject implements Serializable {
    private static final long serialVersionUID = -1L;
    protected Long id;
    protected Long isDelete;
    protected Long gmtCreate;
    protected Long gmtModified;
}