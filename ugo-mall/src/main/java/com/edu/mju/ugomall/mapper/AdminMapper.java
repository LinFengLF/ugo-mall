package com.edu.mju.ugomall.mapper;

import com.edu.mju.ugomall.annotation.PassToken;
import com.edu.mju.ugomall.entity.Admin;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 管理员表(Admin)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-01 20:50:16
 */
public interface AdminMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Admin queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Admin> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param admin 实例对象
     * @return 对象列表
     */
    List<Admin> queryAll(Admin admin);

    /**
     * 新增数据
     *
     * @param admin 实例对象
     * @return 影响行数
     */
    int insert(Admin admin);

    /**
     * 修改数据
     *
     * @param admin 实例对象
     * @return 影响行数
     */
    int update(Admin admin);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);



    /**
     * 查询全部数据
     * @param userName
     * @return
     */
    List<Admin> getList(@Param("userName") String userName);

    /**
     * 批量删除+单个删除
     * @param ids
     * @return
     */
    int deleteByBatch(@Param("ids")String[] ids);

    /**
     * 管理员登录方法
     * @param username
     * @param password
     * @return
     */
    Admin doLogin(@Param("username") String username,@Param("password") String password);

}