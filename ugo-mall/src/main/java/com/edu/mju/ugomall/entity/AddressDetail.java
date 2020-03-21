package com.edu.mju.ugomall.entity;

import java.util.Arrays;

public class AddressDetail {
    private String label;
    private String[] value;
    private String cityCode;

    @Override
    public String toString() {
        return "AddressDetail{" +
                "label='" + label + '\'' +
                ", value=" + Arrays.toString(value) +
                ", cityCode='" + cityCode + '\'' +
                '}';
    }

    public AddressDetail() {
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String[] getValue() {
        return value;
    }

    public void setValue(String[] value) {
        this.value = value;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
