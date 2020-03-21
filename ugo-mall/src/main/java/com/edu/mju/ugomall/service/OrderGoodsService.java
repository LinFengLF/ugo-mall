package com.edu.mju.ugomall.service;

import com.edu.mju.ugomall.entity.OrderGoods;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 订单商品表(OrderGoods)表服务接口
 *
 * @author makejava
 * @since 2020-03-13 23:51:04
 */
public interface OrderGoodsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OrderGoods queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<OrderGoods> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param orderGoods 实例对象
     * @return 实例对象
     */
    Map<String,Object> insert(OrderGoods orderGoods);

    /**
     * 修改数据
     *
     * @param orderGoods 实例对象
     * @return 实例对象
     */
    OrderGoods update(OrderGoods orderGoods);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);


    PageInfo<OrderGoods> getList(Integer page, Integer pageSize);

    Map<String, Object> deleteByBatch(String[] ids);

}