package com.zhu.ticketgrabbing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 登录cookie信息
 */
@Data
@AllArgsConstructor
public class LogdeviceModel {

    private String exp;
    private String dfp;
}