package com.kayleh.tmall.service;

import com.kayleh.tmall.pojo.Property;

import java.util.List;

/**
 * @Author: Wizard
 * @Date: 2020/5/2 17:18
 */
public interface PropertyService {
    void add(Property c);
    void delete(int id);
    void update(Property c);
    Property get(int id);
    List list(int cid);
}
