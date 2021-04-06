package com.tuya.hardware.symphony.framework.base;

import com.tuya.hardware.symphony.framework.utils.MidSpringContextUtil;
import com.tuya.middleware.dubai.dao.SequenceGenerator;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/12/23 7:51 下午
 * @description：repo基类
 */
public abstract class BaseRepository {

    protected long populateId(AbstractDataObject insertDataObject) {
        SequenceGenerator sequenceGenerator = (SequenceGenerator) MidSpringContextUtil.getBean(SequenceGenerator.class);
        return sequenceGenerator.next(this.buildKey(insertDataObject));
    }

    private String buildKey(AbstractDataObject insertDataObject) {
        String name = insertDataObject.getClass().getSimpleName();
        return name.toLowerCase().substring(0, name.length() - 3) + "_id";
    }
}
