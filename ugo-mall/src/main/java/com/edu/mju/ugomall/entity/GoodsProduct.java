package com.edu.mju.ugomall.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 商品货品表(GoodsProduct)实体类
 *
 * @author makejava
 * @since 2020-03-06 21:04:40
 */
public class GoodsProduct implements Serializable {
    private static final long serialVersionUID = -81691970039996241L;
    
    private Integer id;
    /**
    * 商品表的商品ID
    */
    private Integer goodsId;
    /**
    * 商品规格值列表，采用JSON数组格式
    */
    private String specifications;
    /**
    * 商品货品价格
    */
    private String price;
    /**
    * 商品货品数量
    */
    private Integer number;
    /**
    * 商品货品图片
    */
    private String url;
    /**
    * 创建时间
    */
    private Date addTime;
    /**
    * 更新时间
    */
    private Date updateTime;
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

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public GoodsProduct() {
    }

    @Override
    public String toString() {
        return "GoodsProduct{" +
                "id=" + id +
                ", goodsId=" + goodsId +
                ", specifications='" + specifications + '\'' +
                ", price='" + price + '\'' +
                ", number=" + number +
                ", url='" + url + '\'' +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                ", deleted=" + deleted +
                '}';
    }
}