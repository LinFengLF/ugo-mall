package com.edu.mju.ugomall.service;

import com.edu.mju.ugomall.entity.GoodsSpecification;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 商品规格表(GoodsSpecification)表服务接口
 *
 * @author makejava
 * @since 2020-03-06 22:04:02
 */
public interface GoodsSpecificationService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    GoodsSpecification queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<GoodsSpecification> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param goodsSpecification 实例对象
     * @return 实例对象
     */
    Map<String,Object> insert(GoodsSpecification goodsSpecification);

    /**
     * 修改数据
     *
     * @param goodsSpecification 实例对象
     * @return 实例对象
     */
    Map<String,Object> update(GoodsSpecification goodsSpecification);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    Map<String,Object> deleteById(String id);


    Map<String,Object> getList();

    Map<String,Object> getByGoodsId(String goodsId);

}