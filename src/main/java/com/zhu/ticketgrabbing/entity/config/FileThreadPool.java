package com.zhu.ticketgrabbing.entity.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文件线程池实体类
 */
@Data
@Component
@ConfigurationProperties(prefix = "thread-pool.file")
public class FileThreadPool extends AbstractThreadPool {
    private String threadNamePrefix = "file-thread-pool";
}
