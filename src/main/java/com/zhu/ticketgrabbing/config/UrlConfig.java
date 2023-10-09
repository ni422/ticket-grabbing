package com.zhu.ticketgrabbing.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "url")
public class UrlConfig {

    private String queryApi;
    private String checkLogin;
    private String messageCode;
    private String login;

}
