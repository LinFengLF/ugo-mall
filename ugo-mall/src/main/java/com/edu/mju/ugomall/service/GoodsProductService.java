package com.edu.mju.ugomall.service;

import com.edu.mju.ugomall.entity.GoodsProduct;
import java.util.List;
import java.util.Map;

/**
 * 商品货品表(GoodsProduct)表服务接口
 *
 * @author makejava
 * @since 2020-03-06 21:04:41
 */
public interface GoodsProductService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    GoodsProduct queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<GoodsProduct> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param goodsProduct 实例对象
     * @return 实例对象
     */
    Map<String,Object> insert(GoodsProduct goodsProduct);

    /**
     * 修改数据
     *
     * @param goodsProduct 实例对象
     * @return 实例对象
     */
    Map<String,Object> update(GoodsProduct goodsProduct);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    Map<String,Object> deleteById(String id);

    Map<String, Object> getList();
}