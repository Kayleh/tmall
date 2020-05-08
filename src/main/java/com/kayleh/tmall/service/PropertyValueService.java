package com.kayleh.tmall.service;

import com.kayleh.tmall.pojo.Product;
import com.kayleh.tmall.pojo.Property;
import com.kayleh.tmall.pojo.PropertyValue;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Wizard
 * @Date: 2020/5/5 16:27
 */
@Service
public interface PropertyValueService {

    void init(Product product);
    void update(PropertyValue propertyValue);

    PropertyValue get(int ptid,int pid);

    List<PropertyValue> list(int pid);

}
