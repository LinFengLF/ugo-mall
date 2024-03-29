package com.edu.mju.ugomall.mapper;

import com.edu.mju.ugomall.entity.Address;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 收货地址表(Address)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-01 16:51:37
 */
public interface AddressMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Address queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Address> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param address 实例对象
     * @return 对象列表
     */
    List<Address> queryAll(Address address);

    /**
     * 新增数据
     *
     * @param address 实例对象
     * @return 影响行数
     */
    int insert(Address address);

    /**
     * 修改数据
     *
     * @param address 实例对象
     * @return 影响行数
     */
    int update(Address address);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);


    /**
     * 查询全部数据
     * @param userId
     * @param name
     * @return
     */
    List<Address> getList(@Param("userId") String userId,@Param("name") String name);

    /**
     * 批量删除+单个删除
     * @param ids
     * @return
     */
    int deleteByBatch(@Param("ids")String[] ids);

    List<Address> getListByUserId(@Param("userId") String userId);

    Address getByIsDefault();


}