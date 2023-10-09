package com.zhu.ticketgrabbing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * 邮件实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailModel implements Serializable {
    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 收件人邮箱
     */
    private String[] recipientMailbox;

    /**
     * 抄送人邮箱
     */
    private String[] ccMailbox;

    /**
     * 加密抄送人邮箱
     */
    private String[] bccMailbox;

    /**
     * 发送内容
     */
    private String sendContent;

    /**
     * 真实名称/附件路径
     */
    private Map<String, String> enclosures;

    /**
     * 标识是否为html格式内容
     * 如何发送内容为html格式，需设置该属性为 true
     */
    private boolean htmlFlag = false;


    /**
     * html格式时内联图片数据 TODO 确认下文件格式
     */
    private Map<String, String> inLinePic;


}