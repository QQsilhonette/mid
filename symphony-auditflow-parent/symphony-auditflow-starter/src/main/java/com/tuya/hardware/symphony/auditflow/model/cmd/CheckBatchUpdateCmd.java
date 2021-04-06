package com.tuya.hardware.symphony.auditflow.model.cmd;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * description: 开发审核状态更新
 * create time: 2020/10/27 3:37 下午
 *
  * @Param: null
 * @return
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class CheckBatchUpdateCmd implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ref id
     */
    @NotNull
    private Long refId;

    @NotNull
    private String refType;

    @NotNull
    private Integer checkValue;

    private List<String> checkKeys;
}
