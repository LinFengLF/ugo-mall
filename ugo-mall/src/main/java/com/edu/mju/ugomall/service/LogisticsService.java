package com.edu.mju.ugomall.service;

import com.edu.mju.ugomall.entity.Logistics;
import java.util.List;
import java.util.Map;

/**
 * (Logistics)表服务接口
 *
 * @author makejava
 * @since 2020-03-16 09:40:01
 */
public interface LogisticsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Logistics queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Logistics> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param logistics 实例对象
     * @return 实例对象
     */
    Logistics insert(Logistics logistics);

    /**
     * 修改数据
     *
     * @param logistics 实例对象
     * @return 实例对象
     */
    Logistics update(Logistics logistics);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    Map<String,Object> getList();

}