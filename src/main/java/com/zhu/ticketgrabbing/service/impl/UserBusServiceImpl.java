package com.zhu.ticketgrabbing.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhu.ticketgrabbing.config.Config;
import com.zhu.ticketgrabbing.config.UrlConfig;
import com.zhu.ticketgrabbing.constant.Result;
import com.zhu.ticketgrabbing.constant.TicketStant;
import com.zhu.ticketgrabbing.dao.TrainStationDao;
import com.zhu.ticketgrabbing.entity.*;
import com.zhu.ticketgrabbing.dao.UserBusDao;
import com.zhu.ticketgrabbing.evet.MailEvent;
import com.zhu.ticketgrabbing.service.UserBusService;
import com.zhu.ticketgrabbing.utils.HttpCommonUtil;
import com.zhu.ticketgrabbing.utils.HttpUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import static com.zhu.ticketgrabbing.constant.TicketStant.CHECK_ERROR_MSG;

/**
 * 用户业务表(UserBus)表服务实现类
 *
 * @author makejava
 * @since 2023-10-02 02:26:47
 */
@Service("userBusService")
@Slf4j
public class UserBusServiceImpl implements UserBusService {

    @Autowired
    private UserBusDao userBusDao;
    @Autowired
    private HttpUtils httpUtils;

    @Autowired
    private UrlConfig urlConfig;

    @Autowired
    private Config config;

    @Autowired
    private TrainStationDao trainStationDao;

    @Autowired
    private Executor threadPoolExecutor;

    @Value("${url.queryApi}")
    private String queryApi;

    @Value("${url.checkLogin}")
    private String checkLogin;

