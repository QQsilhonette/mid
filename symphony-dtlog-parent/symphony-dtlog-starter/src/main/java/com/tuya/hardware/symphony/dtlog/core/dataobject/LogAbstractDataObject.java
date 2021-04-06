package com.tuya.hardware.symphony.dtlog.core.dataobject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author wujun
 * create time: 12:49 下午
 */
@Getter
@Setter
@ToString
public abstract class LogAbstractDataObject implements Serializable {
    private static final long serialVersionUID = -1L;

    protected Long id;

    protected Long isDelete;

    protected Long gmtCreate;

    protected Long gmtModified;
}