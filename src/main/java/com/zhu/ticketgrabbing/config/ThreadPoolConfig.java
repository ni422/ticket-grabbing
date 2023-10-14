package com.zhu.ticketgrabbing.config;

import com.zhu.ticketgrabbing.entity.config.AbstractThreadPool;
import com.zhu.ticketgrabbing.entity.config.CommonThreadPool;
import com.zhu.ticketgrabbing.entity.config.FileThreadPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池全局配置
 */
@EnableAsync
@Configuration
@Slf4j
public class ThreadPoolConfig {

    private final FileThreadPool fileThreadPool;
    private final CommonThreadPool commonThreadPool;

    @Autowired
    public ThreadPoolConfig(FileThreadPool fileThreadPool, CommonThreadPool commonThreadPool) {
        this.fileThreadPool = fileThreadPool;
        this.commonThreadPool = commonThreadPool;
    }

    /**
     * 通用线程池配置
     */
    @Bean("commonThreadPoolExecutor")
    public Executor commonThreadPoolExecutor() {
        return commonExecutor(fileThreadPool, fileThreadPool.getThreadNamePrefix(), (r, executor) -> {
            return;
        });
    }

    /**
     * 文件线程池配置
     */
    @Bean("fileThreadPoolExecutor")
    public Executor fileThreadPoolExecutor() {
        return commonExecutor(commonThreadPool, fileThreadPool.getThreadNamePrefix());
    }

    /**
     * 使用默认拒绝策略
     */
    private Executor commonExecutor(AbstractThreadPool threadPoolPojo, String threadNamePrefix) {
        ThreadPoolExecutor.AbortPolicy defaultHandler = new ThreadPoolExecutor.AbortPolicy();
        return commonExecutor(threadPoolPojo, threadNamePrefix, defaultHandler);
    }


    /**
     * 通用线程池配置
     *
     * @param threadPoolPojo    通用配置对象
     * @param threadNamePrefix  线程池前缀
     * @param rejectedExecution 拒绝策略
     */
    private Executor commonExecutor(AbstractThreadPool threadPoolPojo, String threadNamePrefix, RejectedExecutionHandler rejectedExecution) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(threadPoolPojo.getCorePoolSize());
        executor.setMaxPoolSize(threadPoolPojo.getMaximumPoolSize());
        executor.setQueueCapacity(threadPoolPojo.getQueueCapacity());
        executor.setKeepAliveSeconds(threadPoolPojo.getKeepAliveTime());
        // 拒绝策略
        executor.setRejectedExecutionHandler(rejectedExecution);
        // 线程前缀
        executor.setThreadNamePrefix(threadNamePrefix);
        return executor;
    }


}
