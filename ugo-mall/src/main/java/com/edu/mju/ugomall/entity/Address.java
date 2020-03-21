package com.edu.mju.ugomall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.io.Serializable;

/**
 * 收货地址表(Address)实体类
 *
 * @author makejava
 * @since 2020-03-01 16:51:37
 */
public class Address implements Serializable {
    private static final long serialVersionUID = 143958091784455465L;
    
    private String id;

    private Object address;
    /**
    * 收货人名称
    */
    private String name;
    /**
    * 用户表的用户ID
    */
    private String userId;
    /**
    * 行政区域表的省ID
    */
    private String province;
    /**
    * 行政区域表的市ID
    */
    private String city;
    /**
    * 行政区域表的区县ID
    */
    private String county;
    /**
    * 详细收货地址
    */
    private String addressDetail;
    /**
    * 地区编码
    */
    private String areaCode;
    /**
    * 邮政编码
    */
    private String postalCode;
    /**
    * 手机号码
    */
    private String tel;
    /**
    * 是否默认地址
    */
    private Integer isDefault;

    /**
     *地址编码
     */
    private String[] cityValue;

    private String label;

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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
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

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }


    public String[] getCityValue() {
        return cityValue;
    }

    public void setCityValue(String[] cityValue) {
        this.cityValue = cityValue;
    }


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Address() {
    }

    @Override
    public String toString() {
        return "Address{" +
                "id='" + id + '\'' +
                ", address=" + address +
                ", name='" + name + '\'' +
                ", userId='" + userId + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", addressDetail='" + addressDetail + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", tel='" + tel + '\'' +
                ", isDefault=" + isDefault +
                ", cityValue=" + Arrays.toString(cityValue) +
                ", label='" + label + '\'' +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                ", deleted=" + deleted +
                '}';
    }
}