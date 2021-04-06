package com.tuya.hardware.symphony.whitelist.infrastructure.mapper;

import com.tuya.hardware.symphony.whitelist.infrastructure.dataobject.EmbeddedDataWhitelistDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @mbg.generated
 * 表名: t_embedded_data_whitelist
 * @date 2020/09/26
 */
public interface EmbeddedDataWhitelistMapper {

    int insert(EmbeddedDataWhitelistDO record);

    EmbeddedDataWhitelistDO getByRefAndValueAndType(@Param("refType") String refType, @Param("refId") Long refId,
                                                          @Param("refValue") String refValue, @Param("type") Integer type);
    List<EmbeddedDataWhitelistDO> getByRef(@Param("refType") String refType, @Param("refId") Long refId);

    List<EmbeddedDataWhitelistDO> getByRefs(@Param("refType") String refType, @Param("refIds") List<Long> refIds);

    void deleteById(Long id);

    EmbeddedDataWhitelistDO queryById(Long id);
}