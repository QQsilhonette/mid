package com.tuya.hardware.symphony.whitelist.repository;

import com.tuya.hardware.symphony.framework.base.BaseRepository;
import com.tuya.hardware.symphony.whitelist.infrastructure.dataobject.EmbeddedDataWhitelistDO;
import com.tuya.hardware.symphony.whitelist.infrastructure.mapper.EmbeddedDataWhitelistMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/10/26 3:06 下午
 * @description：白名单Repo
 */
@Repository
public class WhitelistRepository extends BaseRepository {

    @Resource
    private EmbeddedDataWhitelistMapper embeddedDataWhitelistMapper;

    public void add(EmbeddedDataWhitelistDO embeddedDataWhitelistDO) {
        embeddedDataWhitelistDO.setId(populateId(embeddedDataWhitelistDO));
        embeddedDataWhitelistMapper.insert(embeddedDataWhitelistDO);
    }

    public EmbeddedDataWhitelistDO getByRefAndValueAndType(String refType, Long refId, String refValue, Integer type) {
        return embeddedDataWhitelistMapper.getByRefAndValueAndType(refType, refId, refValue, type);
    }

    public List<EmbeddedDataWhitelistDO> getByRef(String refType, Long refId) {
        return embeddedDataWhitelistMapper.getByRef(refType, refId);
    }

    public List<EmbeddedDataWhitelistDO> getByRefs(String refType, List<Long> refIds) {
        return embeddedDataWhitelistMapper.getByRefs(refType, refIds);
    }

    public void deleteById(Long id) {
        embeddedDataWhitelistMapper.deleteById(id);
    }

    public EmbeddedDataWhitelistDO queryById(Long id) {
       return embeddedDataWhitelistMapper.queryById(id);
    }
}