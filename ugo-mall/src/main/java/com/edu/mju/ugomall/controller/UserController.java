package com.edu.mju.ugomall.controller;

import com.edu.mju.ugomall.entity.User;
import com.edu.mju.ugomall.service.impl.TokenService;
import com.edu.mju.ugomall.service.impl.UserServiceImpl;
import com.edu.mju.ugomall.utils.JsonResult;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ugoMall/userController")
@Api("用户信息相关的api")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private TokenService tokenService;


    @ApiOperation(value = "查询用户信息", notes = "查询数据库中用户信息")
    @ResponseBody
    @RequestMapping(value = "/getUserInfo")
    public PageInfo<User> getUserInfo(@RequestParam(required = false) String userName, @RequestParam(required = false) String userId,
                                      @RequestParam Integer page, @RequestParam Integer pageSize) {
        logger.info("获取用户所有数据");
        if (userName == null || ("").equals(userName)) {
            userName = "";
        }
        PageInfo<User> pageInfo = userService.getAllUser(page, pageSize, userName, userId);
        return pageInfo;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteByBatch")
    public Map<String, Object> deleteByBatch(@RequestParam String ids) {
        logger.info("删除方法");
        String[] arr = ids.split(",");
        return userService.deleteByBatch(arr);
    }

    @ResponseBody
    @RequestMapping(value = "/insert")
    public Map<String, Object> insert(@RequestBody User user) {
        logger.info("添加方法");
        return userService.insert(user);
    }

    @ResponseBody
    @RequestMapping(value = "/updateUserInfo")
    public Map<String, Object> updateUserInfo(@RequestBody User user) {
        logger.info("更新方法");
        return userService.updateUserInfo(user);
    }


}
