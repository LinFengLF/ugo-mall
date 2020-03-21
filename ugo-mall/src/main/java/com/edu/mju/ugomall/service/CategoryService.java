package com.edu.mju.ugomall.service;

import com.edu.mju.ugomall.entity.Ad;
import com.edu.mju.ugomall.entity.Category;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 类目表(Category)表服务接口
 *
 * @author makejava
 * @since 2020-02-29 21:09:31
 */
public interface CategoryService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Category queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Category> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param category 实例对象
     * @return 实例对象
     */
    Map<String,Object>  insert(Category category);

    /**
     * 修改数据
     *
     * @param category 实例对象
     * @return 实例对象
     */
    Map<String,Object>  update(Category category);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    PageInfo<Category> getList(Integer page, Integer pageSize);

    /**
     * 批量删除+单个删除
     * @param ids
     * @return
     */
    Map<String, Object> deleteByBatch(@Param("ids")String[] ids);


    Map<String,Object> getCateGoryList();

}