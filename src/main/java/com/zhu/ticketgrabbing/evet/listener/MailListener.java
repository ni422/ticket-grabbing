package com.zhu.ticketgrabbing.evet.listener;

import com.zhu.ticketgrabbing.entity.EmailModel;
import com.zhu.ticketgrabbing.evet.MailEvent;
import com.zhu.ticketgrabbing.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MailListener implements ApplicationListener<MailEvent> {

    @Autowired
    private EmailService emailService;

    /**
     * 获取邮件事件执行具体逻辑
     */
    @Override
    public void onApplicationEvent(MailEvent event) {
        EmailModel emailModel = event.getEmailModel();
        log.info("邮件发送实体：{}", emailModel);
        // 发送邮件
        emailService.sendModelMail(emailModel);
    }


/*
    // 该注解与实现ApplicationListener的效果一样
    @EventListener
    public void listenMail(MailEvent event){
        System.out.println("测试");
    }*/
}
