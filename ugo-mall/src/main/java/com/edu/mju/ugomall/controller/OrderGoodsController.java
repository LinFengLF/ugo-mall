package com.edu.mju.ugomall.controller;

import com.edu.mju.ugomall.entity.OrderGoods;
import com.edu.mju.ugomall.entity.User;
import com.edu.mju.ugomall.service.OrderGoodsService;
import com.edu.mju.ugomall.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 订单商品表(OrderGoods)表控制层
 *
 * @author makejava
 * @since 2020-03-13 23:51:04
 */
@RestController
@RequestMapping("/ugoMall/orderGoodsController")
public class OrderGoodsController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private StringUtils stringUtils = new StringUtils();
    /**
     * 服务对象
     */
    @Resource
    private OrderGoodsService orderGoodsService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public OrderGoods selectOne(Integer id) {
        return this.orderGoodsService.queryById(id);
    }


    @ResponseBody
    @RequestMapping(value = "/getList")
    public PageInfo<OrderGoods> getList(@RequestParam Integer page, @RequestParam Integer pageSize) {
        logger.info("获取订单商品所有数据");
        PageInfo<OrderGoods> pageInfo = orderGoodsService.getList(page, pageSize);
        return pageInfo;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteByBatch")
    public Map<String, Object> deleteByBatch(@RequestParam String ids) {
        logger.info("删除方法");
        String[] arr = ids.split(",");
        return orderGoodsService.deleteByBatch(arr);
    }


    @ResponseBody
    @RequestMapping(value = "/insert")
    public Map<String, Object> insert(@RequestBody OrderGoods orderGoods) {
        logger.info("添加方法");
        return orderGoodsService.insert(orderGoods);
    }

}