package com.zhu.ticketgrabbing.service.impl;

import com.zhu.ticketgrabbing.entity.TrainCity;
import com.zhu.ticketgrabbing.dao.TrainCityDao;
import com.zhu.ticketgrabbing.service.TrainCityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 地区(TrainCity)表服务实现类
 *
 * @author makejava
 * @since 2023-10-03 14:51:32
 */
@Service("trainCityService")
public class TrainCityServiceImpl implements TrainCityService {
    @Resource
    private TrainCityDao trainCityDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TrainCity queryById(Integer id) {
        return this.trainCityDao.queryById(id);
    }


    /**
     * 新增数据
     *
     * @param trainCity 实例对象
     * @return 实例对象
     */
    @Override
    public TrainCity insert(TrainCity trainCity) {
        this.trainCityDao.insert(trainCity);
        return trainCity;
    }

    /**
     * 修改数据
     *
     * @param trainCity 实例对象
     * @return 实例对象
     */
    @Override
    public TrainCity update(TrainCity trainCity) {
        this.trainCityDao.update(trainCity);
        return this.queryById(trainCity.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.trainCityDao.deleteById(id) > 0;
    }
}
