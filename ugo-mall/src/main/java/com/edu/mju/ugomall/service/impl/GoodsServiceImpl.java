package com.edu.mju.ugomall.service.impl;

import com.edu.mju.ugomall.entity.Goods;
import com.edu.mju.ugomall.entity.GoodsAllinone;
import com.edu.mju.ugomall.entity.GoodsAttribute;
import com.edu.mju.ugomall.entity.GoodsSpecification;
import com.edu.mju.ugomall.mapper.GoodsMapper;
import com.edu.mju.ugomall.mapper.GoodsSpecificationMapper;
import com.edu.mju.ugomall.service.GoodsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品基本信息表(Goods)表服务实现类
 *
 * @author makejava
 * @since 2020-02-20 00:05:26
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsSpecificationMapper goodsSpecificationMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Map<String,Object> queryById(String id) {
        Goods goods = goodsMapper.queryById(id);
        List<GoodsSpecification> specifications = goodsSpecificationMapper.getByGoodsId(id);
        Map<String,Object> map = new HashMap<>();
        map.put("goods",goods);
        map.put("specifications",specifications);
        map.put("code",1);
        map.put("msg","数据获取成功");
        return map;
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Goods> queryAllByLimit(int offset, int limit) {
        return this.goodsMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param goods 实例对象
     * @return 实例对象
     */
    @Override
    public Map<String, Object> insert(Goods goods) {
        Map<String, Object> resultMap = new HashMap<>();
        goods.setAddTime(LocalDateTime.now());
        try {
            int count = goodsMapper.insert(goods);
            System.out.println("添加的条数为： " + count);
            if (count >= 1) {
                resultMap.put("msg", "商品上架成功");
                resultMap.put("code", "1");
            } else {
                resultMap.put("msg", "商品上架失败");
                resultMap.put("code", "0");
            }
        } catch (Exception e) {
            resultMap.put("msg", "商品上架失败");
            resultMap.put("code", "0");
        }
        return resultMap;
    }

    /**
     * 修改数据
     *
     * @param goods 实例对象
     * @return 实例对象
     */
    @Override
    public Map<String, Object> update(Goods goods) {
        Map<String, Object> resultMap = new HashMap<>();
        goods.setUpdateTime(LocalDateTime.now());
        try {
            int count = goodsMapper.update(goods);
            System.out.println("更新的条数为：" + count);
            if (count >= 1) {
                resultMap.put("msg", "商品信息更新成功");
                resultMap.put("code", "1");
            } else {
                resultMap.put("msg", "商品信息更新失败");
                resultMap.put("code", "0");
            }
        } catch (Exception e) {
            resultMap.put("msg", "商品信息更新失败");
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
        return this.goodsMapper.deleteById(id) > 0;
    }

    /**
     * 批量删除+单个删除
     *
     * @param ids
     * @return
     */
    @Override
    public Map<String, Object> deleteByBatch(String[] ids) {
        System.out.println("----------------------" + ids[0]);
        Map<String, Object> resultMap = new HashMap<>();
        int count = goodsMapper.deleteByBatch(ids);
        String goodsId = ids[0];
        if (count >= 1) {
            goodsSpecificationMapper.deleteByGoodsId(goodsId);
            resultMap.put("msg", "删除成功");
            resultMap.put("code", "1");
        } else {
            resultMap.put("msg", "网络错误,删除失败");
            resultMap.put("code", "0");
        }
        return resultMap;
    }

    /**
     * 带分页的商品查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @param id
     * @param goodsSn
     * @return
     */
    @Override
    public PageInfo<Goods> getGoodsList(Integer page, Integer pageSize, String name, String id, String goodsSn,
                                        String categoryId) {
        PageInfo<Goods> pageInfo = null;
        PageHelper.startPage(page, pageSize);
        List<Goods> list = goodsMapper.getGoodsInfo(goodsSn, name, id, categoryId);
        try {
            pageInfo = new PageInfo<>(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageInfo;
    }

    @Override
    public Map<String, Object> publishGoods(GoodsAllinone goodsAllinone) {
        Map<String,Object> resultMap = new HashMap<>();
        Goods goods = goodsAllinone.getGoods();
        GoodsSpecification[] goodsSpecifications = goodsAllinone.getSpecifications();
        GoodsAttribute[] goodsAttributes = goodsAllinone.getAttributes();

        int count = goodsMapper.insert(goods);
        if(count > 0){
            resultMap.put("code",1);
            resultMap.put("msg","商品成功上架");
            //将商品的id设置到商品规格表中,并添加到数据库中
            for (GoodsSpecification specification :goodsSpecifications){
                specification.setGoodsId(goods.getId());
                goodsSpecificationMapper.insert(specification);
            }
        }else {
            resultMap.put("code",0);
            resultMap.put("msg","商品上架失败");
        }
        return resultMap;
    }


    @Override
    public Map<String, Object> editGoods(GoodsAllinone goodsAllinone) {
        Map<String,Object> map = new HashMap<>();
        Goods goods = goodsAllinone.getGoods();
        GoodsSpecification[] specifications = goodsAllinone.getSpecifications();
        int count = goodsMapper.update(goods);
        if(count > 0){
            map.put("code",1);
            map.put("msg","商品信息更新成功");
            for(GoodsSpecification specification : specifications){
                goodsSpecificationMapper.update(specification);
            }
        }else {
            map.put("code",0);
            map.put("msg","商品信息更新失败");
        }

        return map;
    }

    @Override
    public int getGoodsNum() {
        return goodsMapper.getGoodsNum();
    }
}