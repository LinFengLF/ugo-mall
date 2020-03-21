package com.edu.mju.ugomall.service.impl;

import com.edu.mju.ugomall.entity.GoodsProduct;
import com.edu.mju.ugomall.mapper.GoodsProductMapper;
import com.edu.mju.ugomall.service.GoodsProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品货品表(GoodsProduct)表服务实现类
 *
 * @author makejava
 * @since 2020-03-06 21:04:41
 */
@Service
public class GoodsProductServiceImpl implements GoodsProductService {
    @Resource
    private GoodsProductMapper goodsProductMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public GoodsProduct queryById(Integer id) {
        return this.goodsProductMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<GoodsProduct> queryAllByLimit(int offset, int limit) {
        return this.goodsProductMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param goodsProduct 实例对象
     * @return 实例对象
     */
    @Override
    public Map<String,Object> insert(GoodsProduct goodsProduct) {
        Map<String,Object> resultMap = new HashMap<>();
        int count = this.goodsProductMapper.insert(goodsProduct);
        if(count > 0){
            resultMap.put("code",1);
            resultMap.put("msg","商品货品表添加成功");
        }else {
            resultMap.put("code",0);
            resultMap.put("msg","商品货品表添加失败");
        }
        return resultMap;
    }

    /**
     * 修改数据
     *
     * @param goodsProduct 实例对象
     * @return 实例对象
     */
    @Override
    public Map<String,Object> update(GoodsProduct goodsProduct) {
        Map<String,Object> resultMap = new HashMap<>();
        int count = this.goodsProductMapper.update(goodsProduct);
        if(count > 0){
            resultMap.put("code",1);
            resultMap.put("msg","商品货品表更新成功");
        }else {
            resultMap.put("code",0);
            resultMap.put("msg","商品货品表更新失败");
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
        int count = this.goodsProductMapper.deleteById(id);
        if(count > 0){
            resultMap.put("code",1);
            resultMap.put("msg","商品货品表删除成功");
        }else {
            resultMap.put("code",0);
            resultMap.put("msg","商品货品表删除失败");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getList() {
        Map<String, Object> resultMap = new HashMap<>();
        List<GoodsProduct> list = new ArrayList<>();
        if (goodsProductMapper.getList().size() > 0) {
            list = goodsProductMapper.getList();
            resultMap.put("data", list);
            resultMap.put("msg", "商品货品列表获取成功");
            resultMap.put("code", 1);
        } else {
            resultMap.put("data", null);
            resultMap.put("msg", "商品货品列表获取失败");
            resultMap.put("code", 0);
        }
        return resultMap;
    }
}