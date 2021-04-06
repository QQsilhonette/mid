package com.tuya.hardware.symphony.auditflow.model.cmd;

import com.tuya.hardware.symphony.auditflow.enums.audit.AuditStateEnum;
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
public class AudStatusUpdateCmd implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ref id
     */
    @NotNull
    private Long id;

    /**
     * 开发状态
     * @see AuditStateEnum
     */
    @NotNull
    private String auditStatus;

    /**
     * 扩展字段
     */
    private String extension;

    /**
     * 提交人staffId
     */
    private String submitUser;

    /**
     * 审批流key
     */
    private String auditKey;

    /**
     * 审批内容
     */
    private String auditContent;

    /**
     * 审批内容
     */
    private String auditReason;

    /**
     * 撤回状态下需传入确认项，将确认项退回
     */
    private List<String> checkKeys;
}
