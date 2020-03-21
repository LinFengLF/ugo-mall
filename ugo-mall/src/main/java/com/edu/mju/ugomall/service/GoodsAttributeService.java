package com.edu.mju.ugomall.service;

import com.edu.mju.ugomall.entity.GoodsAttribute;
import java.util.List;
import java.util.Map;

/**
 * 商品参数表(GoodsAttribute)表服务接口
 *
 * @author makejava
 * @since 2020-03-06 16:55:03
 */
public interface GoodsAttributeService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    GoodsAttribute queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<GoodsAttribute> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param goodsAttribute 实例对象
     * @return 实例对象
     */
    Map<String,Object> insert(GoodsAttribute goodsAttribute);

    /**
     * 修改数据
     *
     * @param goodsAttribute 实例对象
     * @return 实例对象
     */
    GoodsAttribute update(GoodsAttribute goodsAttribute);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    Map<String,Object> deleteById(String id);


    Map<String,Object> getList();

}