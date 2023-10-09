package com.zhu.ticketgrabbing.service;

import com.zhu.ticketgrabbing.entity.TrainStation;

/**
 * 列车站点信息(TrainStation)表服务接口
 *
 * @author makejava
 * @since 2023-10-03 14:51:05
 */
public interface TrainStationService {

    /**
     * 通过ID查询单条数据
     *
     * @param stationId 主键
     * @return 实例对象
     */
    TrainStation queryById(Long stationId);

    /**
     * 新增数据
     *
     * @param trainStation 实例对象
     * @return 实例对象
     */
    TrainStation insert(TrainStation trainStation);

    /**
     * 修改数据
     *
     * @param trainStation 实例对象
     * @return 实例对象
     */
    TrainStation update(TrainStation trainStation);

    /**
     * 通过主键删除数据
     *
     * @param stationId 主键
     * @return 是否成功
     */
    boolean deleteById(Long stationId);

}
