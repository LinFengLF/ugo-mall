package com.edu.mju.ugomall.mapper;

import com.edu.mju.ugomall.entity.GoodsAttribute;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品参数表(GoodsAttribute)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-06 16:55:03
 */
@Repository
public interface GoodsAttributeMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    GoodsAttribute queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<GoodsAttribute> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param goodsAttribute 实例对象
     * @return 对象列表
     */
    List<GoodsAttribute> queryAll(GoodsAttribute goodsAttribute);

    /**
     * 新增数据
     *
     * @param goodsAttribute 实例对象
     * @return 影响行数
     */
    int insert(GoodsAttribute goodsAttribute);

    /**
     * 修改数据
     *
     * @param goodsAttribute 实例对象
     * @return 影响行数
     */
    int update(GoodsAttribute goodsAttribute);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

    List<GoodsAttribute> getList();

}