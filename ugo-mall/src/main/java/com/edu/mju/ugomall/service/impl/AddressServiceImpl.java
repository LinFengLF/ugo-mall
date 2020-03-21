package com.edu.mju.ugomall.service.impl;

import com.edu.mju.ugomall.entity.Address;
import com.edu.mju.ugomall.entity.AddressAllinone;
import com.edu.mju.ugomall.entity.AddressDetail;
import com.edu.mju.ugomall.mapper.AddressMapper;
import com.edu.mju.ugomall.service.AddressService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收货地址表(Address)表服务实现类
 *
 * @author makejava
 * @since 2020-03-01 16:51:37
 */
@Service("addressService")
public class AddressServiceImpl implements AddressService {
    @Resource
    private AddressMapper addressMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Address queryById(String id) {
        return this.addressMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Address> queryAllByLimit(int offset, int limit) {
        return this.addressMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param addressAllinone 实例对象
     * @return 实例对象
     */
    @Override
    public Map<String, Object> insert(AddressAllinone addressAllinone) {
        Address address = addressAllinone.getAddress();
        AddressDetail addressDetail = addressAllinone.getAddressDetail();

        address.setLabel(addressDetail.getLabel());
        address.setAreaCode(addressDetail.getCityCode());
        address.setCityValue(addressDetail.getValue());
        address.setAddTime(LocalDateTime.now());

        //判断是否存在默认地址,假设不存在默认地址
        boolean flag = false;
        Map<String, Object> map = new HashMap<>();
        List<Address> list = addressMapper.getListByUserId(address.getUserId());
        if (address.getIsDefault() == 1) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getIsDefault() == 1) {
                    flag = true;
                    map.put("msg", "已存在默认地址");
                    map.put("code", 1);
                    break;
                }
            }
            if (!flag) {
                addressMapper.insert(address);
            }
        } else {
            int count = addressMapper.insert(address);
            if (count > 0) {
                map.put("code", 1);
                map.put("msg", "收货地址添加成功");
            } else {
                map.put("code", 0);
                map.put("msg", "收货地址添加失败");
            }
        }
        return map;
    }

    /**
     * 修改数据
     *
     * @param addressAllinone 实例对象
     * @return 实例对象
     */
    @Override
    public Map<String, Object> update(AddressAllinone addressAllinone) {
        //判断是否存在默认地址,假设不存在默认地址
        boolean flag = false;
        Map<String, Object> map = new HashMap<>();
        Address address = addressAllinone.getAddress();
        Address defaultAddress = addressMapper.getByIsDefault();
        AddressDetail addressDetail = addressAllinone.getAddressDetail();

        address.setLabel(addressDetail.getLabel());
        address.setAreaCode(addressDetail.getCityCode());
        address.setCityValue(addressDetail.getValue());
        address.setUpdateTime(LocalDateTime.now());

        List<Address> list = addressMapper.getListByUserId(address.getUserId());
        //如果存在有默认地址
        if (defaultAddress != null) {
            //需要编辑的就是默认地址
            if (address.getIsDefault() == 1 && address.getId().equals(defaultAddress.getId())) {
                int count = addressMapper.update(address);
                if (count > 0) {
                    map.put("code", 1);
                    map.put("msg", "收货地址信息更新成功");
                } else {
                    map.put("code", 0);
                    map.put("msg", "收货地址信息更新失败");
                }
            }
            //需要编辑的不是默认地址，flag标记
            if (address.getIsDefault() != 1) {
                int num = addressMapper.update(address);
                if (num > 0) {
                    map.put("code", 1);
                    map.put("msg", "更新成功");
                } else {
                    map.put("code", 0);
                    map.put("msg", "更新失败");
                }
            }
        }else {
            int num = addressMapper.update(address);
            if (num > 0) {
                map.put("code", 1);
                map.put("msg", "更新成功");
            } else {
                map.put("code", 0);
                map.put("msg", "更新失败");
            }
        }

        return map;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.addressMapper.deleteById(id) > 0;
    }

    /**
     * 查询全部
     *
     * @param page
     * @param pageSize
     * @param name
     * @param userId
     * @return
     */
    @Override
    public PageInfo<Address> getList(Integer page, Integer pageSize, String name, String userId) {
        PageInfo<Address> pageInfo = null;
        PageHelper.startPage(page, pageSize);
        List<Address> list = addressMapper.getList(userId, name);
        try {
            pageInfo = new PageInfo<>(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageInfo;
    }

    @Override
    public Map<String, Object> deleteByBatch(String[] ids) {
        Map<String, Object> resultMap = new HashMap<>();
        int count = addressMapper.deleteByBatch(ids);
        System.out.println("**********删除方法*******" + count);
        if (count >= 1) {
            resultMap.put("msg", "用户地址删除成功");
            resultMap.put("code", "1");
        } else {
            resultMap.put("msg", "用户地址删除失败");
            resultMap.put("code", "0");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getListByUserId(String userId) {
        Map<String, Object> map = new HashMap<>();
        List<Address> list = new ArrayList<>();
        list = addressMapper.getListByUserId(userId);
        if (list.size() > 0) {
            map.put("msg", "用户地址信息获取成功");
            map.put("code", 1);
            map.put("data", list);
        } else {
            map.put("data", null);
            map.put("msg", "用户地址信息获取失败");
            map.put("code", 0);
        }
        return map;
    }
}