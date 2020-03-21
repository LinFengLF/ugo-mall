package com.edu.mju.ugomall.entity;

public class OrderAllinone extends Order {
    private String goodsId;
    private String picUrl;
    private String goodsName;
    private String price;
    private String specifications;
    private String num;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }


    public OrderAllinone() {
    }

    @Override
    public String toString() {
        return "OrderAllinone{" +
                "goodsId='" + goodsId + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", price='" + price + '\'' +
                ", specifications='" + specifications + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}
