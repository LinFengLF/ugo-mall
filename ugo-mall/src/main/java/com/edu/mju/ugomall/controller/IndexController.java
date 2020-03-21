package com.edu.mju.ugomall.controller;


import com.edu.mju.ugomall.service.CommentService;
import com.edu.mju.ugomall.service.GoodsService;
import com.edu.mju.ugomall.service.OrderService;
import com.edu.mju.ugomall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**首页数据初始化
 * @author 67072
 */
@RestController
@RequestMapping("/ugoMall/indexController")
public class IndexController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CommentService commentService;


    @ResponseBody
    @RequestMapping(value = "/indexData")
    public Map<String,Object> indexData(){
        Map<String,Object> map = new HashMap<>();
        int userNum = userService.getUserNum();
        int goodsNum = goodsService.getGoodsNum();
        int orderNum = orderService.getOrderNum();
        int commentNum = commentService.getCommentNum();
        map.put("userNum",userNum);
        map.put("goodsNum",goodsNum);
        map.put("orderNum",orderNum);
        map.put("commentNum",commentNum);
        return map;
    }
}
