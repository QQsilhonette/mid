package com.tuya.hardware.symphony.whitelist.service;

import com.google.common.collect.Lists;
import com.tuya.basic.client.domain.exception.BaseException;
import com.tuya.hardware.symphony.whitelist.infrastructure.dataobject.EmbeddedDataWhitelistDO;
import com.tuya.hardware.symphony.whitelist.model.cmd.WhitelistAddCmd;
import com.tuya.hardware.symphony.whitelist.model.dto.WhitelistListDTO;
import com.tuya.hardware.symphony.whitelist.model.dto.WhitelistListSimpleDTO;
import com.tuya.hardware.symphony.whitelist.model.enums.WhitelistTypeEnum;
import com.tuya.hardware.symphony.whitelist.repository.WhitelistRepository;
import com.tuya.sparta.client.v2.IOpenStaffQueryService;
import com.tuya.sparta.client.v2.domain.staff.OpenStaffOrganizationVO;
import com.tuya.talos.client.domain.user.vo.BusinessUserVO;
import com.tuya.talos.client.enterprise.IDevelopUserService;
import com.tuya.talos.client.enterprise.vo.UserDeveloperDetailVO;
import com.tuya.talos.client.user.IBusinessUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/12/17 2:40 下午
 * @description：白名单service
 */
@Service
public class WhitelistService implements IWhitelistService {

    @Resource
    private IBusinessUserService businessUserService;
    @Resource
    private IDevelopUserService developUserService;
    @Resource
    private IOpenStaffQueryService openStaffQueryService;
    @Resource
    private WhitelistRepository whitelistRepository;

    @Override
    public Long addWhitelist(WhitelistAddCmd whitelistAddCmd) {
        String value = checkAndGetValue(whitelistAddCmd.getType(), whitelistAddCmd.getRefValue());
        EmbeddedDataWhitelistDO whitelistDO = whitelistRepository.getByRefAndValueAndType(whitelistAddCmd.getRefType(),
                whitelistAddCmd.getRefId(), value, whitelistAddCmd.getType());
        if (Objects.nonNull(whitelistDO)) {
            throw new BaseException("已经存在白名单信息！");
        }
        EmbeddedDataWhitelistDO whitelist = new EmbeddedDataWhitelistDO()
                .setRefValue(value)
                .setType(whitelistAddCmd.getType())
                .setModifier(whitelistAddCmd.getModifier())
                .setRefId(whitelistAddCmd.getRefId())
                .setRefType(whitelistAddCmd.getRefType());
        whitelistRepository.add(whitelist);
        return whitelist.getId();
    }

    @Override
    public void delWhitelist(Long id) {
        whitelistRepository.deleteById(id);
    }

    @Override
    public List<WhitelistListDTO> queryWhitelist(String refType, Long refId) {
        List<EmbeddedDataWhitelistDO> whitelistDOS = whitelistRepository.getByRef(refType, refId);

        List<String> uids = whitelistDOS.stream()
                .filter(embeddedDataWhitelistDO -> WhitelistTypeEnum.isIotAccount(embeddedDataWhitelistDO.getType()))
                .map(EmbeddedDataWhitelistDO::getRefValue)
                .distinct()
                .collect(Collectors.toList());

        List<String> pids = whitelistDOS.stream()
                .filter(embeddedDataWhitelistDO -> WhitelistTypeEnum.isProductId(embeddedDataWhitelistDO.getType()))
                .map(EmbeddedDataWhitelistDO::getRefValue)
                .distinct()
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(whitelistDOS)) {
            return Lists.newArrayList();
        }

        List<WhitelistListDTO> whitelistListDTOS = whitelistDOS.stream()
                .map(whitelistDO -> {
                    Integer type = whitelistDO.getType();
                    String refValue = whitelistDO.getRefValue();
                    OpenStaffOrganizationVO staff = openStaffQueryService.queryStaffByStaffId(whitelistDO.getModifier());
                    String modifier = Optional.ofNullable(getWholeName(staff.getTuyaName(), staff.getName()))
                            .orElse(null);
                    WhitelistListDTO whitelistUserDTO = new WhitelistListDTO()
                            .setId(whitelistDO.getId())
                            .setModifier(modifier)
                            .setGmtCreate(whitelistDO.getGmtCreate())
                            .setRefValue(whitelistDO.getRefValue())
                            .setType(whitelistDO.getType());

                    if (WhitelistTypeEnum.isProductId(type)) {
                        // 暂不实现
                    } else if (WhitelistTypeEnum.isIotAccount(type)) {
                        Map<String, String> developersMap = developUsersParse(developUserService.getByMapUids(new HashSet<>(uids)));
                        Map<String, String> businessUsersMap = businessUsersParse(businessUserService.getByUids(uids));
                        if (!developersMap.isEmpty() && developersMap.containsKey(refValue)) {
                            String enterpriseName = developersMap.get(refValue);
                            whitelistUserDTO.setEnterpriseName(enterpriseName);
                        }
                        if (!businessUsersMap.isEmpty() && businessUsersMap.containsKey(refValue)) {
                            String username = businessUsersMap.get(refValue);
                            whitelistUserDTO.setRefValue(username);
                        }
                    }
                    return whitelistUserDTO;
                }).collect(Collectors.toList());

        return whitelistListDTOS;
    }

    @Override
    public Map<Long, List<WhitelistListSimpleDTO>> queryWhiteListMap(String refType, List<Long> refIds) {
        List<EmbeddedDataWhitelistDO> whitelistDOS = whitelistRepository.getByRefs(refType, refIds);
        Map<Long, List<WhitelistListSimpleDTO>> whitelistListSimpleDTOMap = whitelistDOS
                .stream()
                .map(embeddedDataWhitelistDO -> {
                    WhitelistListSimpleDTO whitelistListSimpleDTO = new WhitelistListSimpleDTO()
                            .setId(embeddedDataWhitelistDO.getRefId())
                            .setType(embeddedDataWhitelistDO.getType())
                            .setRefValue(embeddedDataWhitelistDO.getRefValue());
                    return whitelistListSimpleDTO;
                }).collect(Collectors.groupingBy(WhitelistListSimpleDTO::getId));
        return whitelistListSimpleDTOMap;
    }

    private String checkAndGetValue(Integer type, String value) {
        if (WhitelistTypeEnum.isIotAccount(type)) {
            BusinessUserVO userInfo = businessUserService.getByUsernameBizType(value, 0);
            if (null == userInfo) {
                throw new BaseException("没有该客户，请确认账号是否正确");
            }
            return userInfo.getUid();
        } else if (WhitelistTypeEnum.isProductId(type)) {
            // 目前暂不校验
            return value;
        }
        return value;
    }

    private Map<String, String> developUsersParse(Map<String, UserDeveloperDetailVO> userMap) {
        if (userMap.isEmpty()) {
            return new HashMap<>();
        }
        HashMap<String, String> result = new HashMap<>();
        for (Map.Entry<String, UserDeveloperDetailVO> entry : userMap.entrySet()) {
            result.put(entry.getKey(), entry.getValue().getEnterpriseName());
        }
        return result;
    }

    private Map<String, String> businessUsersParse(List<BusinessUserVO> userList) {
        if (CollectionUtils.isEmpty(userList)) {
            return new HashMap<>();
        }
        return userList.stream().collect(Collectors.toMap(BusinessUserVO::getUid, BusinessUserVO::getUsername));
    }

    private String getWholeName(String tuyaName, String name) {
        StringBuilder result = new StringBuilder()
                .append(tuyaName)
                .append("(")
                .append(name)
                .append(")");
        return result.toString();
    }
}
