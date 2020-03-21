package com.edu.mju.ugomall.mapper;

import com.edu.mju.ugomall.entity.GoodsSpecification;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 商品规格表(GoodsSpecification)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-06 22:04:02
 */
@Repository
public interface GoodsSpecificationMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    GoodsSpecification queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<GoodsSpecification> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param goodsSpecification 实例对象
     * @return 对象列表
     */
    List<GoodsSpecification> queryAll(GoodsSpecification goodsSpecification);

    /**
     * 新增数据
     *
     * @param goodsSpecification 实例对象
     * @return 影响行数
     */
    int insert(GoodsSpecification goodsSpecification);

    /**
     * 修改数据
     *
     * @param goodsSpecification 实例对象
     * @return 影响行数
     */
    int update(GoodsSpecification goodsSpecification);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

    List<GoodsSpecification> getList();

    List<GoodsSpecification> getByGoodsId(@Param("goodsId") String goodsId);

    int deleteByGoodsId(@Param("goodsId") String goodsId);

    int updateNum(GoodsSpecification goodsSpecification);

    GoodsSpecification getByNameAndSpecification(@Param("goodsId") String goodsId,
                                                 @Param("specification") String specification, @Param("value") String value);

}