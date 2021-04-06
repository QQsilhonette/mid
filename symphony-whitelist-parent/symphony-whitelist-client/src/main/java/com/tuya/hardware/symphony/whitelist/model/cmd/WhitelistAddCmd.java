package com.tuya.hardware.symphony.whitelist.model.cmd;

import com.tuya.hardware.symphony.whitelist.model.enums.WhitelistTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author: 旋影
 * @create: 2020-12-02
 * @description:
 **/
@Getter
@Setter
@Accessors(chain = true)
public class WhitelistAddCmd {

    /**
     * 白名单类型
     * @see WhitelistTypeEnum
     */
    @NotNull
    private Integer type;

    /**
     * iot账号 / 商品pid
     */
    @NotNull
    private String refValue;

    /**
     * 关联主键id
     */
    @NotNull
    private Long refId;

    /**
     * 关联类型
     */
    @NotNull
    private String refType;

    /**
     * 修改人
     */
    @NotNull
    private String modifier;
}
