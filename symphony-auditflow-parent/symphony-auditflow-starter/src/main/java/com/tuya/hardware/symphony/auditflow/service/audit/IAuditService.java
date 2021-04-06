package com.tuya.hardware.symphony.auditflow.service.audit;

import com.tuya.hardware.symphony.auditflow.model.cmd.AudDeleteCmd;
import com.tuya.hardware.symphony.auditflow.model.cmd.AudStatusUpdateCmd;
import com.tuya.hardware.symphony.auditflow.model.dto.ShelfAudStatusDTO;

public interface IAuditService {

    /**
     * 更新审核状态
     * @param audStatusUpdateCmd
     * @param refType
     * @param auditType
     */
    void updateAudStatus(AudStatusUpdateCmd audStatusUpdateCmd, String refType, String auditType);

    /**
     * 查询审核状态
     * @param shelfAudStatusDTO
     * @param refId
     * @param refType
     * @param auditType
     */
    void getAudStatusDTOAud(ShelfAudStatusDTO shelfAudStatusDTO, Long refId, String refType, String auditType);

    void deleteAudit(AudDeleteCmd cmd, String refType, String auditType);

}
