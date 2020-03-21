package com.edu.mju.ugomall.service.impl;

import com.edu.mju.ugomall.entity.Collect;
import com.edu.mju.ugomall.entity.Goods;
import com.edu.mju.ugomall.entity.User;
import com.edu.mju.ugomall.mapper.CollectMapper;
import com.edu.mju.ugomall.mapper.GoodsMapper;
import com.edu.mju.ugomall.service.CollectService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收藏表(Collect)表服务实现类
 *
 * @author makejava
 * @since 2020-02-29 13:09:41
 */
@Service
public class CollectServiceImpl implements CollectService {
    @Resource
    private CollectMapper collectMapper;
    @Resource
    private GoodsMapper goodsMapper;
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Collect queryById(Integer id) {
        return this.collectMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Collect> queryAllByLimit(int offset, int limit) {
        return this.collectMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param collect 实例对象
     * @return 实例对象
     */
    @Override
    public Map<String,Object> insert(Collect collect) {
        collect.setAddTime(LocalDateTime.now());
        Map<String,Object> resultMap = new HashMap<>();
        int count = this.collectMapper.insert(collect);
        if(count > 0){
            resultMap.put("msg","已收藏");
            resultMap.put("code",1);
        }else {
            resultMap.put("msg","收藏失败");
            resultMap.put("code",0);
        }
        return resultMap;
    }

    /**
     * 修改数据
     *
     * @param collect 实例对象
     * @return 实例对象
     */
    @Override
    public Map<String,Object> update(Collect collect) {
        collect.setUpdateTime(LocalDateTime.now());
        Map<String,Object> resultMap = new HashMap<>();
        int count = this.collectMapper.update(collect);
        if(count > 0){
            resultMap.put("msg","更新收藏信息成功");
            resultMap.put("code",1);
        }else {
            resultMap.put("msg","更新收藏信息失败");
            resultMap.put("code",0);
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
        return this.collectMapper.deleteById(id) > 0;
    }


    /**
     * 带分页查询所有数据
     * @param page
     * @param pageSize
     * @param userId
     * @param goodsId
     * @return
     */
    @Override
    public PageInfo<Collect> getAllCollection(Integer page, Integer pageSize, String userId, String goodsId) {
        PageInfo<Collect> pageInfo = null;
        PageHelper.startPage(page, pageSize);
        List<Collect> list = collectMapper.getCollectInfo(goodsId, userId);
        try {
            pageInfo = new PageInfo<>(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageInfo;
    }

    @Override
    public Map<String, Object> getUserCollection(String userId) {
        Map<String,Object> map = new HashMap<>();
        List<Collect> list = collectMapper.getUserCollection(userId);
        List<Goods> goodsList = new ArrayList<>();
        if (list.size() > 0){
            for (int i = 0;i < list.size(); i++) {
                String goodsId = Integer.toString(list.get(i).getValueId());
                Goods goods = goodsMapper.queryById(goodsId);
                if (goods != null){
                    goodsList.add(goods);
                }
            }
            map.put("data",goodsList);
            map.put("code",1);
            map.put("msg","用户收藏商品信息获取成功");
        }else {
            map.put("data",null);
            map.put("code",0);
            map.put("msg","用户收藏商品信息获取失败");
        }
        return map;
    }


    @Override
    public Map<String, Object> deleteByValueId(String valueId) {
        Map<String,Object> map = new HashMap<>();
        int count = collectMapper.deleteByValueId(valueId);
        if(count > 0){
            map.put("code",1);
            map.put("msg","用户的收藏信息删除成功");
        }else {
            map.put("code",0);
            map.put("msg","用户的收藏信息删除失败");
        }
        return map;
    }

    @Override
    public Map<String, Object> isExist(String userId, String valueId) {
        Map<String, Object> map = new HashMap<>();
        int count = collectMapper.isExist(userId, valueId);
        System.out.println("数据库中被收藏了几次**************" + count);
        if (count > 0){
            map.put("code",1);
            map.put("msg","该商品已收藏");
        }else {
            map.put("code",0);
            map.put("msg","该商品未被收藏");
        }
        return map;
    }

    @Override
    public Map<String, Object> cancelCollect(String userId, String valueId) {
        Map<String, Object> map = new HashMap<>();
        int count = collectMapper.cancelCollect(userId, valueId);
        if (count > 0 ){
            map.put("code",1);
            map.put("msg","已取消收藏");
        }else {
            map.put("code",0);
            map.put("msg","服务器错误");
        }
        return map;
    }
}