package com.zhu.ticketgrabbing.dao;

import com.zhu.ticketgrabbing.entity.TrainCity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 地区(TrainCity)表数据库访问层
 *
 * @author makejava
 * @since 2023-10-03 14:51:32
 */
@Mapper
public interface TrainCityDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TrainCity queryById(Integer id);

    /**
     * 统计总行数
     *
     * @param trainCity 查询条件
     * @return 总行数
     */
    long count(TrainCity trainCity);

    /**
     * 新增数据
     *
     * @param trainCity 实例对象
     * @return 影响行数
     */
    int insert(TrainCity trainCity);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TrainCity> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TrainCity> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TrainCity> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TrainCity> entities);

    /**
     * 修改数据
     *
     * @param trainCity 实例对象
     * @return 影响行数
     */
    int update(TrainCity trainCity);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

