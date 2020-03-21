package com.edu.mju.ugomall.entity;

import java.io.Serializable;

/**
 * 物流公司(Logistics)实体类
 *
 * @author makejava
 * @since 2020-03-16 09:40:01
 */
public class Logistics implements Serializable {
    private static final long serialVersionUID = -26425402343257153L;
    
    private Integer id;
    
    private String code;
    
    private String name;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Logistics() {
    }

    @Override
    public String toString() {
        return "Logistics{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}