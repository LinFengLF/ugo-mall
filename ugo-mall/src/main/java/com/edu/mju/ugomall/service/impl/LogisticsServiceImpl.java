package com.edu.mju.ugomall.service.impl;

import com.edu.mju.ugomall.entity.Logistics;
import com.edu.mju.ugomall.mapper.LogisticsMapper;
import com.edu.mju.ugomall.service.LogisticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (Logistics)表服务实现类
 *
 * @author makejava
 * @since 2020-03-16 09:40:01
 */
@Service("logisticsService")
public class LogisticsServiceImpl implements LogisticsService {
    @Resource
    private LogisticsMapper logisticsMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Logistics queryById(Integer id) {
        return this.logisticsMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Logistics> queryAllByLimit(int offset, int limit) {
        return this.logisticsMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param logistics 实例对象
     * @return 实例对象
     */
    @Override
    public Logistics insert(Logistics logistics) {
        this.logisticsMapper.insert(logistics);
        return logistics;
    }

    /**
     * 修改数据
     *
     * @param logistics 实例对象
     * @return 实例对象
     */
    @Override
    public Logistics update(Logistics logistics) {
        this.logisticsMapper.update(logistics);
        return this.queryById(logistics.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.logisticsMapper.deleteById(id) > 0;
    }


    @Override
    public Map<String, Object> getList() {
        Map<String,Object> map = new HashMap<>();
        List<Logistics> list = logisticsMapper.getList();
        if (list.size() > 0){
            map.put("data",list);
            map.put("code",1);
            map.put("msg","物流公司查询成功");
        }else {
            map.put("code",0);
            map.put("msg","物流公司查询失败");
        }
        return map;
    }
}