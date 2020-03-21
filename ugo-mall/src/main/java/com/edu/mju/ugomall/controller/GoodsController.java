package com.edu.mju.ugomall.controller;

import com.edu.mju.ugomall.entity.Goods;
import com.edu.mju.ugomall.entity.GoodsAllinone;
import com.edu.mju.ugomall.service.GoodsService;
import com.edu.mju.ugomall.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 商品基本信息表(Goods)表控制层
 *
 * @author makejava
 * @since 2020-02-20 00:05:28
 */
@RestController
@RequestMapping("/ugoMall/goodsController")
public class GoodsController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private StringUtils stringUtils = new StringUtils();
    /**
     * 服务对象
     */
    @Resource
    private GoodsService goodsService;

    @ApiOperation(value = "查询商品信息", notes = "查询数据库中商品信息")
    @ResponseBody
    @RequestMapping(value = "/getGoodsList")
    public PageInfo<Goods> getGoodsList(@RequestParam(required = false) String name, @RequestParam(required = false) String id,
                                        @RequestParam(required = false) String goodsSn, @RequestParam(required = false) String categoryId,
                                        @RequestParam Integer page, @RequestParam Integer pageSize) {
        logger.info("获取商品所有数据");
        if (name == null || ("").equals(name)) {
            name = "";
        }
        if (id == null || ("").equals(id)) {
            id = "";
        }
        if (goodsSn == null || ("").equals(goodsSn)) {
            goodsSn = "";
        }
        categoryId = stringUtils.isEmpty(categoryId) ? "" : categoryId;
        PageInfo<Goods> pageInfo = goodsService.getGoodsList(page, pageSize, name, id, goodsSn, categoryId);
        return pageInfo;
    }

    /**
     * 批量删除+单个删除
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteByBatch")
    public Map<String, Object> deleteByBatch(@RequestParam String ids) {
        logger.info("删除方法");
        String[] arr = ids.split(",");
        return goodsService.deleteByBatch(arr);
    }

    /**
     * 商品上架
     *
     * @param goods
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insert")
    public Map<String, Object> insert(@RequestBody Goods goods) {
        logger.info("添加方法");
        return goodsService.insert(goods);
    }

    /**
     * 通过ID查询
     *  把商品规格信息一起返回
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryById")
    public Map<String,Object> queryById(@RequestParam String id) {
//        Goods goods = goodsService.queryById(id);
        return goodsService.queryById(id);
    }

    /**
     * 更新商品信息
     *
     * @param goods
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update")
    public Map<String, Object> update(@RequestBody Goods goods) {
        Integer id = goods.getId();
        logger.info("**********************商品的id为" + id);
        logger.info("更新方法");
        return goodsService.update(goods);
    }

    @ResponseBody
    @RequestMapping(value = "/publishGoods")
    public Map<String,Object> publishGoods(@RequestBody GoodsAllinone goodsAllinone){
        return goodsService.publishGoods(goodsAllinone);
    }

    @ResponseBody
    @RequestMapping(value = "/editGoods")
    public Map<String,Object> editGoods(@RequestBody GoodsAllinone goodsAllinone){
        return goodsService.editGoods(goodsAllinone);
    }

}