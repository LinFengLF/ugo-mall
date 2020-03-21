package com.edu.mju.ugomall.service.impl;

import com.edu.mju.ugomall.entity.Admin;
import com.edu.mju.ugomall.mapper.AdminMapper;
import com.edu.mju.ugomall.service.AdminService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员表(Admin)表服务实现类
 *
 * @author makejava
 * @since 2020-03-01 20:50:16
 */
@Service("adminService")
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Admin queryById(Integer id) {
        return this.adminMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Admin> queryAllByLimit(int offset, int limit) {
        return this.adminMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param admin 实例对象
     * @return 实例对象
     */
    @Override
    public Map<String, Object> insert(Admin admin) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int count = adminMapper.insert(admin);
            if (count >= 1) {
                resultMap.put("msg", "管理员添加成功");
                resultMap.put("code", "1");
            } else {
                resultMap.put("msg", "管理员添加失败");
                resultMap.put("code", "0");
            }
        } catch (Exception e) {
            resultMap.put("msg", "管理员添加失败");
            resultMap.put("code", "0");
        }
        return resultMap;
    }

    /**
     * 修改数据
     *
     * @param admin 实例对象
     * @return 实例对象
     */
    @Override
    public Map<String, Object> update(Admin admin) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int count = adminMapper.update(admin);

            System.out.println("更新的条数为：" + count);
            if (count >= 1) {
                resultMap.put("msg", "更新管理员信息成功");
                resultMap.put("code", "1");
            } else {
                resultMap.put("msg", "更新管理员信息失败");
                resultMap.put("code", "0");
            }
        } catch (Exception e) {
            resultMap.put("msg", "更新管理员信息失败");
            resultMap.put("code", "0");
        }
        return resultMap;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.adminMapper.deleteById(id) > 0;
    }

    @Override
    public PageInfo<Admin> getList(Integer page, Integer pageSize, String userName) {
        PageInfo<Admin> pageInfo = null;
        PageHelper.startPage(page, pageSize);
        List<Admin> list = adminMapper.getList(userName);
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
        int count = adminMapper.deleteByBatch(ids);
        System.out.println("**********删除方法*******" + count);
        if (count >= 1) {
            resultMap.put("msg", "管理员删除成功");
            resultMap.put("code", "1");
        } else {
            resultMap.put("msg", "管理员删除失败");
            resultMap.put("code", "0");
        }
        return resultMap;
    }

    @Override
    public Admin doLogin(String username, String password) {
        return adminMapper.doLogin(username, password);
    }
}