package com.edu.mju.ugomall.service;

import com.edu.mju.ugomall.entity.Comment;
import com.edu.mju.ugomall.entity.User;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2020-03-15 18:54:03
 */
public interface CommentService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Comment queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Comment> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param comment 实例对象
     * @return 实例对象
     */
    Map<String,Object> insert(Comment comment);

    /**
     * 修改数据
     *
     * @param comment 实例对象
     * @return 实例对象
     */
    Comment update(Comment comment);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);


    PageInfo<Comment> getList(Integer page, Integer pageSize, String userId,String valueId);

    Map<String, Object> deleteByBatch(@Param("ids")String[] ids);

    int getCommentNum();

}