package com.edu.mju.ugomall.controller;

import com.edu.mju.ugomall.entity.Order;
import com.edu.mju.ugomall.entity.OrderAllinone;
import com.edu.mju.ugomall.entity.OrderGoods;
import com.edu.mju.ugomall.entity.User;
import com.edu.mju.ugomall.service.OrderService;
import com.edu.mju.ugomall.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

/**
 * 订单表(Order)表控制层
 *
 * @author makejava
 * @since 2020-03-01 21:31:59
 */
@RestController
@RequestMapping("/ugoMall/orderController")
public class OrderController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private StringUtils stringUtils = new StringUtils();
    private KdniaoTrackQueryAPI kdniaoTrackQueryAPI = new KdniaoTrackQueryAPI();
    /**
     * 服务对象
     */
    @Resource
    private OrderService orderService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ResponseBody
    @RequestMapping(value = "/selectOne")
    public Map<String,Object> selectOne(Integer id) {
        return this.orderService.queryById(id);
    }


    @ResponseBody
    @RequestMapping(value = "/insert")
    public Map<String,Object> insert(@RequestBody Order order){
        return orderService.insert(order);
    }

    @ResponseBody
    @RequestMapping(value = "/update")
    public Map<String,Object> update(@RequestBody Order order){
        return orderService.update(order);
    }


    @ResponseBody
    @RequestMapping(value = "/ship")
    public Map<String,Object> ship(@RequestBody Order order){
        return orderService.ship(order);
    }

    @ResponseBody
    @RequestMapping(value = "/getList")
    public PageInfo<OrderAllinone> getList(@RequestParam(required = false) Integer orderStatus, @RequestParam(required = false) String userId,
                                           @RequestParam(required = false)String orderSn, @RequestParam Integer page, @RequestParam Integer pageSize) {
        userId = stringUtils.isEmpty(userId) ? "" :userId;
        orderSn = stringUtils.isEmpty(orderSn) ? "" :orderSn;
        PageInfo<OrderAllinone> pageInfo = orderService.getList(page,pageSize,orderStatus,userId,orderSn);
        return pageInfo;
    }

    /**
     * 物流即时查询接口
     * @param expCode
     * @param expNo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryLogistics")
    public String QueryLogistics(@RequestParam String expCode,@RequestParam String expNo){
        try{
           String result = kdniaoTrackQueryAPI.getOrderTracesByJson(expCode, expNo);
           return result;
        }catch (Exception e){
            String result = "未查询到物流相关信息";
            return result;
        }
    }

}