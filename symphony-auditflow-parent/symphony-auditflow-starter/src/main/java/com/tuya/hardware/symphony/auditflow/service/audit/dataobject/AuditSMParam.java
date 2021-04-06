package com.tuya.hardware.symphony.auditflow.service.audit.dataobject;//package com.tuya.symphony.auditflow.parent.starter.service.audit;

import com.tuya.hardware.symphony.auditflow.enums.audit.AuditEventEnum;
import com.tuya.hardware.symphony.auditflow.enums.audit.AuditStateEnum;
import com.tuya.sparta.client.v2.domain.staff.OpenStaffOrganizationVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/11/13 9:38 上午
 * @description：审核状态机参数
 */

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AuditSMParam {

    /**
     * 审核起始状态
     */
    private AuditStateEnum startState;

    /**
     * 审核终止状态
     */
    private AuditStateEnum endState;

    /**
     * 审核事件类型
     */
    private AuditEventEnum event;

    /**
     * 审核人
     */
    private OpenStaffOrganizationVO auditUser;

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
