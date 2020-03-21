package com.edu.mju.ugomall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.io.Serializable;

/**
 * 商品基本信息表(Goods)实体类
 *
 * @author makejava
 * @since 2020-02-20 00:05:18
 */
public class Goods implements Serializable {
    private static final long serialVersionUID = -45545765025480549L;
    
    private Integer id;
    /**
    * 商品编号
    */
    private String goodsSn;
    /**
    * 商品名称
    */
    private String name;
    /**
    * 商品所属类目ID
    */
    private Integer categoryId;
    
    private Integer brandId;
    /**
    * 商品宣传图片列表，采用JSON数组格式
    */
    private String[] gallery;
    /**
    * 商品关键字，采用逗号间隔
    */
    private String keywords;
    /**
    * 商品简介
    */
    private String brief;
    /**
    * 是否上架
    */
    private Integer isOnSale;
    
    private Integer sortOrder;
    /**
    * 商品页面商品图片
    */
    private String picUrl;
    /**
    * 商品分享朋友圈图片
    */
    private String shareUrl;
    /**
    * 是否新品首发，如果设置则可以在新品首发页面展示
    */
    private Integer isNew;
    /**
    * 是否人气推荐，如果设置则可以在人气推荐页面展示
    */
    private Integer isHot;
    /**
    * 商品单位，例如件、盒
    */
    private String unit;
    /**
    * 专柜价格
    */
    private Double counterPrice;
    /**
    * 零售价格
    */
    private Double retailPrice;
    /**
    * 商品详细介绍，是富文本格式
    */
    private String detail;
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

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String[] getGallery() {
        return gallery;
    }

    public void setGallery(String[] gallery) {
        this.gallery = gallery;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public Integer getIsOnSale() {
        return isOnSale;
    }

    public void setIsOnSale(Integer isOnSale) {
        this.isOnSale = isOnSale;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getCounterPrice() {
        return counterPrice;
    }

    public void setCounterPrice(Double counterPrice) {
        this.counterPrice = counterPrice;
    }

    public Double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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


    public Goods() {
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", goodsSn='" + goodsSn + '\'' +
                ", name='" + name + '\'' +
                ", categoryId=" + categoryId +
                ", brandId=" + brandId +
                ", gallery='" + gallery + '\'' +
                ", keywords='" + keywords + '\'' +
                ", brief='" + brief + '\'' +
                ", isOnSale=" + isOnSale +
                ", sortOrder=" + sortOrder +
                ", picUrl='" + picUrl + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                ", isNew=" + isNew +
                ", isHot=" + isHot +
                ", unit='" + unit + '\'' +
                ", counterPrice=" + counterPrice +
                ", retailPrice=" + retailPrice +
                ", detail='" + detail + '\'' +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                ", deleted=" + deleted +
                '}';
    }
}