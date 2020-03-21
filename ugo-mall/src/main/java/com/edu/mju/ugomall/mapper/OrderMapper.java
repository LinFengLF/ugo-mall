package com.edu.mju.ugomall.mapper;

import com.edu.mju.ugomall.entity.Order;
import com.edu.mju.ugomall.entity.OrderAllinone;
import com.edu.mju.ugomall.entity.OrderGoods;
import com.edu.mju.ugomall.entity.User;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * 订单表(Order)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-01 21:31:59
 */
public interface OrderMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Order queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Order> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param order 实例对象
     * @return 对象列表
     */
    List<Order> queryAll(Order order);

    /**
     * 新增数据
     *
     * @param order 实例对象
     * @return 影响行数
     */
    int insert(Order order);

    /**
     * 修改数据
     *
     * @param order 实例对象
     * @return 影响行数
     */
    int update(Order order);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);



//    int updateOrderStatus(String id, String orderStatus, Timestamp updateTime);

    List<OrderAllinone> getList(@Param("orderStatus") Integer orderStatus, @Param("userId") String userId,
                                @Param("orderSn") String orderSn);

    int getOrderNum();

}