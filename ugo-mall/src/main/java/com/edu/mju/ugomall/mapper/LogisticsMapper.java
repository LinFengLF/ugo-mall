package com.edu.mju.ugomall.mapper;

import com.edu.mju.ugomall.entity.Logistics;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Logistics)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-16 09:40:01
 */
public interface LogisticsMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Logistics queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Logistics> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param logistics 实例对象
     * @return 对象列表
     */
    List<Logistics> queryAll(Logistics logistics);

    /**
     * 新增数据
     *
     * @param logistics 实例对象
     * @return 影响行数
     */
    int insert(Logistics logistics);

    /**
     * 修改数据
     *
     * @param logistics 实例对象
     * @return 影响行数
     */
    int update(Logistics logistics);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);


    List<Logistics> getList();

}