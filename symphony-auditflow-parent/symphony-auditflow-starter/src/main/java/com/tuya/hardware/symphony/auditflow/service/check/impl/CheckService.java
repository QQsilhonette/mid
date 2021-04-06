package com.tuya.hardware.symphony.auditflow.service.check.impl;

import com.tuya.basic.client.domain.exception.BaseException;
import com.tuya.hardware.symphony.auditflow.model.cmd.CheckBatchUpdateCmd;
import com.tuya.hardware.symphony.auditflow.repository.check.CheckRepository;
import com.tuya.hardware.symphony.auditflow.infrastructure.dataobject.CheckDO;
import com.tuya.hardware.symphony.auditflow.model.dto.CheckItemDTO;
import com.tuya.hardware.symphony.auditflow.model.cmd.CheckItemUpdateCmd;
import com.tuya.hardware.symphony.auditflow.enums.common.SupportEnum;
import com.tuya.hardware.symphony.auditflow.service.check.CheckStrategy;
import com.tuya.hardware.symphony.auditflow.service.check.ICheckService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/10/26 8:30 下午
 * @description：校验项Service
 */
@Slf4j
@Service
public class CheckService implements ICheckService {

    @Resource
    private CheckRepository checkRepository;

    @Override
    public List<CheckItemDTO> getCheckList(Long refId, String refType, Map<String, String> checkHandlers) {
        LinkedList<CheckItemDTO> result = new LinkedList<>();
        List<CheckDO> checkList = checkRepository.getByRefIdAndRefType(refId, refType);
        Map<String, CheckDO> alreadyCheckedMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(checkList)) {
            alreadyCheckedMap = checkList.stream().collect(Collectors.toMap(CheckDO::getCheckKey, o -> o));
        }

        for (Map.Entry<String, String> checkItem : checkHandlers.entrySet()) {
            String checkKey = checkItem.getKey();
            String beanName = checkItem.getValue();
            CheckItemDTO checkItemDTO = new CheckItemDTO();
            checkItemDTO.setCheckKey(checkKey);
            Integer checkValue = alreadyCheckedMap.containsKey(checkKey) ?
                    alreadyCheckedMap.get(checkKey).getCheckValue() : SupportEnum.NOT_SUPPORT.getValue();
            checkItemDTO.setCheckValue(checkValue);
            try {
                log.info("queryCheckList checkKey: {}", checkKey);
                checkItemDTO.setCheckContent(analyse(refId, refType, checkKey, beanName));
            } catch (BaseException e) {
                log.warn(e.getErrorMsg());
                continue;
            }
            result.add(checkItemDTO);
        }
        return result;
    }

    @Override
    public void updateValByRefAndCheckKeys(CheckBatchUpdateCmd cmd) {
        checkRepository.updateValByRefAndCheckKeys(cmd.getRefId(), cmd.getRefType(), cmd.getCheckKeys(), cmd.getCheckValue());
    }

    private String analyse(Long refId, String refType, String checkKey, String beanName) {
        CheckStrategy checkStrategy = CheckStrategyFactory.getCheckStrategy(beanName);
        if (null == checkStrategy) {
            throw new BaseException("校验项handler不存在：" + beanName);
        }
        return checkStrategy.analyse(refId, refType, checkKey);
    }

    @Override
    public void upsertCheckItem(CheckItemUpdateCmd checkItemUpdateCmd, String refType) {
        if (null == checkRepository.getByRefAndCheckKey(checkItemUpdateCmd.getId(),
                refType, checkItemUpdateCmd.getCheckKey())) {
            CheckDO checkDO = new CheckDO()
                    .setRefId(checkItemUpdateCmd.getId())
                    .setRefType(refType)
                    .setCheckKey(checkItemUpdateCmd.getCheckKey())
                    .setCheckValue(checkItemUpdateCmd.getCheckValue());
            checkRepository.add(checkDO);
        } else {
            checkRepository.updateValByRefAndKey(checkItemUpdateCmd.getId(), refType,
                    checkItemUpdateCmd.getCheckKey(), checkItemUpdateCmd.getCheckValue());
        }
    }
}
