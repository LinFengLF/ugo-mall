package com.edu.mju.ugomall.service.impl;

import com.edu.mju.ugomall.entity.GoodsCart;
import com.edu.mju.ugomall.mapper.GoodsCartMapper;
import com.edu.mju.ugomall.service.GoodsCartService;
import com.edu.mju.ugomall.utils.GetFieldValueByName;
import com.github.dadiyang.equator.Equator;
import com.github.dadiyang.equator.FieldInfo;
import com.github.dadiyang.equator.GetterBaseEquator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 购物车商品表(GoodsCart)表服务实现类
 *
 * @author makejava
 * @since 2020-03-09 09:59:13
 */
@Service("goodsCartService")
public class GoodsCartServiceImpl implements GoodsCartService {
    @Resource
    private GoodsCartMapper goodsCartMapper;
    Equator equator = new GetterBaseEquator();
    GetFieldValueByName util = new GetFieldValueByName();


    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Equator equator = new GetterBaseEquator();
        GetFieldValueByName util = new GetFieldValueByName();
        GoodsCart goodsCart = new GoodsCart();
        GoodsCart goodsCart1 = new GoodsCart();
        goodsCart.setId(1);
        goodsCart.setGoodsName("121231564564");
        goodsCart1.setId(2);
        goodsCart1.setGoodsName("121231564564");
        List<FieldInfo> diff = equator.getDiffFields(goodsCart, goodsCart1);
        System.out.println(diff);
        Object returnValue = util.getFieldValueByName(diff.get(0).getFieldName(),goodsCart);
        util.setFieldValueByFieldName(diff.get(0).getFieldName(),goodsCart, diff.get(0).getSecondVal());
        System.out.println(returnValue);
        System.out.println(goodsCart.toString());
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public GoodsCart queryById(Integer id) {
        return this.goodsCartMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<GoodsCart> queryAllByLimit(int offset, int limit) {
        return this.goodsCartMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param goodsCart 实例对象
     * @return 实例对象
     */
    @Override
    public Map<String, Object> insert(GoodsCart goodsCart) {
        Map<String, Object> map = new HashMap<>();

        GoodsCart cartMapperExist = goodsCartMapper.isExist(goodsCart.getUserId(), goodsCart.getGoodsId(), goodsCart.getGoodsSn(),
                goodsCart.getGoodsName(), goodsCart.getPrice(),goodsCart.getSpecifications());
        if (cartMapperExist != null) {
            List<FieldInfo> diff = equator.getDiffFields(goodsCart, cartMapperExist);
            if (diff.size() > 0 ){
                int num = cartMapperExist.getNum() + goodsCart.getNum();
                for (int i = 0; i < diff.size(); i++) {
                    System.out.println(diff.get(i).getFieldName());
//                    if (diff.get(i).getFieldName().equals("num")){
//                         num = cartMapperExist.getNum() + goodsCart.getNum();
//                    }
                }
                cartMapperExist.setNum(num);
                cartMapperExist.setUpdateTime(LocalDateTime.now());
                int flag = goodsCartMapper.update(cartMapperExist);
                if (flag > 0){
                    map.put("code",1);
                    map.put("msg","购物车商品更新成功");
                }else {
                    map.put("code",0);
                    map.put("msg","购物车商品更新失败");
                }
            }
        }else {
            goodsCart.setAddTime(LocalDateTime.now());
            int count = this.goodsCartMapper.insert(goodsCart);
            if (count > 0) {
                map.put("msg", "加入购物车");
                map.put("code", 1);
            } else {
                map.put("msg", "加入购物车失败");
                map.put("code", 0);
            }
        }

        return map;
    }

    /**
     * 修改数据
     *
     * @param goodsCart 实例对象
     * @return 实例对象
     */
    @Override
    public Map<String, Object> update(GoodsCart goodsCart) {
        Map<String, Object> map = new HashMap<>();
        int count = this.goodsCartMapper.update(goodsCart);
        if (count > 0) {
            map.put("msg", "购物车信息修改成功");
            map.put("code", 1);
        } else {
            map.put("msg", "服务器错误");
            map.put("code", 0);
        }
        return map;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public Map<String, Object> deleteById(String id) {
        Map<String, Object> map = new HashMap<>();
        int count = this.goodsCartMapper.deleteById(id);
        if (count > 0) {
            map.put("msg", "删除成功");
            map.put("code", 1);
        } else {
            map.put("msg", "删除失败");
            map.put("code", 0);
        }
        return map;
    }

    @Override
    public Map<String, Object> getList() {
        Map<String, Object> map = new HashMap<>();
        List<GoodsCart> list = goodsCartMapper.getList();
        if (list.size() > 0) {
            map.put("data", list);
            map.put("msg", "购物车列表信息获取成功");
            map.put("code", 1);
        } else {
            map.put("data", null);
            map.put("msg", "购物车列表信息获取失败");
            map.put("code", 0);
        }
        return map;
    }


    @Override
    public Map<String, Object> getListByUserId(String userId) {
        Map<String, Object> map = new HashMap<>();
        List<GoodsCart> cartList = goodsCartMapper.getListByUserId(userId);
        if (cartList.size() > 0) {
            map.put("data", cartList);
            map.put("msg", "用户购物车列表信息获取成功");
            map.put("code", 1);
        } else {
            map.put("data", null);
            map.put("msg", "用户购物车列表信息获取失败");
            map.put("code", 0);
        }
        return map;
    }

    @Override
    public Map<String, Object> deleteByBatch(String[] ids) {
        Map<String, Object> resultMap = new HashMap<>();
        int count = goodsCartMapper.deleteByBatch(ids);
        System.out.println("**********购物车商品删除方法*******" + count);
        if (count >= 1) {
            resultMap.put("msg", "购物车商品删除成功");
            resultMap.put("code", "1");
        } else {
            resultMap.put("msg", "购物车商品删除失败");
            resultMap.put("code", "0");
        }
        return resultMap;
    }
}