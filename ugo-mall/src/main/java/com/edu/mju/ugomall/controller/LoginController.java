package com.edu.mju.ugomall.controller;

import com.edu.mju.ugomall.annotation.PassToken;
import com.edu.mju.ugomall.entity.Admin;
import com.edu.mju.ugomall.entity.User;
import com.edu.mju.ugomall.service.AdminService;
import com.edu.mju.ugomall.service.UserService;
import com.edu.mju.ugomall.service.impl.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录管理
 *
 * @author 67072
 */
@RestController
@RequestMapping(value = "/ugoMall/login")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AdminService adminService;


    @PassToken
    @ResponseBody
    @RequestMapping(value = "/doLogin")
    public Map<String, Object> login(String userName, String password, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Admin admin = adminService.doLogin(userName, password);

        if (admin == null) {
            map.put("msg", "登录失败，用户名或密码错误");
            map.put("code", "300");
        } else {
            String token = tokenService.getAdminToken(admin);
            map.put("msg", "登录成功");
            map.put("token", token);
            map.put("code", "200");
            map.put("adminInfo", admin);
        }

        return map;
    }



/*
    @PassToken
    @ResponseBody
    @RequestMapping(value = "/doLogin")
    public Map<String, Object> userLogin(Admin admin, @RequestParam String userName, @RequestParam String userPwd) {
        Map<String, Object> map = new HashMap<>();
        logger.info("用户名：" + userName + "，用户密码：" + userPwd);

        Map<String, User> userMap = userService.getUserByUserName(userName, userPwd);
        User userForBase = userMap.get("obj");
        System.out.println("***********" + userForBase.toString());
        if (userForBase == null) {
            map.put("msg", "用户名或密码错误");
            map.put("token", "error");
            map.put("isLogin","0");
            map.put("userInfo",null);
        } else {
            String token = tokenService.getToken(userForBase);
            map.put("msg", "登录成功");
            map.put("token", token);
            map.put("isLogin","1");
            map.put("userInfo",userForBase);
        }
        return map;
    }*/
}
