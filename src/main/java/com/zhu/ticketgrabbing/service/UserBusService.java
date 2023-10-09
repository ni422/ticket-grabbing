package com.zhu.ticketgrabbing.service;

import com.zhu.ticketgrabbing.constant.Result;
import com.zhu.ticketgrabbing.entity.QueryDto;
import com.zhu.ticketgrabbing.entity.UserBus;
import org.springframework.ui.Model;

import java.util.List;

/**
 * 用户业务表(UserBus)表服务接口
 *
 * @author makejava
 * @since 2023-10-02 02:26:45
 */
public interface UserBusService {


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserBus queryById(Integer id);

    /**
     * 新增数据
     *
     * @param userBus 实例对象
     * @return 实例对象
     */
    UserBus insert(UserBus userBus);

    /**
     * 修改数据
     *
     * @param userBus 实例对象
     * @return 实例对象
     */
    UserBus update(UserBus userBus);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    void getTrainInfoList(QueryDto queryDto, Model model);

    void getTrainStationList(Model model);

    Result<Object> loginUser(Model model, UserBus user);

    Result<Object> getMessageCode(String phoneId, String cartId);
}
