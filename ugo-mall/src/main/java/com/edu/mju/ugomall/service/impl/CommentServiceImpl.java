package com.edu.mju.ugomall.service.impl;

import com.edu.mju.ugomall.entity.Comment;
import com.edu.mju.ugomall.entity.Order;
import com.edu.mju.ugomall.mapper.CommentMapper;
import com.edu.mju.ugomall.mapper.OrderMapper;
import com.edu.mju.ugomall.service.CommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2020-03-15 18:54:03
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentMapper commentMapper;
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Comment queryById(Integer id) {
        return this.commentMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Comment> queryAllByLimit(int offset, int limit) {
        return this.commentMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param comment 实例对象
     * @return 实例对象
     */
    @Override
    public Map<String,Object> insert(Comment comment) {
        Map<String,Object> map = new HashMap<>();
        comment.setAddTime(LocalDateTime.now());
        int count = this.commentMapper.insert(comment);
        if (count > 0){
            map.put("code",1);
            map.put("msg","商品评论添加成功");
        }else {
            map.put("code",0);
            map.put("msg","商品评论添加失败");
        }
        return map;
    }

    /**
     * 修改数据
     *
     * @param comment 实例对象
     * @return 实例对象
     */
    @Override
    public Comment update(Comment comment) {
        this.commentMapper.update(comment);
        return this.queryById(comment.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.commentMapper.deleteById(id) > 0;
    }


    @Override
    public PageInfo<Comment> getList(Integer page, Integer pageSize, String userId,String valueId) {
        PageInfo<Comment> pageInfo = null;
        PageHelper.startPage(page, pageSize);
        List<Comment> list = commentMapper.getList(userId,valueId);
        try {
            pageInfo = new PageInfo<>(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageInfo;
    }

    @Override
    public Map<String, Object> deleteByBatch(String[] ids) {
        Map<String, Object> resultMap = new HashMap<>();
        int count = commentMapper.deleteByBatch(ids);
        if (count >= 1) {
            resultMap.put("msg", "用户评论删除成功");
            resultMap.put("code", "1");
        } else {
            resultMap.put("msg", "用户评论删除失败");
            resultMap.put("code", "0");
        }
        return resultMap;
    }

    @Override
    public int getCommentNum() {
        return commentMapper.getCommentNum();
    }
}