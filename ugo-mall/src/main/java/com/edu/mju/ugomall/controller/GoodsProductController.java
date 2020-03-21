package com.edu.mju.ugomall.controller;

import com.edu.mju.ugomall.entity.GoodsProduct;
import com.edu.mju.ugomall.service.GoodsProductService;
import com.edu.mju.ugomall.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 商品货品表(GoodsProduct)表控制层
 *
 * @author makejava
 * @since 2020-03-06 21:04:41
 */
@RestController
@RequestMapping("/ugoMall/goodsProductController")
public class GoodsProductController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private StringUtils stringUtils = new StringUtils();
    /**
     * 服务对象
     */
    @Resource
    private GoodsProductService goodsProductService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public GoodsProduct selectOne(Integer id) {
        return this.goodsProductService.queryById(id);
    }


    @RequestMapping(value = "/getList")
    @ResponseBody
    public Map<String,Object> getList(){
        logger.info("查询所有商品货品");
        return goodsProductService.getList();
    }


    @RequestMapping(value = "/insert")
    @ResponseBody
    public Map<String,Object> insert(@RequestBody GoodsProduct goodsProduct){
        logger.info("插入商品货品");
        return goodsProductService.insert(goodsProduct);
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Map<String,Object> delete(@RequestParam String id){
        logger.info("删除商品货品");
        return goodsProductService.deleteById(id);
    }


    @RequestMapping(value = "/update")
    @ResponseBody
    public Map<String,Object> update(@RequestBody GoodsProduct goodsProduct){
        logger.info("更新商品货品");
        return goodsProductService.update(goodsProduct);
    }


}