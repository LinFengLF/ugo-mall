package com.edu.mju.ugomall.mapper;

import com.edu.mju.ugomall.entity.OrderGoods;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 订单商品表(OrderGoods)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-13 23:51:04
 */
public interface OrderGoodsMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OrderGoods queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<OrderGoods> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param orderGoods 实例对象
     * @return 对象列表
     */
    List<OrderGoods> queryAll(OrderGoods orderGoods);

    /**
     * 新增数据
     *
     * @param orderGoods 实例对象
     * @return 影响行数
     */
    int insert(OrderGoods orderGoods);

    /**
     * 修改数据
     *
     * @param orderGoods 实例对象
     * @return 影响行数
     */
    int update(OrderGoods orderGoods);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);


    /**
     * 批量删除+单个删除
     * @param ids
     * @return
     */
    int deleteByBatch(@Param("ids")String[] ids);

    /**
     * @return
     */
    List<OrderGoods> getList();

    List<OrderGoods> getByOrderId(@Param("orderId") String orderId);

}