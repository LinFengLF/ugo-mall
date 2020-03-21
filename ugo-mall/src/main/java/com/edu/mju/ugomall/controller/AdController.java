package com.edu.mju.ugomall.controller;

import com.edu.mju.ugomall.entity.Ad;
import com.edu.mju.ugomall.service.AdService;
import com.edu.mju.ugomall.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 广告表(Ad)表控制层
 *
 * @author makejava
 * @since 2020-02-23 21:22:51
 */
@RestController
@RequestMapping("/ugoMall/adController")
public class AdController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private StringUtils stringUtils = new StringUtils();
    /**
     * 服务对象
     */
    @Autowired
    private AdService adService;



    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Ad selectOne(Integer id) {
        return this.adService.queryById(id);
    }


    @ApiOperation(value = "查询所有广告信息")
    @ResponseBody
    @RequestMapping(value = "/getAdInfo")
    public PageInfo<Ad> getAdInfo(@RequestParam(required = false) String name,
                                  @RequestParam Integer page,
                                  @RequestParam Integer pageSize,
                                  @RequestParam(required = false) String content) {
        logger.info("查询所有广告信息");
        name = stringUtils.isEmpty(name) ?  "" : name;
        content = stringUtils.isEmpty(content) ?  "" : content;
        PageInfo<Ad> pageInfo = adService.getAdInfo(page, pageSize, name, content);
        return pageInfo;
    }

    /**
     * 批量删除+单个删除
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteByBatch")
    public Map<String, Object> deleteByBatch(@RequestParam String ids) {
        logger.info("删除方法");
        String[] arr = ids.split(",");
        return adService.deleteByBatch(arr);
    }

    /**
     * 添加广告
     * @param ad
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insert")
    public Map<String, Object> insert(@RequestBody Ad ad) {
        logger.info("添加方法");
        return adService.insert(ad);
    }

    /**
     * 通过ID查询
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryById")
    public Ad queryById(@RequestParam Integer id){
        Ad ad = adService.queryById(id);
        return ad;
    }

    /**
     * 更新商品信息
     * @param ad
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update")
    public Map<String, Object> update(@RequestBody Ad ad) {
        Integer id = ad.getId();
        logger.info("**********************这条广告的id为" + id);
        logger.info("更新方法");
        return adService.update(ad);
    }




}