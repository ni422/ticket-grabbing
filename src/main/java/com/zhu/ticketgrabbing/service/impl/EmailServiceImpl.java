package com.zhu.ticketgrabbing.service.impl;

import com.zhu.ticketgrabbing.entity.EmailModel;
import com.zhu.ticketgrabbing.service.EmailService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.MapUtils;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    /**
     * 发送者邮箱
     */
    @Value("${spring.mail.username}")
    public String MAIL_USERNAME;

    @Resource
    private JavaMailSender javaMailSender;


    /**
     * 邮件发送
     *
     * @param model 封装的邮件实体类
     */
    @SneakyThrows
    @Override
    public void sendModelMail(EmailModel model) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject(model.getSubject()); // 邮件标题
        helper.setFrom(MAIL_USERNAME); // 发送者邮箱
        helper.setTo(model.getRecipientMailbox()); // 收件人邮箱
        if (model.getCcMailbox() != null && model.getCcMailbox().length != 0) {
            helper.setCc(model.getCcMailbox()); // 抄送人
        }
        if (model.getBccMailbox() != null && model.getBccMailbox().length != 0) {
            helper.setBcc(model.getBccMailbox()); // 加密抄送
        }
        helper.setSentDate(new Date()); // 发送日期
        // 判断是否为html文件
        helper.setText(model.getSendContent(), model.isHtmlFlag()); // 发送内容

        // 处理html的内联图片
        if (model.isHtmlFlag() && !MapUtils.isEmpty(model.getInLinePic())) {
            log.info("处理内联图片展示");
            for (String key : model.getInLinePic().keySet()) {
                helper.addInline(key, new File(model.getInLinePic().get(key)));
            }
        }

        // 处理附件文件
        if (!MapUtils.isEmpty(model.getEnclosures())) {
            log.info("-------[Iterator循环遍历]通过keySet取出map数据---------");
            for (String key : model.getEnclosures().keySet()) {
                helper.addAttachment(key, new File(model.getEnclosures().get(key)));// 预览文件
            }
        }
        javaMailSender.send(mimeMessage);
    }
}
