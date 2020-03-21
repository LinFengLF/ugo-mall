package com.edu.mju.ugomall.entity;

import java.util.Arrays;

public class GoodsAllinone {
    Goods goods;
    GoodsSpecification[] specifications;
    GoodsAttribute[] attributes;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public GoodsSpecification[] getSpecifications() {
        return specifications;
    }

    public void setSpecifications(GoodsSpecification[] specifications) {
        this.specifications = specifications;
    }

    public GoodsAttribute[] getAttributes() {
        return attributes;
    }

    public void setAttributes(GoodsAttribute[] attributes) {
        this.attributes = attributes;
    }

    public GoodsAllinone() {
    }

    @Override
    public String toString() {
        return "GoodsAllinone{" +
                "goods=" + goods +
                ", specifications=" + Arrays.toString(specifications) +
                ", attributes=" + Arrays.toString(attributes) +
                '}';
    }
}
