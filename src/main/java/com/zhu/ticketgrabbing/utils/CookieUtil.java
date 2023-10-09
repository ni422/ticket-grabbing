package com.zhu.ticketgrabbing.utils;

import com.zhu.ticketgrabbing.config.Config;
import com.zhu.ticketgrabbing.entity.LogdeviceModel;
import lombok.AllArgsConstructor;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CookieUtil {


    private Config config;

    public BasicCookieStore init(BasicCookieStore basicCookieStore, LogdeviceModel logdeviceModel) {

        if (basicCookieStore == null) {
            basicCookieStore = new BasicCookieStore();
        }
        BasicClientCookie expCookie = new BasicClientCookie("RAIL_EXPIRATION", logdeviceModel.getExp());
        expCookie.setDomain(config.getHost());
        expCookie.setPath("/");
        basicCookieStore.addCookie(expCookie);

        BasicClientCookie dfCookie = new BasicClientCookie("RAIL_DEVICEID", logdeviceModel.getDfp());
        dfCookie.setDomain(config.getHost());
        dfCookie.setPath("/");
        basicCookieStore.addCookie(dfCookie);


        return basicCookieStore;
    }
}