package com.edu.mju.ugomall.service.impl;

import com.edu.mju.ugomall.entity.GoodsSpecification;
import com.edu.mju.ugomall.entity.OrderGoods;
import com.edu.mju.ugomall.mapper.GoodsSpecificationMapper;
import com.edu.mju.ugomall.mapper.OrderGoodsMapper;
import com.edu.mju.ugomall.service.OrderGoodsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单商品表(OrderGoods)表服务实现类
 *
 * @author makejava
 * @since 2020-03-13 23:51:04
 */
@Service("orderGoodsService")
public class OrderGoodsServiceImpl implements OrderGoodsService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private OrderGoodsMapper orderGoodsMapper;
    @Autowired
    private GoodsSpecificationMapper goodsSpecificationMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public OrderGoods queryById(Integer id) {
        return this.orderGoodsMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<OrderGoods> queryAllByLimit(int offset, int limit) {
        return this.orderGoodsMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param orderGoods 实例对象
     * @return 实例对象
     */
    @Override
    public Map<String,Object> insert(OrderGoods orderGoods) {
        //获取商品id
        Integer goodsId = orderGoods.getGoodsId();
        //查找相关商品id的商品规格列表
        List<GoodsSpecification> list = goodsSpecificationMapper.getByGoodsId(String.valueOf(goodsId));
        //如果查找到相关商品规格
        if (list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                //将商品规格名称进行拼接
                String spec = list.get(i).getSpecification() + ":" + list.get(i).getValue();
                //相同
                if (orderGoods.getSpecifications().equals(spec)){
                    //将商品规格中的库存减去订单商品的数据
                    int nums = Integer.parseInt(list.get(i).getNum()) - orderGoods.getNum();
                    list.get(i).setNum(String.valueOf(nums));
                    goodsSpecificationMapper.update(list.get(i));
                }
            }
        }
        orderGoods.setAddTime(LocalDateTime.now());
        Map<String,Object> map = new HashMap<>();
        int count = this.orderGoodsMapper.insert(orderGoods);
        if (count > 0){
            map.put("code",1);
            map.put("msg","订单商品添加成功");
        }else {
            map.put("code",0);
            map.put("msg","订单商品添加失败");
        }
        return map;
    }

    /**
     * 修改数据
     *
     * @param orderGoods 实例对象
     * @return 实例对象
     */
    @Override
    public OrderGoods update(OrderGoods orderGoods) {
        orderGoods.setUpdateTime(LocalDateTime.now());
        this.orderGoodsMapper.update(orderGoods);
        return this.queryById(orderGoods.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.orderGoodsMapper.deleteById(id) > 0;
    }

    @Override
    public PageInfo<OrderGoods> getList(Integer page, Integer pageSize) {
        PageInfo<OrderGoods> pageInfo = null;
        PageHelper.startPage(page, pageSize);
        List<OrderGoods> list = orderGoodsMapper.getList();
        try {
            pageInfo = new PageInfo<>(list);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("订单商品查询失败");
        }
        return pageInfo;
    }

    @Override
    public Map<String, Object> deleteByBatch(String[] ids) {
        Map<String, Object> resultMap = new HashMap<>();
        int count = orderGoodsMapper.deleteByBatch(ids);
        System.out.println("**********删除方法*******" + count);
        if (count >= 1) {
            resultMap.put("msg", "订单商品删除成功");
            resultMap.put("code", "1");
        } else {
            resultMap.put("msg", "订单商品删除失败");
            resultMap.put("code", "0");
        }
        return resultMap;
    }
}