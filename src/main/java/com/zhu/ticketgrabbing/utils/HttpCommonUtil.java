package com.zhu.ticketgrabbing.utils;

import com.alibaba.fastjson.JSONObject;
import com.zhu.ticketgrabbing.config.Config;
import com.zhu.ticketgrabbing.constant.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class HttpCommonUtil {

    private HttpClient httpClient;
    private HttpClient httpPureClient;
    private BasicCookieStore basicCookieStore;
    private Config config;

    public HttpCommonUtil() {
        this.init();
    }

    public HttpCommonUtil(BasicCookieStore basicCookieStore) {
        init(basicCookieStore);
    }

    /**
     * 初始化，
     */
    public void init() {
        BasicCookieStore basicCookieStore = new BasicCookieStore();
        this.init(basicCookieStore);
    }

    public void init(BasicCookieStore basicCookieStore) {
        this.basicCookieStore = basicCookieStore;
        this.config = ApplicationContextHelper.getBean(Config.class);
        httpClient = HttpClients.custom().setDefaultCookieStore(basicCookieStore).build();
        if (config != null && config.getEnableProxy()) {
            HttpHost proxy = new HttpHost(config.getProxyIp().getIp(), config.getProxyIp().getPort());
            DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
            httpClient = HttpClients.custom().setRoutePlanner(routePlanner)
                    .setDefaultCookieStore(basicCookieStore).build();
        }
        httpPureClient = HttpClients.createDefault();
    }

    public BasicCookieStore getBasicCookieStore() {
        return basicCookieStore;
    }

    public String get(HttpGet httpGet) {
        return this.doAction(httpGet, httpClient);
    }

    public String post(HttpPost httpPost) {
        return this.doAction(httpPost, httpClient);
    }

    private String doAction(HttpRequestBase httpRequestBase, HttpClient client) {
        httpRequestBase.addHeader(new BasicHeader("Origin", config.getBaseUrl()));
        httpRequestBase.addHeader(new BasicHeader(HttpHeaders.REFERER, config.getBaseUrl()));
        httpRequestBase.addHeader(new BasicHeader(HttpHeaders.HOST, config.getHost()));
        httpRequestBase.setHeader("X-Requested-With", "XMLHttpRequest");
        httpRequestBase.addHeader(new BasicHeader("Cookie", "\n" +
                "_passport_session=70b0c0de649a4aa5888e504e0397062d7321; guidesStatus=off; highContrastMode=defaltMode; cursorStatus=off; _jc_save_wfdc_flag=dc; BIGipServerotn=1591279882.24610.0000; BIGipServerpassport=904397066.50215.0000; route=6f50b51faa11b987e576cdb301e545c4; _jc_save_fromStation=%u4E0A%u6D77%2CSHH; _jc_save_toStation=%u5317%u4EAC%2CBJP; _jc_save_fromDate=2023-10-14; _jc_save_toDate=2023-10-04; BIGipServerpool_passport=48497162.50215.0000; BIGipServerportal=3151233290.17183.0000; fo=4ujcz71cb1w3n8miT-yr7b-28W6c5KUsNnqL3enQnKdkNPFtBYTlHkaUelLJu-7_0hHv9Rc-inbxedXsQWnVJZ91YX-Sj_aqgHejiOMEzV4ghAx-b-KvXv-OSZ6NhFn9UW24DcaXK_--fyRhQJhL1mrFxU59bcXO2J2QI4Z-SPVgoFeVb6ZzMQKaSKY19PPhSYRT7MEgJ62St8fK"));
        return sendRequest(httpRequestBase, client);
    }

    /**
     * 发送 http 请求
     *
     * @param httpRequestBase request
     * @param client          http client
     * @return response
     */
    private String sendRequest(HttpRequestBase httpRequestBase, HttpClient client) {
        String result = null;
        try {
            HttpResponse response = client.execute(httpRequestBase);
            HttpEntity httpEntity = response.getEntity();
            result = EntityUtils.toString(httpEntity, "UTF-8");
            EntityUtils.consume(httpEntity);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 异步http Get请求 无12306cookies植入等 纯净版
     *
     * @param httpGet 请求参数
     * @return future response
     */
    public CompletableFuture<String> asyncGet(HttpGet httpGet) {
        return CompletableFuture.supplyAsync(() -> sendRequest(httpGet, httpPureClient));
    }

    /**
     * 异步http Post请求 无12306cookies植入等 纯净版
     *
     * @param httpPost 请求参数
     * @return future response
     */
    public CompletableFuture<String> asyncPost(HttpPost httpPost) {
        return CompletableFuture.supplyAsync(() -> sendRequest(httpPost, httpPureClient));
    }


    /**
     * 对结果进行处理
     *
     * @param authCodeRes string类型的返回值
     */
    public static Result<Object> getObjectResult(String authCodeRes) {
        JSONObject codeRes = JSONObject.parseObject(authCodeRes);
        if ("0".equals(codeRes.get("result_code"))) {
            return new Result<Object>(cn.hutool.http.HttpStatus.HTTP_OK, (String) codeRes.get("result_message"));
        } else {
            return new Result<Object>(cn.hutool.http.HttpStatus.HTTP_BAD_REQUEST, (String) codeRes.get("result_message"));
        }
    }

}
