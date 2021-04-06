package com.tuya.hardware.symphony.auditflow.infrastructure.mapper;

import com.tuya.hardware.symphony.auditflow.infrastructure.dataobject.CheckDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @mbg.generated
 * 表名: t_check
 * @date 2020/10/26
 */
public interface CheckMapper {

    /**
     * @mbg.generated
     */
    int insert(CheckDO record);

    /**
     * @mbg.generated
     */
    CheckDO selectByPrimaryKey(Long id);

    /**
     * @mbg.generated
     */
    List<CheckDO> getByRefIdAndRefType(@Param("refId") Long refId, @Param("refType") String refType);

    /**
     * @mbg.generated
     */
    int updateByPrimaryKey(CheckDO record);

    CheckDO getByRefAndCheckKey(@Param("refId") Long refId, @Param("refType") String refType, @Param("checkKey") String checkKey);

    int updateValByRefAndKey(@Param("refId") Long refId, @Param("refType") String refType,
                             @Param("checkKey") String checkKey, @Param("checkValue") Integer checkValue);

    int updateValByRefAndCheckKeys(@Param("refId") Long refId, @Param("refType") String refType,
                                   @Param("checkKeys") List<String> checkKeys, @Param("checkValue") Integer checkValue);
}