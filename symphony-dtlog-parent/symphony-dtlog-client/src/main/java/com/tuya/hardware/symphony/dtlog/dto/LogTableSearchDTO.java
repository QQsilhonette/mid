package com.tuya.hardware.symphony.dtlog.dto;

import com.tuya.hardware.symphony.dtlog.base.DTO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
public class LogTableSearchDTO extends DTO {

    private String fieldName;

    private String esField;

    private String searchName;

    private String searchType;

    private Map<String, String> options;
}
