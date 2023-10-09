package com.zhu.ticketgrabbing;

import com.zhu.ticketgrabbing.entity.EmailModel;
import com.zhu.ticketgrabbing.evet.MailEvent;
import com.zhu.ticketgrabbing.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

@SpringBootTest
@Slf4j
class TicketGrabbingApplicationTests {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private EmailService emailService;


    @Test
    public void testLog() {

        try {
            int i = 10 / 0;
        } catch (Exception e) {

            log.info("测试", e);
            log.error("测试", e);

        }

        log.info("错误已被捕获");
        log.info("错误已被捕获");
        log.info("错误已被捕获");

    }


    @Test
    public void mailSent() {

        EmailModel emailModel = new EmailModel();
        emailModel.setSubject("测试邮件功能");
        emailModel.setRecipientMailbox(new String[]{"2970176344@qq.com"});
        emailModel.setSendContent("异步");
        emailService.sendModelMail(emailModel);
//        applicationEventPublisher.publishEvent(new MailEvent(emailModel));

    }
}
