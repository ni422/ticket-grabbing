package com.zhu.ticketgrabbing.service;

import com.zhu.ticketgrabbing.entity.TrainCity;

/**
 * 地区(TrainCity)表服务接口
 *
 * @author makejava
 * @since 2023-10-03 14:51:32
 */
public interface TrainCityService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TrainCity queryById(Integer id);

    /**
     * 新增数据
     *
     * @param trainCity 实例对象
     * @return 实例对象
     */
    TrainCity insert(TrainCity trainCity);

    /**
     * 修改数据
     *
     * @param trainCity 实例对象
     * @return 实例对象
     */
    TrainCity update(TrainCity trainCity);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
