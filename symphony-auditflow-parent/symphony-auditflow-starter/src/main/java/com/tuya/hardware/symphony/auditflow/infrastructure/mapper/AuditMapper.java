package com.tuya.hardware.symphony.auditflow.infrastructure.mapper;

import com.tuya.hardware.symphony.auditflow.infrastructure.dataobject.AuditDO;
import org.apache.ibatis.annotations.Param;

/**
 * @mbg.generated
 * 表名: t_audit
 * @date 2020/10/26
 */
public interface AuditMapper {

    /**
     * @mbg.generated
     */
    int insert(AuditDO record);

    /**
     * @mbg.generated
     */
    AuditDO selectByPrimaryKey(Long id);

    /**
     * @mbg.generated
     */
    int updateByPrimaryKey(AuditDO record);

    AuditDO getByRefAndType(@Param("refId") Long refId, @Param("refType") String refType, @Param("auditType") String auditType);

    int updateStatusByRefAndType(@Param("refId") Long refId, @Param("refType") String refType,
                                     @Param("auditType") String auditType, @Param("auditStatus") String auditStatus);

    void deleteByRefAndType(@Param("refId") Long refId, @Param("refType") String refType,
                                     @Param("auditType") String auditType);

    void updateStatusByTraceId(@Param("traceId") String traceId, @Param("auditStatus") String auditStatus);

    void deleteByTraceId(@Param("traceId") String traceId);

    AuditDO getByTraceId(@Param("traceId") String traceId);
}