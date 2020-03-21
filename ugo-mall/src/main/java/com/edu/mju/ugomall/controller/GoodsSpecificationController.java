package com.edu.mju.ugomall.controller;

import com.edu.mju.ugomall.entity.GoodsSpecification;
import com.edu.mju.ugomall.service.GoodsSpecificationService;
import com.edu.mju.ugomall.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 商品规格表(GoodsSpecification)表控制层
 *
 * @author makejava
 * @since 2020-03-06 22:04:02
 */
@RestController
@RequestMapping("/ugoMall/goodsSpecificationController")
public class GoodsSpecificationController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private StringUtils stringUtils = new StringUtils();
    /**
     * 服务对象
     */
    @Resource
    private GoodsSpecificationService goodsSpecificationService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public GoodsSpecification selectOne(Integer id) {
        return this.goodsSpecificationService.queryById(id);
    }

    @RequestMapping(value = "/getList")
    @ResponseBody
    public Map<String,Object> getList(){
        logger.info("查询所有商品规格");
        return goodsSpecificationService.getList();
    }


    @RequestMapping(value = "/insert")
    @ResponseBody
    public Map<String,Object> insert(@RequestBody GoodsSpecification goodsSpecification){
        logger.info("插入商品规格");
        return goodsSpecificationService.insert(goodsSpecification);
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Map<String,Object> delete(@RequestParam String id){
        logger.info("删除商品规格");
        return goodsSpecificationService.deleteById(id);
    }


    @RequestMapping(value = "/getByGoodsId")
    @ResponseBody
    public Map<String,Object> getByGoodsId(@RequestParam String goodsId){
        logger.info("查询所有商品规格");
        return goodsSpecificationService.getByGoodsId(goodsId);
    }


}