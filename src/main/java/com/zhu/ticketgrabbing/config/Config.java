package com.zhu.ticketgrabbing.config;

import com.zhu.ticketgrabbing.utils.StaticUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "config")
public class Config {

    private String host;

    private String baseUrl;

    private Boolean enableProxy;

    private String proxyHost;

    private Integer proxyPort;

    private Map<String, Integer> queryTicketSleepTime;

    private String pythonPath;

    private String appId;

    private ProxyIp proxyIp;

    public Integer querySleep() {
        return (int) (queryTicketSleepTime.get("min") + Math.random() * (
                queryTicketSleepTime.get("max") - queryTicketSleepTime.get("min") + 1));
    }

    public ProxyIp getProxyIp() {
        proxyIp = StaticUtil.proxyIp();
        if (proxyIp == null) {
            proxyIp = new ProxyIp();
            proxyIp.setIp(proxyHost);
            proxyIp.setPort(proxyPort);
        }
        return proxyIp;
    }
}