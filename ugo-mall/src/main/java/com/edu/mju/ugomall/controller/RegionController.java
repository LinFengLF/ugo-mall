//package com.edu.mju.ugomall.controller;
//
//import com.edu.mju.ugomall.entity.Region;
//import com.edu.mju.ugomall.service.RegionService;
//import com.edu.mju.ugomall.utils.StringUtils;
//import com.github.pagehelper.PageInfo;
//import io.swagger.annotations.ApiOperation;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//import java.util.Map;
//
///**
// * 行政区域表(Region)表控制层
// *
// * @author makejava
// * @since 2020-02-29 16:26:49
// */
//@RestController
//@RequestMapping("/ugoMall/regionController")
//public class RegionController {
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//    private StringUtils stringUtils = new StringUtils();
//
//    /**
//     * 服务对象
//     */
//    @Resource
//    private RegionService regionService;
//
//    /**
//     * 通过主键查询单条数据
//     *
//     * @param id 主键
//     * @return 单条数据
//     */
//    @GetMapping("selectOne")
//    public Region selectOne(Integer id) {
//        return this.regionService.queryById(id);
//    }
//
//    @ApiOperation(value = "查询收藏信息", notes = "查询数据库中收藏信息")
//    @ResponseBody
//    @RequestMapping(value = "/getList")
//    public Map<String,Object> getList() {
//        logger.info("获取所有行政区数据");
//        return regionService.getList();
//    }
//
//
//}