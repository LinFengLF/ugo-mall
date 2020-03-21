package com.edu.mju.ugomall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * 商品规格表(GoodsSpecification)实体类
 *
 * @author makejava
 * @since 2020-03-06 22:04:02
 */
public class GoodsSpecification implements Serializable {
    private static final long serialVersionUID = 359968234384159866L;
    
    private Integer id;
    /**
    * 商品表的商品ID
    */
    private Integer goodsId;
    /**
    * 商品规格名称
    */
    private String specification;
    /**
    * 商品规格值
    */
    private String value;
    /**
    * 商品规格图片
    */
    private String picUrl;
    /**
    * 创建时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date addTime;
    /**
    * 更新时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;
    /**
    * 逻辑删除
    */
    private Integer deleted;


    private String price;

    private String num;


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

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
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


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public GoodsSpecification() {
    }

    @Override
    public String toString() {
        return "GoodsSpecification{" +
                "id=" + id +
                ", goodsId=" + goodsId +
                ", specification='" + specification + '\'' +
                ", value='" + value + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                ", deleted=" + deleted +
                ", price='" + price + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}