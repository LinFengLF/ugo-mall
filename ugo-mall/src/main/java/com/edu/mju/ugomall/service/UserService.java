package com.edu.mju.ugomall.service;

import com.edu.mju.ugomall.entity.User;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface UserService {
    /**
     * 用户信息获取
     * @param userName
     * @return
     */
//    Map<String, Object> getUserInfo(@Param("userName") String userName);

    /**
     * 批量删除+单个删除
     * @param ids
     * @return
     */
    Map<String, Object> deleteByBatch(@Param("ids")String[] ids);

    /**
     * 用户添加方法
     * @param user
     * @return
     */
    Map<String, Object> insert(User user);

    /**
     * 带分页的用户查询方法
     * @param page
     * @param pageSize
     * @param userName
     * @param userId
     * @return
     */
    PageInfo<User> getAllUser(Integer page,Integer pageSize,String userName,String userId);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    Map<String,Object> updateUserInfo(User user);

    /**
     * 用户登录
     * @param userName
     * @param userPwd
     * @return
     */
    Map<String,User> getUserByUserName(String userName,String userPwd);

    /**
     * 通过ID查询用户信息
     * @param userId
     * @return
     */
    User getUserInfoById( Integer userId);

    int getUserNum();


}
