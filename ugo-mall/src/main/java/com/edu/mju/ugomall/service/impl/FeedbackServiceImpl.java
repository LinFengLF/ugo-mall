package com.edu.mju.ugomall.service.impl;

import com.edu.mju.ugomall.entity.Feedback;
import com.edu.mju.ugomall.mapper.FeedbackMapper;
import com.edu.mju.ugomall.service.FeedbackService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 意见反馈表(Feedback)表服务实现类
 *
 * @author makejava
 * @since 2020-03-20 15:43:22
 */
@Service("feedbackService")
public class FeedbackServiceImpl implements FeedbackService {
    @Resource
    private FeedbackMapper feedbackMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Feedback queryById(Integer id) {
        return this.feedbackMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Feedback> queryAllByLimit(int offset, int limit) {
        return this.feedbackMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param feedback 实例对象
     * @return 实例对象
     */
    @Override
    public Map<String,Object> insert(Feedback feedback) {
        Map<String,Object> map = new HashMap<>();
        feedback.setAddTime(LocalDateTime.now());
        int count = this.feedbackMapper.insert(feedback);
        if(count > 0) {
            map.put("code", 1);
            map.put("msg", "意见反馈成功");
        }else {
            map.put("code", 0);
            map.put("msg", "意见反馈失败");
        }
        return map;
    }

    /**
     * 修改数据
     *
     * @param feedback 实例对象
     * @return 实例对象
     */
    @Override
    public Feedback update(Feedback feedback) {
        this.feedbackMapper.update(feedback);
        return this.queryById(feedback.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.feedbackMapper.deleteById(id) > 0;
    }


    @Override
    public PageInfo<Feedback> getList(Integer page, Integer pageSize, String type, String userId) {
        PageInfo<Feedback> pageInfo = null;
        PageHelper.startPage(page, pageSize);
        List<Feedback> list = feedbackMapper.getList(type, userId);
        try {
            pageInfo = new PageInfo<>(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageInfo;
    }
}