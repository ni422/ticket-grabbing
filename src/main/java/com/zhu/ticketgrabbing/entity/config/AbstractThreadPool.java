package com.zhu.ticketgrabbing.entity.config;

import lombok.Data;

/**
 * 封装线程池通用字段
 */
@Data
public abstract class AbstractThreadPool {

    private int corePoolSize;
    private int maximumPoolSize;
    private int keepAliveTime;
    private int queueCapacity;

}
