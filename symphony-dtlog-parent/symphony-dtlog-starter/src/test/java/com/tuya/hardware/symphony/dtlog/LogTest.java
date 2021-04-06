package com.tuya.hardware.symphony.dtlog;

import com.tuya.hardware.symphony.dtlog.core.processor.LogProcessor;
import com.tuya.hardware.symphony.dtlog.core.service.LogFacadeServiceImpl;

import java.util.Arrays;

public class LogTest {

    public static void main(String[] args) throws Exception {
        LogProcessor logProcessor = new LogProcessor();
        LogFacadeServiceImpl logProducerFacadeService = new LogFacadeServiceImpl();
        logProducerFacadeService.logProcessor = logProcessor;
        FirmwareEsDO firmwareEsDO = new FirmwareEsDO()
                .setFirmwareKey("electronic_log_firmware_base")
                .setFirmwareName("定制固件1")
                .setIdentification("keytdpsxxgnn3y9d123")
                .setCategory("模组固件-单品固件")
                .setCategoryLabel("cz")
                .setChannelCategory("主联网模块固件")
                .setCommunicationType(Arrays.asList("wifi"))
                .setFlashSize("8Mbit")
                .setRemark("定制固件")
                .setPrincipal("一兮(黄锦涛)")
                .setRemark("123123123");
        logProducerFacadeService.persistLog(firmwareEsDO);
    }

}
