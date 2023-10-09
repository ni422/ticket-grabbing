package com.zhu.ticketgrabbing.evet;

import com.zhu.ticketgrabbing.entity.EmailModel;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

/**
 * 邮件事件定义
 */
public class MailEvent extends ApplicationEvent {

    private final EmailModel emailModel;

    public MailEvent(EmailModel emailModel) {
        super(emailModel);
        this.emailModel = emailModel;
    }

    public EmailModel getEmailModel() {
        return emailModel;
    }
}
