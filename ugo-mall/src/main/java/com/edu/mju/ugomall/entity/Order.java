package com.edu.mju.ugomall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.io.Serializable;

/**
 * 订单表(Order)实体类
 *
 * @author makejava
 * @since 2020-03-01 21:31:59
 */
public class Order implements Serializable {
    private static final long serialVersionUID = 648295823143648405L;
    
    private Integer id;
    /**
    * 用户表的用户ID
    */
    private Integer userId;
    /**
    * 订单编号
    */
    private String orderSn;
    /**
    * 订单状态
    */
    private Integer orderStatus;
    /**
    * 收货人名称
    */
    private String consignee;
    /**
    * 收货人手机号
    */
    private String mobile;
    /**
    * 收货具体地址
    */
    private String address;
    /**
    * 用户订单留言
    */
    private String message;
    /**
    * 商品总费用
    */
    private String goodsPrice;
    /**
    * 配送费用
    */
    private String freightPrice;
    /**
    * 优惠券减免
    */
    private String couponPrice;
    /**
    * 用户积分减免
    */
    private String integralPrice;
    /**
    * 团购优惠价减免
    */
    private String grouponPrice;
    /**
    * 订单费用， = goods_price + freight_price - coupon_price
    */
    private String orderPrice;
    /**
    * 实付费用， = order_price - integral_price
    */
    private String actualPrice;
    /**
    * 微信付款编号
    */
    private String payId;
    /**
    * 微信付款时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime payTime;
    /**
    * 发货编号
    */
    private String shipSn;
    /**
    * 发货快递公司
    */
    private String shipChannel;
    /**
    * 发货开始时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime shipTime;
    /**
    * 用户确认收货时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime confirmTime;
    /**
    * 待评价订单商品数量
    */
    private Integer comments;
    /**
    * 订单关闭时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime endTime;
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

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getFreightPrice() {
        return freightPrice;
    }

    public void setFreightPrice(String freightPrice) {
        this.freightPrice = freightPrice;
    }

    public String getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(String couponPrice) {
        this.couponPrice = couponPrice;
    }

    public String getIntegralPrice() {
        return integralPrice;
    }

    public void setIntegralPrice(String integralPrice) {
        this.integralPrice = integralPrice;
    }

    public String getGrouponPrice() {
        return grouponPrice;
    }

    public void setGrouponPrice(String grouponPrice) {
        this.grouponPrice = grouponPrice;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(String actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }


    public String getShipSn() {
        return shipSn;
    }

    public void setShipSn(String shipSn) {
        this.shipSn = shipSn;
    }

    public String getShipChannel() {
        return shipChannel;
    }

    public void setShipChannel(String shipChannel) {
        this.shipChannel = shipChannel;
    }




    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public LocalDateTime getPayTime() {
        return payTime;
    }

    public void setPayTime(LocalDateTime payTime) {
        this.payTime = payTime;
    }

    public LocalDateTime getShipTime() {
        return shipTime;
    }

    public void setShipTime(LocalDateTime shipTime) {
        this.shipTime = shipTime;
    }

    public LocalDateTime getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(LocalDateTime confirmTime) {
        this.confirmTime = confirmTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
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


    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", orderSn='" + orderSn + '\'' +
                ", orderStatus=" + orderStatus +
                ", consignee='" + consignee + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                ", message='" + message + '\'' +
                ", goodsPrice='" + goodsPrice + '\'' +
                ", freightPrice='" + freightPrice + '\'' +
                ", couponPrice='" + couponPrice + '\'' +
                ", integralPrice='" + integralPrice + '\'' +
                ", grouponPrice='" + grouponPrice + '\'' +
                ", orderPrice='" + orderPrice + '\'' +
                ", actualPrice='" + actualPrice + '\'' +
                ", payId='" + payId + '\'' +
                ", payTime=" + payTime +
                ", shipSn='" + shipSn + '\'' +
                ", shipChannel='" + shipChannel + '\'' +
                ", shipTime=" + shipTime +
                ", confirmTime=" + confirmTime +
                ", comments=" + comments +
                ", endTime=" + endTime +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                ", deleted=" + deleted +
                '}';
    }
}