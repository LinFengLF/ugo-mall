package com.edu.mju.ugomall.service.impl;

import com.edu.mju.ugomall.entity.GoodsSpecification;
import com.edu.mju.ugomall.mapper.GoodsSpecificationMapper;
import com.edu.mju.ugomall.service.GoodsSpecificationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品规格表(GoodsSpecification)表服务实现类
 *
 * @author makejava
 * @since 2020-03-06 22:04:02
 */
@Service
public class GoodsSpecificationServiceImpl implements GoodsSpecificationService {
    @Resource
    private GoodsSpecificationMapper goodsSpecificationMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public GoodsSpecification queryById(Integer id) {
        return this.goodsSpecificationMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<GoodsSpecification> queryAllByLimit(int offset, int limit) {
        return this.goodsSpecificationMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param goodsSpecification 实例对象
     * @return 实例对象
     */
    @Override
    public Map<String,Object> insert(GoodsSpecification goodsSpecification) {
        Map<String,Object> resultMap = new HashMap<>();
        int count = this.goodsSpecificationMapper.insert(goodsSpecification);
        if(count > 0){
            resultMap.put("code",1);
            resultMap.put("msg","商品规格添加成功");
        }else {
            resultMap.put("code",0);
            resultMap.put("msg","商品规格添加失败");
        }
        return resultMap;
    }

    /**
     * 修改数据
     *
     * @param goodsSpecification 实例对象
     * @return 实例对象
     */
    @Override
    public Map<String,Object> update(GoodsSpecification goodsSpecification) {
        Map<String,Object> resultMap = new HashMap<>();
        int count = this.goodsSpecificationMapper.update(goodsSpecification);
        if(count > 0){
            resultMap.put("code",1);
            resultMap.put("msg","商品规格更新成功");
        }else {
            resultMap.put("code",0);
            resultMap.put("msg","商品规格更新失败");
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
    public Map<String,Object> deleteById(String id) {
        Map<String,Object> resultMap = new HashMap<>();
        int count = this.goodsSpecificationMapper.deleteById(id);
        if(count > 0){
            resultMap.put("code",1);
            resultMap.put("msg","商品规格删除成功");
        }else {
            resultMap.put("code",0);
            resultMap.put("msg","商品规格删除失败");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getList() {
        Map<String, Object> resultMap = new HashMap<>();
        List<GoodsSpecification> list = new ArrayList<>();
        if (goodsSpecificationMapper.getList().size() > 0) {
            list = goodsSpecificationMapper.getList();
            resultMap.put("data", list);
            resultMap.put("msg", "商品规格获取成功");
            resultMap.put("code", 1);
        } else {
            resultMap.put("data", null);
            resultMap.put("msg", "商品规格获取失败");
            resultMap.put("code", 0);
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getByGoodsId(String goodsId) {
        Map<String, Object> resultMap = new HashMap<>();
        List<GoodsSpecification> list = new ArrayList<>();
        if (goodsSpecificationMapper.getByGoodsId(goodsId).size() > 0) {
            list = goodsSpecificationMapper.getByGoodsId(goodsId);
            resultMap.put("data", list);
            resultMap.put("msg", "商品规格获取成功");
            resultMap.put("code", 1);
        } else {
            resultMap.put("data", null);
            resultMap.put("msg", "商品规格获取失败");
            resultMap.put("code", 0);
        }
        return resultMap;
    }
}