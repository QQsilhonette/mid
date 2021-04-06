package com.tuya.hardware.symphony.auditflow.service.check;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/10/26 5:56 下午
 * @description：CheckKeyAnalyse
 */
public interface CheckStrategy {

    /**
     * 确认项内容解析
     * @param refId
     * @param refType
     * @param checkKey
     * @return
     */
    String analyse(Long refId, String refType, String checkKey);
}
