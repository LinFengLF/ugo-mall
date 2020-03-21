package com.edu.mju.ugomall.service.impl;

import com.edu.mju.ugomall.entity.Category;
import com.edu.mju.ugomall.mapper.CategoryMapper;
import com.edu.mju.ugomall.service.CategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.corba.se.spi.ior.ObjectKey;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类目表(Category)表服务实现类
 *
 * @author makejava
 * @since 2020-02-29 21:09:32
 */
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Category queryById(Integer id) {
        return this.categoryMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Category> queryAllByLimit(int offset, int limit) {
        return this.categoryMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param category 实例对象
     * @return 实例对象
     */
    @Override
    public Map<String, Object> insert(Category category) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int count = categoryMapper.insert(category);
            if (count >= 1) {
                resultMap.put("msg", "类别添加成功");
                resultMap.put("code", "1");
            } else {
                resultMap.put("msg", "类别添加失败");
                resultMap.put("code", "0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("msg", "类别添加失败");
            resultMap.put("code", "0");
        }
        return resultMap;
    }

    /**
     * 修改数据
     *
     * @param category 实例对象
     * @return 实例对象
     */
    @Override
    public Map<String, Object> update(Category category) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int count = categoryMapper.update(category);
            System.out.println("更新的条数为：" + count);
            if (count >= 1) {
                resultMap.put("msg", "类别信息更新成功");
                resultMap.put("code", "1");
            } else {
                resultMap.put("msg", "类别信息更新失败");
                resultMap.put("code", "0");
            }
        } catch (Exception e) {
            resultMap.put("msg", "类别信息更新失败");
            resultMap.put("code", "0");
        }
        return resultMap;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.categoryMapper.deleteById(id) > 0;
    }

    @Override
    public PageInfo<Category> getList(Integer page, Integer pageSize) {
        PageInfo<Category> pageInfo = null;
        PageHelper.startPage(page, pageSize);
        List<Category> list = categoryMapper.getList();
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
        int count = categoryMapper.deleteByBatch(ids);
        if (count >= 1) {
            resultMap.put("msg", "类别删除成功");
            resultMap.put("code", "1");
        } else {
            resultMap.put("msg", "类别删除失败");
            resultMap.put("code", "0");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getCateGoryList() {
        Map<String, Object> resultMap = new HashMap<>();

        List<Object> cateGoryList = new ArrayList();
        List<Category> list = categoryMapper.getList();
        System.out.println("类目的列表为：------------" + list);
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("label", list.get(i).getName());
            map.put("value", list.get(i).getId());
//            resultMap.put("data",map);
            cateGoryList.add(map);
        }
        resultMap.put("data", cateGoryList);
        return resultMap;
    }
}