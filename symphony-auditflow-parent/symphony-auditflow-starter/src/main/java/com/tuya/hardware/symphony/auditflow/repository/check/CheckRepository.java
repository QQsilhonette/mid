package com.tuya.hardware.symphony.auditflow.repository.check;

import com.tuya.hardware.symphony.auditflow.infrastructure.dataobject.CheckDO;
import com.tuya.hardware.symphony.auditflow.infrastructure.mapper.CheckMapper;
import com.tuya.hardware.symphony.framework.base.BaseRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/10/26 3:06 下午
 * @description：确认项Repo
 */
@Repository
public class CheckRepository extends BaseRepository {

    @Resource
    private CheckMapper checkMapper;

    public void add(CheckDO checkDO) {
        checkDO.setId(populateId(checkDO));
        checkMapper.insert(checkDO);
    }

    public List<CheckDO> getByRefIdAndRefType(Long refId, String refType) {
        return checkMapper.getByRefIdAndRefType(refId, refType);
    }

    public CheckDO getByRefAndCheckKey(Long refId, String refType, String checkKey) {
        return checkMapper.getByRefAndCheckKey(refId, refType, checkKey);
    }

    public void updateValByRefAndKey(Long refId, String refType, String checkKey, Integer checkValue) {
        checkMapper.updateValByRefAndKey(refId, refType, checkKey, checkValue);
    }

    public void updateValByRefAndCheckKeys(Long refId, String refType, List<String> checkKeys, Integer checkValue) {
        checkMapper.updateValByRefAndCheckKeys(refId, refType, checkKeys, checkValue);
    }
}