    @Value("${url.messageCode}")
    private String messageCode;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public UserBus queryById(Integer id) {
        return this.userBusDao.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param userBus 实例对象
     * @return 实例对象
     */
    @Override
    public UserBus insert(UserBus userBus) {
        this.userBusDao.insert(userBus);
        return userBus;
    }

    /**
     * 修改数据
     *
     * @param userBus 实例对象
     * @return 实例对象
     */
    @Override
    public UserBus update(UserBus userBus) {
        this.userBusDao.update(userBus);
        return this.queryById(userBus.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.userBusDao.deleteById(id) > 0;
    }


    @Override
    public void getTrainInfoList(QueryDto queryDto, Model model) {

        JSONObject jsonResult = httpUtils.getHttpResBody(queryDto, queryApi);
        QueryResVO<String> data = JSONObject.parseObject(jsonResult.toJSONString(jsonResult.get("data")), QueryResVO.class);
        List<String> resList = data.getResult();
        Map<String, String> areaMap = data.getMap();
        String flag = data.getFlag();

        List<TicketVO> voArrayList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(resList)) {
            for (String ticketInfo : resList) {
                TicketVO ticketVO = new TicketVO();
                String[] split = ticketInfo.split("\\|");
                ticketVO.setDuration(split[10]);

                if (StringUtils.isAnyEmpty(areaMap.get(split[4]), areaMap.get(split[5]))) {
                    List<TrainStation> titleByCode = trainStationDao.getTitleByCode(Arrays.asList(split[4], split[5]));
                    Map<String, String> collect = titleByCode.stream().collect(Collectors.toMap(TrainStation::getStationCode, TrainStation::getStationTitle));
                    ticketVO.setExtreInfo(collect.get(split[4]) + "->" + collect.get(split[5]) + "(过路)");
                } else {
                    ticketVO.setExtreInfo("直达");
                }
                ticketVO.setDepartureStation(areaMap.get(split[6]));
                ticketVO.setArrivalStation(areaMap.get(split[7]));

                ticketVO.setTrainNumber(split[3]);
                ticketVO.setDepartureTime(split[8]);
                ticketVO.setArrivalTime(split[9]);
                // TODO 价格暂未获取
                voArrayList.add(ticketVO);
            }
        }
        model.addAttribute("tickets", voArrayList);
    }

    @Override
    public void getTrainStationList(Model model) {
        List<TrainStation> codeTitleList = trainStationDao.getCodeTitleList();
        model.addAttribute("codeList", codeTitleList);
    }

    /**
     * 用户信息登录、检查
     *
     * @param model 模型
     * @param user  用户信息
     */
    @Override
    public Result<Object> loginUser(Model model, UserBus user) {

        try {
            // TODO 2：根据获取的验证码信息登录网站
            HttpCommonUtil httpCommonUtil = new HttpCommonUtil();
            HttpPost httpPost = new HttpPost(urlConfig.getMessageCode());
            List<NameValuePair> formparams = new ArrayList<>();
            formparams.add(new BasicNameValuePair("appid", config.getAppId()));
            formparams.add(new BasicNameValuePair("sessionId", ""));
            formparams.add(new BasicNameValuePair("sig", ""));
            formparams.add(new BasicNameValuePair("if_check_slide_passcode_token", ""));
            formparams.add(new BasicNameValuePair("scene", ""));
            formparams.add(new BasicNameValuePair("checkMode:", "0"));
            formparams.add(new BasicNameValuePair("randCode", user.getRandCode()));
            formparams.add(new BasicNameValuePair("username", user.getPhoneNo()));
            formparams.add(new BasicNameValuePair("password", user.getPwd()));
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams,
                    Consts.UTF_8);
            httpPost.setEntity(urlEncodedFormEntity);
            String response = httpCommonUtil.post(httpPost);
            // TODO 3：登录成功后，保存用户数据到数据库中
            // TODO 发布Email事件
            applicationEventPublisher.publishEvent(new MailEvent(new EmailModel()));

            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("调用邮件接口验证");
                    System.out.println("存储日志");
                }
            });

            return HttpUtils.getObjectResult(response);

        } catch (Exception e) {
            log.info("获取信息：{}", e.getMessage());
        }
        return new Result<Object>(cn.hutool.http.HttpStatus.HTTP_BAD_REQUEST, "错误");
    }

    @Override
    public Result<Object> getMessageCode(String phoneId, String cartId) {
        // 1：根据用户手机号和身份证后4位获取验证码
        String authCodeRes = getAuthCode(phoneId, cartId);
        log.info("验证码获取返回：{}", authCodeRes);
        // TODO 验证码获取后记录日志信息
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("存储日志");
            }
        });
        Result<Object> objectResult = HttpUtils.getObjectResult(authCodeRes);
        log.info("验证码获取结果：{}", objectResult);
        return objectResult;
    }


    /**
     * 发送请求获取验证码
     *
     * @param phoneId 手机号
     * @param cartId  身份证
     * @return 返回请求结果
     */
    @SneakyThrows
    private String getAuthCode(String phoneId, String cartId) {

        // 检查账号信息
        HttpPost checkPost = new HttpPost(urlConfig.getCheckLogin());
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("appid", config.getAppId()));
        formparams.add(new BasicNameValuePair("username", phoneId));
        String checkRes = httpUtils.post(checkPost, TicketStant.checkLogin, formparams);
        Result<Object> checkResult = HttpUtils.getObjectResult(checkRes);
        if (HttpStatus.SC_OK != checkResult.getCode()) {
            return CHECK_ERROR_MSG;
        }
        log.info("检查接口的返回结果：{}", checkResult);
        formparams.clear();

        return checkRes;

/*        // 请求验证码接口 TODO 屏蔽验证码接口
        HttpPost httpPost = new HttpPost(urlConfig.getMessageCode());
        formparams.add(new BasicNameValuePair("appid", config.getAppId()));
        formparams.add(new BasicNameValuePair("username", phoneId));
        formparams.add(new BasicNameValuePair("castNum", cartId));
        return httpUtils.post(httpPost, TicketStant.messageCode, formparams);*/
    }
}
