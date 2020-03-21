package com.edu.mju.ugomall.service;

import com.edu.mju.ugomall.entity.Goods;
import com.edu.mju.ugomall.entity.GoodsAllinone;
import com.edu.mju.ugomall.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 商品基本信息表(Goods)表服务接口
 *
 * @author makejava
 * @since 2020-02-20 00:05:25
 */
public interface GoodsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Map<String,Object> queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Goods> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param goods 实例对象
     * @return 实例对象
     */
    Map<String, Object> insert(Goods goods);

    /**
     * 更新商品信息
     * @param goods
     * @return
     */
    Map<String,Object> update(Goods goods);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 批量删除+单个删除
     * @param ids
     * @return
     */
    Map<String,Object> deleteByBatch(String[] ids);


    /**
     * 分页商品查询方法
     * @param page
     * @param pageSize
     * @param name
     * @param id
     * @param goodsSn
     * @return
     */
    PageInfo<Goods> getGoodsList(Integer page, Integer pageSize, String name, String id,String goodsSn,String categoryId);


    Map<String,Object> publishGoods(GoodsAllinone goodsAllinone);

    Map<String,Object> editGoods(GoodsAllinone goodsAllinone);

    int getGoodsNum();
}