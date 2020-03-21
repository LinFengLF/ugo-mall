package com.edu.mju.ugomall.service.impl;

import com.edu.mju.ugomall.entity.GoodsAttribute;
import com.edu.mju.ugomall.mapper.GoodsAttributeMapper;
import com.edu.mju.ugomall.service.GoodsAttributeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品参数表(GoodsAttribute)表服务实现类
 *
 * @author makejava
 * @since 2020-03-06 16:55:03
 */
@Service
public class GoodsAttributeServiceImpl implements GoodsAttributeService {
    @Resource
    private GoodsAttributeMapper goodsAttributeMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public GoodsAttribute queryById(Integer id) {
        return this.goodsAttributeMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<GoodsAttribute> queryAllByLimit(int offset, int limit) {
        return this.goodsAttributeMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param goodsAttribute 实例对象
     * @return 实例对象
     */
    @Override
    public Map<String, Object> insert(GoodsAttribute goodsAttribute) {
        Map<String, Object> resultMap = new HashMap<>();
        int count = this.goodsAttributeMapper.insert(goodsAttribute);
        if (count > 0) {
            resultMap.put("code", 1);
            resultMap.put("msg", "商品参数添加成功");
        } else {
            resultMap.put("code", 0);
            resultMap.put("msg", "商品参数添加失败");
        }
        return resultMap;
    }

    /**
     * 修改数据
     *
     * @param goodsAttribute 实例对象
     * @return 实例对象
     */
    @Override
    public GoodsAttribute update(GoodsAttribute goodsAttribute) {
        this.goodsAttributeMapper.update(goodsAttribute);
        return this.queryById(goodsAttribute.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public Map<String, Object> deleteById(String id) {
        int count = this.goodsAttributeMapper.deleteById(id);
        Map<String, Object> resultMap = new HashMap<>();
        if (count > 0) {
            resultMap.put("code", 1);
            resultMap.put("msg", "商品参数删除成功");
        } else {
            resultMap.put("code", 0);
            resultMap.put("msg", "商品参数删除失败");
        }

            return resultMap;
        }

        @Override
        public Map<String, Object> getList () {
            Map<String, Object> resultMap = new HashMap<>();
            List<GoodsAttribute> list = new ArrayList<>();
            if (goodsAttributeMapper.getList().size() > 0) {
                list = goodsAttributeMapper.getList();
                resultMap.put("data", list);
                resultMap.put("msg", "商品参数列表获取成功");
                resultMap.put("code", 1);
            } else {
                resultMap.put("data", null);
                resultMap.put("msg", "商品参数列表获取失败");
                resultMap.put("code", 0);
            }
            return resultMap;
        }
    }