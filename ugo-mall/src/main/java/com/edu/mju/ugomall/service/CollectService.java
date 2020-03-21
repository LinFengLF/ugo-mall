package com.edu.mju.ugomall.service;

import com.edu.mju.ugomall.entity.Collect;
import com.edu.mju.ugomall.entity.User;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 收藏表(Collect)表服务接口
 *
 * @author makejava
 * @since 2020-02-29 13:09:41
 */
public interface CollectService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Collect queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Collect> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param collect 实例对象
     * @return 实例对象
     */
    Map<String,Object> insert(Collect collect);

    /**
     * 修改数据
     *
     * @param collect 实例对象
     * @return 实例对象
     */
    Map<String,Object> update(Collect collect);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);



    /**
     * 带分页的查询方法
     * @param page
     * @param pageSize
     * @param goodsId
     * @param userId
     * @return
     */
    PageInfo<Collect> getAllCollection(Integer page, Integer pageSize, String userId, String goodsId);

    Map<String,Object> getUserCollection(String userId);


    Map<String,Object> deleteByValueId(String valueId);

//    Map<String,Object> collect(Collect collect);

    Map<String,Object> isExist(String userId,String valueId);

    Map<String,Object> cancelCollect(@Param("userId") String userId,@Param("valueId") String valueId);

}