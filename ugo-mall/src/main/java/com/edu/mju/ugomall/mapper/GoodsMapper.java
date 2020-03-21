package com.edu.mju.ugomall.mapper;

import com.edu.mju.ugomall.entity.Goods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品基本信息表(Goods)表数据库访问层
 *
 * @author makejava
 * @since 2020-02-20 00:05:22
 */
@Repository
public interface GoodsMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Goods queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Goods> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param goods 实例对象
     * @return 对象列表
     */
    List<Goods> queryAll(Goods goods);

    /**
     * 新增数据
     *
     * @param goods 实例对象
     * @return 影响行数
     */
    int insert(Goods goods);

    /**
     * 修改数据
     *
     * @param goods 实例对象
     * @return 影响行数
     */
    int update(Goods goods);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);


    /**
     * 查询全部的商品
     *
     * @param goodsSn
     * @param name
     * @param id
     * @return
     */
    List<Goods> getGoodsInfo(@Param("goodsSn") String goodsSn, @Param("name") String name, @Param("id") String id,
                             @Param("categoryId") String categoryId);

    /**
     * 批量删除+单个删除
     *
     * @param ids
     * @return
     */
    int deleteByBatch(@Param("ids") String[] ids);


    int getGoodsNum();

}