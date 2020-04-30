package com.kayleh.tmall.pojo;

/**
 * @Author: Wizard
 * @Date: 2020/4/30 15:55
 */
public class Category {

    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name ==null?null:name.trim();
    }
}
