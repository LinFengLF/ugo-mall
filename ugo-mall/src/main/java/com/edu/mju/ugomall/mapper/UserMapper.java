package com.edu.mju.ugomall.mapper;

import com.edu.mju.ugomall.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper {

    /**
     * 查询全部用户
     * @param userName
     * @return
     */
    List<User> getUserInfo(@Param("userName") String userName,@Param("userId") String userId);

    /**
     * 批量删除+单个删除
     * @param ids
     * @return
     */
    int deleteByBatch(@Param("ids")String[] ids);

    /**
     * 用户添加方法
     * @param user
     * @return
     */
    int insert(User user);

    User getUserInfoById(@Param("userId") Integer userId);

    int updateUserInfo(User user);

    User getUserByUserName(@Param("userName")String userName,@Param("userPwd")String userPwd);

    int getUserNum();

}
