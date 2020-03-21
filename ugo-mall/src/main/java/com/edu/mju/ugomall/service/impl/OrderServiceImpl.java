package com.edu.mju.ugomall.service.impl;

import com.edu.mju.ugomall.entity.Order;
import com.edu.mju.ugomall.entity.OrderAllinone;
import com.edu.mju.ugomall.entity.OrderGoods;
import com.edu.mju.ugomall.entity.User;
import com.edu.mju.ugomall.mapper.OrderGoodsMapper;
import com.edu.mju.ugomall.mapper.OrderMapper;
import com.edu.mju.ugomall.mapper.UserMapper;
import com.edu.mju.ugomall.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 订单表(Order)表服务实现类
 *
 * @author makejava
 * @since 2020-03-01 21:31:59
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Autowired
    private OrderGoodsMapper orderGoodsMapper;
    @Autowired
    private UserMapper userMapper;
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Map<String,Object> queryById(Integer id) {
        Map<String,Object> resultMap = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        Order order = this.orderMapper.queryById(id);
        List<OrderGoods> list = orderGoodsMapper.getByOrderId(String.valueOf(order.getId()));
        User user = userMapper.getUserInfoById(order.getUserId());
        if (order != null && list.size() > 0 && user != null){
            resultMap.put("code",1);
            resultMap.put("msg","订单详情信息获取成功");
            map.put("user",user);
            map.put("order",order);
            map.put("orderGoods",list);
            resultMap.put("data",map);
        }else {
            resultMap.put("code",0);
            resultMap.put("msg","订单详情信息获取失败");
        }
        return resultMap;
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Order> queryAllByLimit(int offset, int limit) {
        return this.orderMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param order 实例对象
     * @return 实例对象
     */
    @Override
    public Map<String,Object> insert(Order order) {
        String orderSn = generateOrderSn(order.getUserId());
        order.setOrderSn(orderSn);
        order.setAddTime(LocalDateTime.now());
        Map<String,Object> map = new HashMap<>();

//        OrderGoods orderGoods = new OrderGoods();
//        if (order != null){
//            orderGoods.setOrderId(Integer.valueOf(order.getOrderSn()));
//        }
        int count = this.orderMapper.insert(order);
        if (count > 0){
            map.put("orderId",order.getId());
            map.put("code",1);
            map.put("msg","订单商品添加成功");
        }else {
            map.put("code",0);
            map.put("msg","订单商品添加失败");
        }
        return map;
    }

    /**
     * 修改数据
     *
     * @param order 实例对象
     * @return 实例对象
     */
    @Override
    public Map<String, Object> update(Order order) {
        if (order.getOrderStatus() == 1 && order.getShipTime() == null){
            order.setPayTime(LocalDateTime.now());
        }
        Map<String, Object> map = new HashMap<>();
        order.setAddTime(LocalDateTime.now());
        int count = this.orderMapper.update(order);
        if(count > 0){
            map.put("code",1);
            map.put("msg","订单信息更新成功");
        }else {
            map.put("code",0);
            map.put("msg","订单信息更新失败");
        }
        return map;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public Map<String,Object> deleteById(String id) {
        Map<String,Object> map = new HashMap<>();
        int count = orderMapper.deleteById(id);
        if (count > 0){
            map.put("code",1);
            map.put("msg","订单删除成功");
        }else {
            map.put("code",0);
            map.put("msg","订单删除失败");
        }
        return map;
    }

    @Override
    public Map<String, Object> getChannels() {
        Map<String,Object> resultMap = new HashMap<>();
        List<Object> list = new ArrayList<>();
        return null;
    }

    @Override
    public PageInfo<OrderAllinone> getList(Integer page, Integer pageSize, Integer orderStatus, String userId, String orderSn) {
        PageInfo<OrderAllinone> pageInfo = null;
        PageHelper.startPage(page, pageSize);
        List<OrderAllinone> list = orderMapper.getList(orderStatus, userId, orderSn);
        try {
            pageInfo = new PageInfo<>(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageInfo;
    }

    @Override
    public Map<String, Object> ship(Order order) {
        Map<String,Object> map = new HashMap<>();
        order.setShipTime(LocalDateTime.now());
        order.setOrderStatus(2);
        int count = orderMapper.update(order);
        if (count > 0){
            map.put("code",1);
            map.put("msg","发货成功");
        }else {
            map.put("code",0);
            map.put("msg","发货失败，请重试");
        }
        return map;
    }

    @Override
    public int getOrderNum() {
        return orderMapper.getOrderNum();
    }

    // TODO 这里应该产生一个唯一的订单，但是实际上这里仍然存在两个订单相同的可能性
    public String generateOrderSn(Integer userId) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        String now = df.format(LocalDate.now());
        String orderSn = now + getRandomNum(6);
        return orderSn;
    }


    private String getRandomNum(Integer num) {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

}