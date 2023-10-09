package com.zhu.ticketgrabbing.dao;

import com.zhu.ticketgrabbing.entity.TrainStation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 列车站点信息(TrainStation)表数据库访问层
 *
 * @author makejava
 * @since 2023-10-03 14:51:04
 */
@Mapper
public interface TrainStationDao {

    /**
     * 通过ID查询单条数据
     *
     * @param stationId 主键
     * @return 实例对象
     */
    TrainStation queryById(Long stationId);
    /**
     * 统计总行数
     *
     * @param trainStation 查询条件
     * @return 总行数
     */
    long count(TrainStation trainStation);

    /**
     * 新增数据
     *
     * @param trainStation 实例对象
     * @return 影响行数
     */
    int insert(TrainStation trainStation);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TrainStation> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TrainStation> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TrainStation> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TrainStation> entities);

    /**
     * 修改数据
     *
     * @param trainStation 实例对象
     * @return 影响行数
     */
    int update(TrainStation trainStation);

    /**
     * 通过主键删除数据
     *
     * @param stationId 主键
     * @return 影响行数
     */
    int deleteById(Long stationId);

    /**
     * 根据code获取站点信息
     *
     * @param list code集合
     * @return 返回数据
     */
    List<TrainStation> getTitleByCode(@Param("list") List list);

    /**
     * 获取所有码值
     * @return 返回集合
     */
    List<TrainStation> getCodeTitleList();
}

