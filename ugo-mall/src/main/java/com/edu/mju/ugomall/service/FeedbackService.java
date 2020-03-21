package com.edu.mju.ugomall.service;

import com.edu.mju.ugomall.entity.Feedback;
import com.github.pagehelper.PageInfo;
import org.omg.CORBA.OBJ_ADAPTER;

import java.util.List;
import java.util.Map;

/**
 * 意见反馈表(Feedback)表服务接口
 *
 * @author makejava
 * @since 2020-03-20 15:43:21
 */
public interface FeedbackService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Feedback queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Feedback> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param feedback 实例对象
     * @return 实例对象
     */
    Map<String, Object> insert(Feedback feedback);

    /**
     * 修改数据
     *
     * @param feedback 实例对象
     * @return 实例对象
     */
    Feedback update(Feedback feedback);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);


    PageInfo<Feedback> getList(Integer page, Integer pageSize, String type, String userId);

}