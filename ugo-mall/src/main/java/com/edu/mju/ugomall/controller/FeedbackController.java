package com.edu.mju.ugomall.controller;

import com.edu.mju.ugomall.entity.Feedback;
import com.edu.mju.ugomall.service.FeedbackService;
import com.edu.mju.ugomall.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 意见反馈表(Feedback)表控制层
 *
 * @author makejava
 * @since 2020-03-20 15:43:22
 */
@RestController
@RequestMapping("/ugoMall/feedbackController")
public class FeedbackController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private StringUtils stringUtils = new StringUtils();
    /**
     * 服务对象
     */
    @Resource
    private FeedbackService feedbackService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Feedback selectOne(Integer id) {
        return this.feedbackService.queryById(id);
    }


    @ResponseBody
    @RequestMapping(value = "/insert")
    public Map<String,Object> insert(@RequestBody Feedback feedback){
        return feedbackService.insert(feedback);
    }

    @ResponseBody
    @RequestMapping(value = "/getList")
    public PageInfo<Feedback> getList(@RequestParam(required = false) String type, @RequestParam(required = false) String userId,
                                      @RequestParam Integer page, @RequestParam Integer pageSize) {
        type = stringUtils.isEmpty(type) ? "" : type;
        userId = stringUtils.isEmpty(userId) ? "" : userId;
        PageInfo<Feedback> pageInfo = feedbackService.getList(page, pageSize, type, userId);
        return pageInfo;
    }

}