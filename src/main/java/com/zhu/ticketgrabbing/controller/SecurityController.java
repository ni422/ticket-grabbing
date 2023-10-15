package com.zhu.ticketgrabbing.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SecurityController {

    @GetMapping("/indexSecurity")
    public String indexSecurity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(JSON.toJSONString(authentication));





        return "测试功能";
    }


}
