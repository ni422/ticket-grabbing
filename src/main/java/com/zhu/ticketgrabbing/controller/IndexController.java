package com.zhu.ticketgrabbing.controller;

import com.zhu.ticketgrabbing.constant.Result;
import com.zhu.ticketgrabbing.entity.QueryDto;
import com.zhu.ticketgrabbing.entity.UserBus;
import com.zhu.ticketgrabbing.service.UserBusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@Slf4j
public class IndexController {

    @Value("${project.title}")
    private String title;

    @Autowired
    private UserBusService userBusService;

    /**
     * 主页访问
     *
     * @param model mv对象
     * @return 返回主页信息
     */
    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("key", "请输入用户信息");
        userBusService.getTrainStationList(model);
        extracted(model);
        return "index";
    }

    /**
     * TODO 需要抽取出来
     */
    private void extracted(Model model) {
        model.addAttribute("title", title);
    }

    /**
     * 跳转个人信息注册页面
     *
     * @return 返回注册地址
     */
    @GetMapping("/login")
    public String login(Model model) {
        extracted(model);
        userBusService.getTrainStationList(model);
        return "login";
    }

    /**
     * 跳转列车详情选择界面
     */
    @GetMapping("/traindetail")
    public String trainDetail(Model model) {
        extracted(model);
        return "traindetail";
    }

    @GetMapping("/getUserInfo")
    public String getUserInfo(Model model) {
        return "";
    }

    /**
     * 查询列车信息
     */
    @PostMapping("/getTrainInfoList")
    public String getTrainInfoList(@ModelAttribute QueryDto queryDto, Model model) {
        userBusService.getTrainInfoList(queryDto, model);
        userBusService.getTrainStationList(model);
        extracted(model);
        return "index";
    }

    /**
     * 用户登录获取验证码
     *
     * @return 返回发送详情
     */
    @GetMapping("/getMessageCode")
    @ResponseBody
    public Result<Object> getMessageCode(@RequestParam("phoneId") String phoneId,
                                         @RequestParam("cartId") String cartId) {
        return userBusService.getMessageCode(phoneId, cartId);
    }


    /**
     * 登录成功，并跳转抢票页面
     *
     * @param user 用户详情
     * @return 返回抢票详情页面
     */
    @PostMapping("/loginUserInfo")
    public String loginUserInfo(
            Model model,
            UserBus user
    ) {
        userBusService.loginUser(model, user);
        return "traindetail";
    }


}
