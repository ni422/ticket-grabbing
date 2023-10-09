package com.zhu.ticketgrabbing.service.impl;

import com.zhu.ticketgrabbing.entity.TrainStation;
import com.zhu.ticketgrabbing.dao.TrainStationDao;
import com.zhu.ticketgrabbing.service.TrainStationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 列车站点信息(TrainStation)表服务实现类
 *
 * @author makejava
 * @since 2023-10-03 14:51:05
 */
@Service("trainStationService")
public class TrainStationServiceImpl implements TrainStationService {
    @Resource
    private TrainStationDao trainStationDao;

    /**
     * 通过ID查询单条数据
     *
     * @param stationId 主键
     * @return 实例对象
     */
    @Override
    public TrainStation queryById(Long stationId) {
        return this.trainStationDao.queryById(stationId);
    }

    /**
     * 新增数据
     *
     * @param trainStation 实例对象
     * @return 实例对象
     */
    @Override
    public TrainStation insert(TrainStation trainStation) {
        this.trainStationDao.insert(trainStation);
        return trainStation;
    }

    /**
     * 修改数据
     *
     * @param trainStation 实例对象
     * @return 实例对象
     */
    @Override
    public TrainStation update(TrainStation trainStation) {
        this.trainStationDao.update(trainStation);
        return this.queryById(trainStation.getStationId());
    }

    /**
     * 通过主键删除数据
     *
     * @param stationId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long stationId) {
        return this.trainStationDao.deleteById(stationId) > 0;
    }
}
