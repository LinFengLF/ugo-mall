package com.edu.mju.ugomall.mapper;

import com.edu.mju.ugomall.entity.GoodsCart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 购物车商品表(GoodsCart)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-09 09:59:13
 */
public interface GoodsCartMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    GoodsCart queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<GoodsCart> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param goodsCart 实例对象
     * @return 对象列表
     */
    List<GoodsCart> queryAll(GoodsCart goodsCart);

    /**
     * 新增数据
     *
     * @param goodsCart 实例对象
     * @return 影响行数
     */
    int insert(GoodsCart goodsCart);

    /**
     * 修改数据
     *
     * @param goodsCart 实例对象
     * @return 影响行数
     */
    int update(GoodsCart goodsCart);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

    /**
     * 查询购物车列表
     *
     * @return
     */
    List<GoodsCart> getList();

    List<GoodsCart> getListByUserId(@Param("userId") String userId);


    /**
     * 批量删除+单个删除
     *
     * @param ids
     * @return
     */
    int deleteByBatch(@Param("ids") String[] ids);

    GoodsCart isExist(@Param("userId") Integer userId, @Param("goodsId") Integer goodsId,
                @Param("goodsSn") String goodsSn, @Param("goodsName") String goodsName,
                            @Param("price") String price,@Param("specifications") String specifications);

}