package com.edu.mju.ugomall.mapper;

import com.edu.mju.ugomall.entity.Collect;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 收藏表(Collect)表数据库访问层
 *
 * @author makejava
 * @since 2020-02-29 13:09:41
 */

@Repository
public interface CollectMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Collect queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Collect> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param collect 实例对象
     * @return 对象列表
     */
    List<Collect> queryAll(Collect collect);

    /**
     * 新增数据
     *
     * @param collect 实例对象
     * @return 影响行数
     */
    int insert(Collect collect);

    /**
     * 修改数据
     *
     * @param collect 实例对象
     * @return 影响行数
     */
    int update(Collect collect);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);


    /**
     * 查询全部
     * @param userId
     * @return
     */
    List<Collect> getCollectInfo(@Param("userId") String userId,@Param("goodsId") String goodsId);


    List<Collect> getUserCollection(@Param("userId") String userId);


    int deleteByValueId(@Param("valueId") String valueId);

    int isExist(@Param("userId") String userId,@Param("valueId") String valueId);

    int cancelCollect(@Param("userId") String userId,@Param("valueId") String valueId);

}