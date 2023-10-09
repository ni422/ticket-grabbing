package com.zhu.ticketgrabbing.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户业务表(UserBus)实体类
 *
 * @author makejava
 * @since 2023-10-02 02:26:44
 */
@Data
public class UserBus implements Serializable {
    private static final long serialVersionUID = -21734294943830064L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 用户中文名
     */
    private String nameZh;
    /**
     * 用户账号
     */
    private String codeNo;
    /**
     * 密码
     */
    private String pwd;
    /**
     * 通知邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String phoneNo;
    /**
     * 出发地
     */
    private String fromData;
    /**
     * 目的地
     */
    private String toData;
    /**
     * 出发时间
     */
    private String startDate;
    /**
     * 第一选择时间
     */
    private String firstDate;
    /**
     * 其他选择时间
     */
    private String otherDate;
    /**
     * 抢票状态
     */
    private Integer status;
    /**
     * 抢票次数
     */
    private Integer doNum;

    /**
     * 身份证后四位
     */
    private String cartId;

    /**
     * 验证码信息
     */
    private String randCode;

}

