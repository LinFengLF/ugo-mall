package com.edu.mju.ugomall.service;

import com.edu.mju.ugomall.entity.Admin;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 管理员表(Admin)表服务接口
 *
 * @author makejava
 * @since 2020-03-01 20:50:16
 */
public interface AdminService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Admin queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Admin> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param admin 实例对象
     * @return 实例对象
     */
    Map<String, Object> insert(Admin admin);

    /**
     * 修改数据
     *
     * @param admin 实例对象
     * @return 实例对象
     */
    Map<String, Object> update(Admin admin);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);


    PageInfo<Admin> getList(Integer page, Integer pageSize, String userName);


    Map<String, Object> deleteByBatch(String[] ids);


    Admin doLogin(String username,String password);

}