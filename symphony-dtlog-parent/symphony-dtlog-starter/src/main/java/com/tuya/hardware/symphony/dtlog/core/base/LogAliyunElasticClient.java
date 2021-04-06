package com.tuya.hardware.symphony.dtlog.core.base;

import com.tuya.elastic.ElasticClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/7/2 3:17 下午
 * @description：es Client
 */
@Repository
public class LogAliyunElasticClient extends ElasticClient {

    @Value("${es.biz.host}")
    private String host;

    @Value("${es.biz.port.restfull}")
    private Integer port;

    @Value("${es.biz.clusterName}")
    private String clusterName;

    @Value("${security.app.key.version}")
    private String passwordVersion;

    @Value("${security.app.key}")
    private String password;

    @Value("${application.name}")
    private String appName;

    @Override
    public void afterPropertiesSet() throws Exception {

        //调用父类的afterPropertiesSet方法之前先设置host和port
        this.setHost(host);
        this.setPort(port);
        this.setClusterName(clusterName);

        //这里的appId是apollo里的appId
        this.setUserName(appName);
        this.setPassword(password);
        this.setPasswordVersion(passwordVersion);

        super.afterPropertiesSet();
    }
}
