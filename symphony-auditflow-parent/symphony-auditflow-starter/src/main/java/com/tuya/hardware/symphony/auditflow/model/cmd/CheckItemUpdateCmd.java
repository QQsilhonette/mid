package com.tuya.hardware.symphony.auditflow.model.cmd;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * description: 确认项更新
 * create time: 2020/10/27 3:37 下午
 *
  * @Param: null
 * @return
 */
@Getter
@Setter
@Accessors(chain = true)
public class CheckItemUpdateCmd implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ref id
     */
    @NotNull
    private Long id;

    /**
     * 确认项
     */
    @NotNull
    private String checkKey;

    /**
     * 确认状态
     */
    @NotNull
    private Integer checkValue;
}
