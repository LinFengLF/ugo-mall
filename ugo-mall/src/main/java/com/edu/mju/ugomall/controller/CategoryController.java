package com.edu.mju.ugomall.controller;

import com.edu.mju.ugomall.entity.Ad;
import com.edu.mju.ugomall.entity.Category;
import com.edu.mju.ugomall.service.CategoryService;
import com.edu.mju.ugomall.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 类目表(Category)表控制层
 *
 * @author makejava
 * @since 2020-02-29 21:09:32
 */
@RestController
@RequestMapping("/ugoMall/categoryController")
public class CategoryController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private StringUtils stringUtils = new StringUtils();
    /**
     * 服务对象
     */
    @Resource
    private CategoryService categoryService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Category selectOne(Integer id) {
        return this.categoryService.queryById(id);
    }


    @ApiOperation(value = "查询所有")
    @ResponseBody
    @RequestMapping(value = "/getList")
    public PageInfo<Category> getList(@RequestParam(required = false) Integer page,
                                      @RequestParam(required = false) Integer pageSize) {
        if(page == null && pageSize == null){
            page = 0;
            pageSize = 0;
        }

        logger.info("查询所有类别信息");
        PageInfo<Category> pageInfo = categoryService.getList(page, pageSize);
        return pageInfo;
    }

    /**
     * 批量删除+单个删除
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteByBatch")
    public Map<String, Object> deleteByBatch(@RequestParam String ids) {
        logger.info("删除方法");
        String[] arr = ids.split(",");
        return categoryService.deleteByBatch(arr);
    }

    /**
     * 添加类别
     *
     * @param category
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insert")
    public Map<String, Object> insert(@RequestBody Category category) {
        logger.info("添加方法");
        return categoryService.insert(category);
    }

    /**
     * 通过ID查询
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryById")
    public Category queryById(@RequestParam Integer id) {
        Category category = categoryService.queryById(id);
        return category;
    }

    /**
     * 更新商品信息
     *
     * @param category
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update")
    public Map<String, Object> update(@RequestBody Category category) {
        Integer id = category.getId();
        logger.info("**********************类别的id为" + id);
        logger.info("更新方法");
        return categoryService.update(category);
    }


    @ResponseBody
    @RequestMapping(value = "/getCategoryList")
    public Map<String,Object> getCategoryList(){
        return categoryService.getCateGoryList();
    }
}