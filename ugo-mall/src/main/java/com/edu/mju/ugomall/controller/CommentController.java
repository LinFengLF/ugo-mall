package com.edu.mju.ugomall.controller;

import com.edu.mju.ugomall.entity.Comment;
import com.edu.mju.ugomall.service.CommentService;
import com.edu.mju.ugomall.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 评论表(Comment)表控制层
 *
 * @author makejava
 * @since 2020-03-15 18:54:03
 */
@RestController
@RequestMapping("/ugoMall/commentController")
public class CommentController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private StringUtils stringUtils = new StringUtils();
    /**
     * 服务对象
     */
    @Resource
    private CommentService commentService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Comment selectOne(Integer id) {
        return this.commentService.queryById(id);
    }


    @ResponseBody
    @RequestMapping(value = "/getList")
    public PageInfo<Comment> getList(@RequestParam(required = false) String userId, @RequestParam(required = false) String valueId,
                                     @RequestParam Integer page, @RequestParam Integer pageSize) {
        logger.info("查询商品评论");
        userId = stringUtils.isEmpty(userId) ? "" : userId;
        valueId = stringUtils.isEmpty(valueId) ? "" : valueId;
        PageInfo<Comment> pageInfo = commentService.getList(page, pageSize, userId, valueId);
        return pageInfo;
    }


    @ResponseBody
    @RequestMapping(value = "/deleteByBatch")
    public Map<String, Object> deleteByBatch(@RequestParam String ids) {
        logger.info("删除方法");
        String[] arr = ids.split(",");
        return commentService.deleteByBatch(arr);
    }


    @ResponseBody
    @RequestMapping(value = "/insert")
    public Map<String, Object> insert(@RequestBody Comment comment) {
        logger.info("添加方法");
        return commentService.insert(comment);
    }
}