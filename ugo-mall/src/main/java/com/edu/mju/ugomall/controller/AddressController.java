package com.edu.mju.ugomall.controller;

import com.edu.mju.ugomall.entity.Address;
import com.edu.mju.ugomall.entity.AddressAllinone;
import com.edu.mju.ugomall.service.AddressService;
import com.edu.mju.ugomall.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 收货地址表(Address)表控制层
 *
 * @author makejava
 * @since 2020-03-01 16:51:37
 */
@RestController
@RequestMapping("/ugoMall/addressController")
public class AddressController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private StringUtils stringUtils = new StringUtils();
    /**
     * 服务对象
     */
    @Resource
    private AddressService addressService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Address selectOne(String id) {
        return this.addressService.queryById(id);
    }


    @ApiOperation(value = "查询用户收货地址信息")
    @ResponseBody
    @RequestMapping(value = "/getList")
    public PageInfo<Address> getList(@RequestParam(required = false) String name, @RequestParam(required = false) String userId,
                                     @RequestParam Integer page, @RequestParam Integer pageSize) {
        name = stringUtils.isEmpty(name) ? "" : name;
        userId = stringUtils.isEmpty(userId) ? "" : userId;
        PageInfo<Address> pageInfo = addressService.getList(page, pageSize, name, userId);
        return pageInfo;
    }

    @ResponseBody
    @RequestMapping("/getListByUserId")
    public Map<String, Object> getListByUserId(@RequestParam String userId) {
        return addressService.getListByUserId(userId);
    }

    @RequestMapping(value = "/insert")
    @ResponseBody
    public Map<String,Object> insert(@RequestBody AddressAllinone addressAllinone){
        return addressService.insert(addressAllinone);
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public Map<String,Object> update(@RequestBody AddressAllinone addressAllinone){
        return addressService.update(addressAllinone);
    }


    @RequestMapping(value = "/delete")
    @ResponseBody
    public Map<String,Object> delete(@RequestParam String[] ids){
        logger.info("删除方法");
//        String[] arr = ids.split(",");
        return addressService.deleteByBatch(ids);
    }

}