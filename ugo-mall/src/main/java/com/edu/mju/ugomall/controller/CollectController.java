package com.edu.mju.ugomall.controller;

import com.edu.mju.ugomall.entity.Collect;
import com.edu.mju.ugomall.entity.User;
import com.edu.mju.ugomall.service.CollectService;
import com.edu.mju.ugomall.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 收藏表(Collect)表控制层
 *
 * @author makejava
 * @since 2020-02-29 13:09:41
 */
@RestController
@RequestMapping("/ugoMall/collectController")
public class CollectController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private StringUtils stringUtils = new StringUtils();

    /**
     * 服务对象
     */
    @Resource
    private CollectService collectService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Collect selectOne(Integer id) {
        return this.collectService.queryById(id);
    }


    @ApiOperation(value = "查询收藏信息", notes = "查询数据库中收藏信息")
    @ResponseBody
    @RequestMapping(value = "/getCollectInfo")
    public PageInfo<Collect> getUserInfo(@RequestParam(required = false) String userId, @RequestParam(required = false) String goodsId,
                                         @RequestParam Integer page, @RequestParam Integer pageSize) {
        logger.info("获取用户所有数据");
        userId = stringUtils.isEmpty(userId) ? "" : userId;
        goodsId = stringUtils.isEmpty(goodsId) ? "" : goodsId;
        PageInfo<Collect> pageInfo = collectService.getAllCollection(page, pageSize, goodsId, userId);
        return pageInfo;
    }

    @ResponseBody
    @RequestMapping(value = "/insert")
    public Map<String, Object> insert(Collect collect) {
        return collectService.insert(collect);
    }

    @ResponseBody
    @RequestMapping(value = "/getUserCollection")
    public Map<String, Object> getUserCollection(@RequestParam String userId) {
        return collectService.getUserCollection(userId);
    }


    @ResponseBody
    @RequestMapping(value = "/deleteByValueId")
    public Map<String, Object> deleteByValueId(@RequestParam String valueId) {
        return collectService.deleteByValueId(valueId);
    }

    /**
     * 取消收藏
     * @param userId
     * @param valueId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/cancelCollect")
    public Map<String, Object> cancelCollect(@RequestParam String userId, @RequestParam String valueId) {
        return collectService.cancelCollect(userId, valueId);
    }

    /**
     * 判断商品是否被收藏
     * @param userId
     * @param valueId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/isExist")
    public Map<String, Object> isExist(@RequestParam String userId,@RequestParam String valueId){
        return collectService.isExist(userId, valueId);
    }

}