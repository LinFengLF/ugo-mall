package com.edu.mju.ugomall.controller;

import com.edu.mju.ugomall.entity.Logistics;
import com.edu.mju.ugomall.service.LogisticsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * (Logistics)表控制层
 *
 * @author makejava
 * @since 2020-03-16 09:40:01
 */
@RestController
@RequestMapping("/ugoMall/logisticsController")
public class LogisticsController {
    /**
     * 服务对象
     */
    @Resource
    private LogisticsService logisticsService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Logistics selectOne(Integer id) {
        return this.logisticsService.queryById(id);
    }

    @ResponseBody
    @RequestMapping(value = "/getList")
    public Map<String,Object> getList(){
        return logisticsService.getList();
    }

}