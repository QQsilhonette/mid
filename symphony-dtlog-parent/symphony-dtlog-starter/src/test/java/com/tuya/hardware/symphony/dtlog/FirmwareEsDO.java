package com.tuya.hardware.symphony.dtlog;

import com.tuya.hardware.symphony.dtlog.core.aspect.LogField;
import com.tuya.hardware.symphony.dtlog.core.aspect.LogTemplate;
import com.tuya.hardware.symphony.dtlog.event.LogEventModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 一兮
 * @Date 2021/03/16
 * @Description
 */
@Getter
@Setter
@Accessors(chain = true)
@LogTemplate(key = "electronic_log_firmware_base")
public class FirmwareEsDO extends LogEventModel {
    @LogField(searchKey = "en_text_1", name = "固件key")
    private String firmwareKey;
    @LogField(searchKey = "cn_text_1", name = "固件名称")
    private String firmwareName;
    @LogField(searchKey = "en_text_2", name = "固件标识")
    private String identification;
    @LogField(name = "固件分类")
    private String channelCategory;
    @LogField(name = "固件类型")
    private String category;
    @LogField(name = "flash大小")
    private String flashSize;
    @LogField(name = "pid绑定关系")
    private String pidBindingRelation;
    @LogField(name = "联网方式")
    private List<String> communicationType;
    @LogField(name = "品类标签")
    private String categoryLabel;
    @LogField(name = "负责人")
    private String principal;
    @LogField(name = "备注说明")
    private String remark;
}
