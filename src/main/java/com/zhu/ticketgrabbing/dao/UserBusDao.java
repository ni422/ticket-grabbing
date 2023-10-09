package com.zhu.ticketgrabbing.dao;

import com.zhu.ticketgrabbing.entity.UserBus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户业务表(UserBus)表数据库访问层
 *
 * @author makejava
 * @since 2023-10-02 02:26:44
 */
@Mapper
public interface UserBusDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserBus queryById(Integer id);


    /**
     * 统计总行数
     *
     * @param userBus 查询条件
     * @return 总行数
     */
    long count(UserBus userBus);

    /**
     * 新增数据
     *
     * @param userBus 实例对象
     * @return 影响行数
     */
    int insert(UserBus userBus);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<UserBus> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<UserBus> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<UserBus> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<UserBus> entities);

    /**
     * 修改数据
     *
     * @param userBus 实例对象
     * @return 影响行数
     */
    int update(UserBus userBus);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

