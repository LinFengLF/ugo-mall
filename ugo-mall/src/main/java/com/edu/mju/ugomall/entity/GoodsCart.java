package com.edu.mju.ugomall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.io.Serializable;

/**
 * 购物车商品表(GoodsCart)实体类
 *
 * @author makejava
 * @since 2020-03-09 09:59:13
 */
public class GoodsCart implements Serializable {
    private static final long serialVersionUID = -41347365711635111L;
    
    private Integer id;
    /**
    * 用户表的用户ID
    */
    private Integer userId;
    /**
    * 商品表的商品ID
    */
    private Integer goodsId;
    /**
    * 商品编号
    */
    private String goodsSn;
    /**
    * 商品名称
    */
    private String goodsName;
    /**
    * 商品货品表的货品ID
    */
    private Integer productId;
    /**
    * 商品货品的价格
    */
    private String price;
    /**
    * 商品货品的数量
    */
    private Integer num;
    /**
    * 商品规格值列表，采用JSON数组格式
    */
    private String specifications;
    /**
    * 购物车中商品是否选择状态
    */
    private Integer checked;
    /**
    * 商品图片或者商品货品图片
    */
    private String picUrl;
    /**
    * 创建时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime addTime;
    /**
    * 更新时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime updateTime;
    /**
    * 逻辑删除
    */
    private Integer deleted;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public GoodsCart() {
    }

    @Override
    public String toString() {
        return "GoodsCart{" +
                "id=" + id +
                ", userId=" + userId +
                ", goodsId=" + goodsId +
                ", goodsSn='" + goodsSn + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", productId=" + productId +
                ", price='" + price + '\'' +
                ", num=" + num +
                ", specifications='" + specifications + '\'' +
                ", checked=" + checked +
                ", picUrl='" + picUrl + '\'' +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                ", deleted=" + deleted +
                '}';
    }
}