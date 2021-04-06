package com.tuya.hardware.symphony.whitelist.service;

import com.tuya.hardware.symphony.whitelist.model.cmd.WhitelistAddCmd;
import com.tuya.hardware.symphony.whitelist.model.dto.WhitelistListDTO;
import com.tuya.hardware.symphony.whitelist.model.dto.WhitelistListSimpleDTO;

import java.util.List;
import java.util.Map;

public interface IWhitelistService {

    /**
     * 新增白名单
     * @param whitelistAddCmd
     * @return
     */
    Long addWhitelist(WhitelistAddCmd whitelistAddCmd);

    /**
     * 删除白名单
     * @param id
     */
    void delWhitelist(Long id);

    /**
     * 查询白名单列表
     * @param refType
     * @param refId
     * @return
     */
    List<WhitelistListDTO> queryWhitelist(String refType, Long refId);

    /**
     * 批量查询白名单列表
     * @param refType
     * @param refIds
     * @return
     */
    Map<Long, List<WhitelistListSimpleDTO>> queryWhiteListMap(String refType, List<Long> refIds);
}
