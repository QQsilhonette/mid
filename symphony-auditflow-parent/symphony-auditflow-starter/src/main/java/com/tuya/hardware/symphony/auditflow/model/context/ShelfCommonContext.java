package com.tuya.hardware.symphony.auditflow.model.context;

import com.tuya.hardware.symphony.auditflow.enums.shelf.ShelfEventEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data
public class ShelfCommonContext {

    /**
     * 关联主键
     */
    private Long refId;

    /**
     * 关联类型
     */
    private String refType;

    /**
     * 审核类型
     */
    private String auditType;

    /**
     * 上架校验项
     */
    private List<String> onShelfCheckTypes;

    /**
     * 下架校验项
     */
    private List<String> offShelfCheckTypes;

    /**
     * 上架事件类型
     */
    private ShelfEventEnum eventType;

    /**
     * 下架时间戳
     */
    private Long stopTimeStamp;

    /**
     * 下架是否需要微信通知
     */
    private boolean needWxNotify;

    /**
     * 发送微信号
     */
    private String dialogue;

    /**
     * 微信通知用户工号列表（竖线分隔）
     */
    private String toUser;

    /**
     * 下架文案
     */
    private String offShelfReason;
}