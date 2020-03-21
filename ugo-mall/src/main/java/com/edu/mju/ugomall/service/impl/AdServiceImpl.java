package com.edu.mju.ugomall.service.impl;

import com.edu.mju.ugomall.entity.Ad;
import com.edu.mju.ugomall.mapper.AdMapper;
import com.edu.mju.ugomall.service.AdService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 广告表(Ad)表服务实现类
 *
 * @author makejava
 * @since 2020-02-23 21:22:51
 */
@Service
public class AdServiceImpl implements AdService {
    @Resource
    private AdMapper adMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Ad queryById(Integer id) {
        return this.adMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Ad> queryAllByLimit(int offset, int limit) {
        return this.adMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param ad 实例对象
     * @return 实例对象
     */
    @Override
    public Map<String,Object> insert(Ad ad) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int count = adMapper.insert(ad);
            if (count >= 1) {
                resultMap.put("msg", "广告添加成功");
                resultMap.put("code", "1");
            } else {
                resultMap.put("msg", "广告添加失败");
                resultMap.put("code", "0");
            }
        } catch (Exception e) {
            resultMap.put("msg", "广告添加失败");
            resultMap.put("code", "0");
        }
        return resultMap;
    }

    /**
     * 修改数据
     *
     * @param ad 实例对象
     * @return 实例对象
     */
    @Override
    public Map<String, Object> update(Ad ad) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int count = adMapper.update(ad);
            System.out.println("更新的条数为：" + count);
            if (count >= 1) {
                resultMap.put("msg", "广告信息更新成功");
                resultMap.put("code", "1");
            } else {
                resultMap.put("msg", "广告信息更新失败");
                resultMap.put("code", "0");
            }
        } catch (Exception e) {
            resultMap.put("msg", "广告信息更新失败");
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
        return this.adMapper.deleteById(id) > 0;
    }

    /**
     * 带分页的查询
     * @param page
     * @param pageSize
     * @param name
     * @param content
     * @return
     */
    @Override
    public PageInfo<Ad> getAdInfo(Integer page, Integer pageSize, String name, String content) {
        PageInfo<Ad> pageInfo = null;
        PageHelper.startPage(page, pageSize);
        List<Ad> list = adMapper.getAdInfo(name, content);
        try {
            pageInfo = new PageInfo<>(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageInfo;
    }

    /**
     * 单个删除+批量删除
     * @param ids
     * @return
     */
    @Override
    public Map<String, Object> deleteByBatch(String[] ids) {
        Map<String, Object> resultMap = new HashMap<>();
        int count = adMapper.deleteByBatch(ids);
        if (count >= 1) {
            resultMap.put("msg", "广告删除成功");
            resultMap.put("code", "1");
        } else {
            resultMap.put("msg", "广告删除失败");
            resultMap.put("code", "0");
        }
        return resultMap;
    }
}