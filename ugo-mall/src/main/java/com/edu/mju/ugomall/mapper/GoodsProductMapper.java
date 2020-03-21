package com.edu.mju.ugomall.mapper;

import com.edu.mju.ugomall.entity.GoodsProduct;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 商品货品表(GoodsProduct)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-06 21:04:41
 */
public interface GoodsProductMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    GoodsProduct queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<GoodsProduct> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param goodsProduct 实例对象
     * @return 对象列表
     */
    List<GoodsProduct> queryAll(GoodsProduct goodsProduct);

    /**
     * 新增数据
     *
     * @param goodsProduct 实例对象
     * @return 影响行数
     */
    int insert(GoodsProduct goodsProduct);

    /**
     * 修改数据
     *
     * @param goodsProduct 实例对象
     * @return 影响行数
     */
    int update(GoodsProduct goodsProduct);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

    List<GoodsProduct> getList();

}