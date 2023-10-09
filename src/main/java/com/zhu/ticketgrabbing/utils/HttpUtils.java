package com.zhu.ticketgrabbing.utils;

import com.alibaba.fastjson.JSONObject;
import com.zhu.ticketgrabbing.config.Config;
import com.zhu.ticketgrabbing.constant.Result;
import com.zhu.ticketgrabbing.entity.MethonEnum;
import com.zhu.ticketgrabbing.entity.QueryDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.zhu.ticketgrabbing.constant.TicketStant.ERROE_MSG;

@Slf4j
@Component
public class HttpUtils {


    @Autowired
    private Config config;

    /**
     * 绕过ssl的get请求
     *
     * @param queryDto 请求对象
     * @param queryApi 请求url
     * @return 返回请求结果
     */
    public JSONObject getHttpResBody(QueryDto queryDto, String queryApi) {
        HttpClient httpsClient = null;
        JSONObject jsonResult = null;
        try {
            httpsClient = getInstance();
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("leftTicketDTO.train_date", queryDto.getDate()));
            params.add(new BasicNameValuePair("leftTicketDTO.from_station", queryDto.getFrom()));
            params.add(new BasicNameValuePair("leftTicketDTO.to_station", queryDto.getTo()));
            params.add(new BasicNameValuePair("purpose_codes", "ADULT"));
            String param = EntityUtils.toString(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));
            String url = queryApi + "?" + param;
            HttpGet request = new HttpGet(url);

            request.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36");
            request.setHeader("X-Requested-With", "XMLHttpRequest");
            request.setHeader("Cookie", "uab_collina=169614418965282241020371; JSESSIONID=077A18D2B44C260EBBA55ADCA50E7E51; route=6f50b51faa11b987e576cdb301e545c4; BIGipServerotn=2162688266.50210.0000; guidesStatus=off; highContrastMode=defaltMode; cursorStatus=off; _jc_save_fromStation=%u5317%u4EAC%u5317%2CVAP; _jc_save_toStation=%u4E0A%u6D77%2CSHH; _jc_save_fromDate=2023-10-03; _jc_save_toDate=2023-10-03; _jc_save_wfdc_flag=wf");
            request.setHeader("Referer", config.getBaseUrl());
            HttpResponse response = httpsClient.execute(request);
            String strResult = EntityUtils.toString(response.getEntity());
            jsonResult = JSONObject.parseObject(strResult);
            log.info("{}", jsonResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResult;
    }


    public String post(HttpPost httpPost, Integer type, List<NameValuePair> paramList) {
        return doPost(httpPost, type, paramList);
    }

    public String post(HttpPost httpPost, Integer type) {
        return doPost(httpPost, type, Collections.emptyList());
    }

    /**
     * 实际执行post请求
     *
     * @param httpPost post对象
     * @param type     cookie类型
     */
    public String doPost(HttpPost httpPost, Integer type, List<NameValuePair> paramList) {

        HttpClient httpClient = getInstance();

        // 判断请求如何为空
        httpPost = Optional.ofNullable(httpPost).orElse(new HttpPost());

        // TODO 封装header信息（注意严格控制请求头）
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36");
        httpPost.addHeader("Origin", config.getBaseUrl());
        httpPost.addHeader(HttpHeaders.REFERER, config.getBaseUrl());
        httpPost.addHeader(HttpHeaders.HOST, config.getHost());
        httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
        httpPost.addHeader("Cookie", MethonEnum.getName(type));

        // 封装参数对象 www结构
        if (!CollectionUtils.isEmpty(paramList)) {
            UrlEncodedFormEntity urlEncodedFormEntity1 = new UrlEncodedFormEntity(paramList,
                    Consts.UTF_8);
            httpPost.setEntity(urlEncodedFormEntity1);
        }
        HttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity httpEntity = response.getEntity();
                String result = EntityUtils.toString(httpEntity, "UTF-8");
                EntityUtils.consume(httpEntity);
                return result;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "请求失败请检查请求是否正确";
    }

    /**
     * 对结果进行处理
     *
     * @param authCodeRes string类型的返回值
     */
    public static Result<Object> getObjectResult(String authCodeRes) {
        log.info("请求返回结果：{}", authCodeRes);
        if (authCodeRes.contains("result_code")) {
            JSONObject codeRes = JSONObject.parseObject(authCodeRes);
            if (((Integer) codeRes.get("result_code")).equals(0)) {
                return new Result<Object>(cn.hutool.http.HttpStatus.HTTP_OK, "请求成功，请注意查收验证码!!!");
            } else {
                return new Result<Object>(cn.hutool.http.HttpStatus.HTTP_BAD_REQUEST, (String) codeRes.get("result_message"));
            }
        } else {
            return new Result<Object>(cn.hutool.http.HttpStatus.HTTP_BAD_REQUEST, ERROE_MSG);
        }

    }


    /**
     * 创建绕过ssl请求对象
     */
    public HttpClient getInstance() {
        try {
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(sslcontext);
            ClientConnectionManager ccm = new DefaultHttpClient().getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", 443, ssf));
            HttpParams params = new BasicHttpParams();
            params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 8000);
            params.setParameter(CoreConnectionPNames.SO_TIMEOUT, 8000);
            HttpClient httpclient = new DefaultHttpClient(ccm, params);
            httpclient.getParams().setParameter(HTTP.USER_AGENT,
                    "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; BOIE9;ZHCN)");
            return httpclient;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 创建ssl实现类
     */
    private static X509TrustManager tm = new X509TrustManager() {
        public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    };

}