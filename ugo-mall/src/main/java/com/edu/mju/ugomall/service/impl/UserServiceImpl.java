package com.edu.mju.ugomall.service.impl;

import com.edu.mju.ugomall.entity.User;
import com.edu.mju.ugomall.mapper.UserMapper;
import com.edu.mju.ugomall.service.UserService;
import com.edu.mju.ugomall.utils.RandomUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 获取用户信息
     *
     * @param userName
     * @return
     */
   /* @Override
    public Map<String, Object> getUserInfo(String userName) {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> mapList = userMapper.getUserInfo(userName);
        if (mapList.isEmpty() || mapList.size() == 0) {
            resultMap.put("code", "0");
            resultMap.put("msg", "用户信息获取失败");
        } else {
            resultMap.put("code", "1");
            resultMap.put("msg", "用户信息获取成功");
            resultMap.put("data", mapList);
        }
        return resultMap;
    }
*/

    /**
     * 批量删除+单个删除
     *
     * @param ids
     * @return
     */
    @Override
    public Map<String, Object> deleteByBatch(String[] ids) {
        Map<String, Object> resultMap = new HashMap<>();
        int count = userMapper.deleteByBatch(ids);
        System.out.println("**********删除方法*******" + count);
        if (count >= 1) {
            resultMap.put("msg", "删除成功");
            resultMap.put("code", "1");
        } else {
            resultMap.put("msg", "删除失败");
            resultMap.put("code", "0");
        }
        return resultMap;
    }

    /**
     * 用户添加
     *
     * @param user
     * @return
     */
    @Override
    public Map<String, Object> insert(User user) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int count = userMapper.insert(user);
            if (count >= 1) {
                resultMap.put("msg", "添加用户成功");
                resultMap.put("code", "1");
            } else {
                resultMap.put("msg", "添加用户失败");
                resultMap.put("code", "0");
            }
        } catch (Exception e) {
            resultMap.put("msg", "添加用户失败");
            resultMap.put("code", "0");
        }
        return resultMap;
    }

    @Override
    public PageInfo<User> getAllUser(Integer page, Integer pageSize, String userName, String userId) {
        PageInfo<User> pageInfo = null;
        PageHelper.startPage(page, pageSize);
        List<User> list = userMapper.getUserInfo(userName, userId);
        try {
            pageInfo = new PageInfo<>(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageInfo;
    }

    @Override
    public Map<String, Object> updateUserInfo(User user) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int count = userMapper.updateUserInfo(user);

            System.out.println("更新的条数为：" + count);
            if (count >= 1) {
                resultMap.put("msg", "更新用户信息成功");
                resultMap.put("code", "1");
            } else {
                resultMap.put("msg", "更新用户信息失败");
                resultMap.put("code", "0");
            }
        } catch (Exception e) {
            resultMap.put("msg", "更新用户信息失败");
            resultMap.put("code", "0");
        }
        return resultMap;
    }

    @Override
    public Map<String,User> getUserByUserName(String userName, String userPwd) {
        Map<String,User> map = new HashMap<>();
        User user = userMapper.getUserByUserName(userName, userPwd);
        if(user == null){
            map.put("obj",null);
        }else {
            map.put("obj",user);
        }
        return map;
    }

    @Override
    public User getUserInfoById(Integer userId) {
        return userMapper.getUserInfoById(userId);
    }


    @Override
    public int getUserNum() {
        return userMapper.getUserNum();
    }
}
