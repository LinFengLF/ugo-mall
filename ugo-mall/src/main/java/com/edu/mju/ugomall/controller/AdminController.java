package com.edu.mju.ugomall.controller;

import com.edu.mju.ugomall.entity.Admin;
import com.edu.mju.ugomall.entity.User;
import com.edu.mju.ugomall.service.AdminService;
import com.edu.mju.ugomall.service.impl.TokenService;
import com.edu.mju.ugomall.service.impl.UserServiceImpl;
import com.edu.mju.ugomall.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 管理员表(Admin)表控制层
 *
 * @author makejava
 * @since 2020-03-01 20:50:16
 */
@RestController
@RequestMapping("/ugoMall/adminController")
public class AdminController {
    /**
     * 服务对象
     */
    @Autowired
    private AdminService adminService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private StringUtils stringUtils = new StringUtils();

    @ApiOperation(value = "查询管理员信息", notes = "查询数据库中管理员信息")
    @ResponseBody
    @RequestMapping(value = "/getList")
    public PageInfo<Admin> getList(@RequestParam(required = false) String userName,
                                      @RequestParam Integer page, @RequestParam Integer pageSize) {
        logger.info("获取管理员所有数据");
        userName = stringUtils.isEmpty(userName) ?  "" : userName;
        PageInfo<Admin> pageInfo = adminService.getList(page, pageSize, userName);
        return pageInfo;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteByBatch")
    public Map<String, Object> deleteByBatch(@RequestParam String ids) {
        logger.info("删除方法");
        String[] arr = ids.split(",");
        return adminService.deleteByBatch(arr);
    }

    @ResponseBody
    @RequestMapping(value = "/insert")
    public Map<String, Object> insert(@RequestBody Admin admin) {
        logger.info("添加方法");
        return adminService.insert(admin);
    }

    @ResponseBody
    @RequestMapping(value = "/update")
    public Map<String, Object> updateUserInfo(@RequestBody Admin admin) {
        logger.info("更新方法");
        return adminService.update(admin);
    }

}