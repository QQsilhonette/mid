package com.tuya.hardware.symphony.dtlog.dto;

import com.tuya.hardware.symphony.dtlog.base.DTO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class LogTableDTO extends DTO {
    private String key;
    private String name;
}
