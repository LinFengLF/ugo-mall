package com.edu.mju.ugomall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.io.Serializable;

/**
 * 意见反馈表(Feedback)实体类
 *
 * @author makejava
 * @since 2020-03-20 15:43:21
 */
public class Feedback implements Serializable {
    private static final long serialVersionUID = -75375512677811269L;
    
    private Integer id;
    /**
    * 用户表的用户ID
    */
    private Integer userId;
    /**
    * 用户名称
    */
    private String username;
    /**
    * 联系方式
    */
    private String contact;
    /**
    * 反馈类型
    */
    private String feedType;
    /**
    * 反馈内容
    */
    private String content;
    /**
    * 是否含有图片
    */
    private Integer hasPicture;
    /**
    * 图片地址列表，采用JSON数组格式
    */
    private String[] picUrls;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getFeedType() {
        return feedType;
    }

    public void setFeedType(String feedType) {
        this.feedType = feedType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getHasPicture() {
        return hasPicture;
    }

    public void setHasPicture(Integer hasPicture) {
        this.hasPicture = hasPicture;
    }

    public String[] getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(String[] picUrls) {
        this.picUrls = picUrls;
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

    public Feedback() {
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", contact='" + contact + '\'' +
                ", feedType='" + feedType + '\'' +
                ", content='" + content + '\'' +
                ", hasPicture=" + hasPicture +
                ", picUrls='" + picUrls + '\'' +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                ", deleted=" + deleted +
                '}';
    }
}