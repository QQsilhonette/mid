package com.tuya.hardware.symphony.dtlog.dto;

import com.tuya.hardware.symphony.dtlog.base.DTO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author 一兮
 * @Date 2021/01/07
 * @Description
 */
@Getter
@Setter
@Accessors(chain = true)
public class LogTableHeaderDTO extends DTO {
    private String title;
    private String dataIndex;
    private String key;
}
