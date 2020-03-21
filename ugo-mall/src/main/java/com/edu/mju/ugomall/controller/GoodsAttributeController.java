package com.edu.mju.ugomall.controller;

import com.edu.mju.ugomall.entity.GoodsAttribute;
import com.edu.mju.ugomall.service.GoodsAttributeService;
import com.edu.mju.ugomall.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 商品参数表(GoodsAttribute)表控制层
 *
 * @author makejava
 * @since 2020-03-06 16:55:04
 */
@RestController
@RequestMapping("/ugoMall/goodsAttributeController")
public class GoodsAttributeController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private StringUtils stringUtils = new StringUtils();
    /**
     * 服务对象
     */
    @Resource
    private GoodsAttributeService goodsAttributeService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public GoodsAttribute selectOne(Integer id) {
        return this.goodsAttributeService.queryById(id);
    }

    @RequestMapping(value = "/getList")
    @ResponseBody
    public Map<String,Object> getList(){
        logger.info("查询所有商品参数");
        return goodsAttributeService.getList();
    }


    @RequestMapping(value = "/insert")
    @ResponseBody
    public Map<String,Object> insert(@RequestBody GoodsAttribute goodsAttribute){
        logger.info("插入商品参数");
        return goodsAttributeService.insert(goodsAttribute);
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Map<String,Object> delete(@RequestParam String id){
        logger.info("删除商品参数");
        return goodsAttributeService.deleteById(id);
    }

}