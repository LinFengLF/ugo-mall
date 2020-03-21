package com.edu.mju.ugomall.controller;

import com.edu.mju.ugomall.entity.GoodsCart;
import com.edu.mju.ugomall.service.GoodsCartService;
import com.edu.mju.ugomall.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 购物车商品表(GoodsCart)表控制层
 *
 * @author makejava
 * @since 2020-03-09 09:59:13
 */
@RestController
@RequestMapping("/ugoMall/goodsCartController")
public class GoodsCartController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private StringUtils stringUtils = new StringUtils();
    /**
     * 服务对象
     */
    @Resource
    private GoodsCartService goodsCartService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public GoodsCart selectOne(Integer id) {
        return this.goodsCartService.queryById(id);
    }


    @ResponseBody
    @RequestMapping(value = "/insert")
    public Map<String,Object> insert(@RequestBody GoodsCart goodsCart){
        return goodsCartService.insert(goodsCart);
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public Map<String,Object> delete(String id){
        return goodsCartService.deleteById(id);
    }

    @ResponseBody
    @RequestMapping(value = "/update")
    public Map<String,Object> update(@RequestBody GoodsCart goodsCart){
        return goodsCartService.update(goodsCart);
    }

    @ResponseBody
    @RequestMapping(value = "/getList")
    public Map<String,Object> getList(){
        return goodsCartService.getList();
    }

    @ResponseBody
    @RequestMapping(value = "/getListByUserId")
    public Map<String,Object> getListByUserId(@RequestParam String userId){
        return goodsCartService.getListByUserId(userId);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteByBatch")
    public Map<String, Object> deleteByBatch(@RequestBody String[] ids) {
        logger.info("删除方法");
//        String[] arr = ids.split(",");
        return goodsCartService.deleteByBatch(ids);
    }

}