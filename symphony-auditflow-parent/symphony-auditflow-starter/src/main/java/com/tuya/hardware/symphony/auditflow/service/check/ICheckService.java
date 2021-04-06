package com.tuya.hardware.symphony.auditflow.service.check;

import com.tuya.hardware.symphony.auditflow.model.cmd.CheckBatchUpdateCmd;
import com.tuya.hardware.symphony.auditflow.model.cmd.CheckItemUpdateCmd;
import com.tuya.hardware.symphony.auditflow.model.dto.CheckItemDTO;

import java.util.List;
import java.util.Map;

public interface ICheckService {

    /**
     * 获取确认项列表
     * @param refId
     * @param refType
     * @param checkHandlers
     * @return
     */
    List<CheckItemDTO> getCheckList(Long refId, String refType, Map<String, String> checkHandlers);

    /**
     * 更新确认项
     * @param checkItemUpdateCmd
     * @param refType
     */
    void upsertCheckItem(CheckItemUpdateCmd checkItemUpdateCmd, String refType);

    /**
     * 批量更新确认项
     * @param cmd
     */
    void updateValByRefAndCheckKeys(CheckBatchUpdateCmd cmd);
}
