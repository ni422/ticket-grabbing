package com.zhu.ticketgrabbing.entity;

import lombok.Data;

@Data
public class LoginVO {

    private String sessionId;
    private String sig;
    private String if_check_slide_passcode_token;
    private String scene;
    private String checkMode; // 0
    private String randCode; // 278872
    private String username; // 18237270815
    private String password; // @bFVpAdy3JbSi3PJo+sz8iQ==
    private String appid; // otn

}
