package com.edu.mju.ugomall.service;

import com.edu.mju.ugomall.entity.Ad;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 广告表(Ad)表服务接口
 *
 * @author makejava
 * @since 2020-02-23 21:22:51
 */
public interface AdService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Ad queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Ad> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param ad 实例对象
     * @return 实例对象
     */
    Map<String,Object> insert(Ad ad);

    /**
     * 修改数据
     *
     * @param ad 实例对象
     * @return 实例对象
     */
    Map<String, Object> update(Ad ad);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    PageInfo<Ad> getAdInfo(Integer page, Integer pageSize, String name, String content);


    /**
     * 批量删除+单个删除
     * @param ids
     * @return
     */
    Map<String, Object> deleteByBatch(@Param("ids")String[] ids);

}