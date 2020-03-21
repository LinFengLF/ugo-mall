package com.edu.mju.ugomall.service;

import com.edu.mju.ugomall.entity.GoodsCart;
import java.util.List;
import java.util.Map;

/**
 * 购物车商品表(GoodsCart)表服务接口
 *
 * @author makejava
 * @since 2020-03-09 09:59:13
 */
public interface GoodsCartService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    GoodsCart queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<GoodsCart> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param goodsCart 实例对象
     * @return 实例对象
     */
    Map<String,Object> insert(GoodsCart goodsCart);

    /**
     * 修改数据
     *
     * @param goodsCart 实例对象
     * @return 实例对象
     */
    Map<String,Object> update(GoodsCart goodsCart);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    Map<String,Object> deleteById(String id);


    Map<String,Object> getList();


    Map<String,Object> getListByUserId(String userId);

    /**
     * 批量删除+单个删除
     * @param ids
     * @return
     */
    Map<String,Object> deleteByBatch(String[] ids);
}

