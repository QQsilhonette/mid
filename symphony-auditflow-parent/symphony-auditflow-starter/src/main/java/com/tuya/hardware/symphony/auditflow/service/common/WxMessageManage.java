package com.tuya.hardware.symphony.auditflow.service.common;

import com.tuya.basic.client.domain.intranet.MessageDO;
import com.tuya.basic.client.service.intranet.IintranetNotifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/7/18 2:13 下午
 * @description：微信消息
 */

@Slf4j
@Service
public class WxMessageManage {

    @Resource
    IintranetNotifyService intranetNotifyService;

    /**
     * 发送微信消息
     * @param dialogue
     * @param toUser
     * @param content
     */
    public void wxSendMsg(String dialogue, String toUser, String content) {
        MessageDO messageDO = new MessageDO();
        messageDO.setToUser(toUser);
        messageDO.setTextStr(content);
        intranetNotifyService.sendMessageByJobNum(dialogue, messageDO);
        log.info("wxSendMsg toUser:{} content:{}", toUser, content);
    }
}
