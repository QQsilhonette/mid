package com.tuya.hardware.symphony.auditflow.model.cmd;

import com.tuya.hardware.symphony.auditflow.enums.audit.AuditStateEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
public class AudDeleteCmd implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ref id
     */
    @NotNull
    private Long id;
}
