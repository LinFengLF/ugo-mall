package com.edu.mju.ugomall.service;

import com.edu.mju.ugomall.entity.Address;
import com.edu.mju.ugomall.entity.AddressAllinone;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 收货地址表(Address)表服务接口
 *
 * @author makejava
 * @since 2020-03-01 16:51:37
 */
public interface AddressService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Address queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Address> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param addressAllinone 实例对象
     * @return 实例对象
     */
    Map<String,Object> insert(AddressAllinone addressAllinone);

    /**
     * 修改数据
     *
     * @param addressAllinone 实例对象
     * @return 实例对象
     */
    Map<String,Object> update(AddressAllinone addressAllinone);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);


    PageInfo<Address> getList(Integer page, Integer pageSize, String name, String userId);


    Map<String, Object> deleteByBatch(String[] ids);


    Map<String, Object> getListByUserId( String userId);

}