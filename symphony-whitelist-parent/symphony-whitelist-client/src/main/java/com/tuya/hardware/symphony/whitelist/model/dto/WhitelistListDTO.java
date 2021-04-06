package com.tuya.hardware.symphony.whitelist.model.dto;

import com.tuya.hardware.symphony.whitelist.model.enums.WhitelistTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author: 旋影
 * @create: 2020-11-23
 * @description:
 **/
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class WhitelistListDTO {

    private static final long serialVersionUID = -1L;

    /**
     * 白名单类型
     * @see WhitelistTypeEnum
     */
    private Integer type;

    /**
     * iot账号 / 产品pid
     */
    private String refValue;

    /**
     * 公司名
     */
    private String enterpriseName;

    /**
     * 修改人名
     */
    private String modifier;

    /**
     * 创建时间
     */
    private Long gmtCreate;
    /**
     * 白名单id
     */
    private Long id;
}
