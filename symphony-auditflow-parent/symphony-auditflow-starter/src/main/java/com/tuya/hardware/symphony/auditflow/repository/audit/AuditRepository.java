package com.tuya.hardware.symphony.auditflow.repository.audit;

import com.tuya.hardware.symphony.auditflow.infrastructure.dataobject.AuditDO;
import com.tuya.hardware.symphony.auditflow.infrastructure.mapper.AuditMapper;
import com.tuya.hardware.symphony.framework.base.BaseRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/10/26 3:06 下午
 * @description：审核Repo
 */
@Repository
public class AuditRepository extends BaseRepository {

    @Resource
    private AuditMapper auditMapper;

    public void add(AuditDO auditDO) {
        auditDO.setId(populateId(auditDO));
        auditMapper.insert(auditDO);
    }

    public AuditDO getByRefAndType(Long refId, String refType, String auditType) {
        return auditMapper.getByRefAndType(refId, refType, auditType);
    }

    public void updateStatusByRefAndType(Long refId, String refType, String auditType, String auditStatus) {
        auditMapper.updateStatusByRefAndType(refId, refType, auditType, auditStatus);
    }

    public void deleteByRefAndType(Long refId, String refType, String auditType) {
        auditMapper.deleteByRefAndType(refId, refType, auditType);
    }

    public void deleteByTraceId(String traceId) {
        auditMapper.deleteByTraceId(traceId);
    }

    public void updateStatusByTraceId(String traceId, String auditStatus) {
        auditMapper.updateStatusByTraceId(traceId, auditStatus);
    }

    public AuditDO getByTraceId(String traceId) {
        return auditMapper.getByTraceId(traceId);
    }
}
