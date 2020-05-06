package com.kayleh.tmall.service;

import com.kayleh.tmall.pojo.Category;
import com.kayleh.tmall.pojo.Product;

import java.util.List;

/**
 * @Author: Wizard
 * @Date: 2020/5/3 10:20
 */
public interface ProductService {
    void add(Product product);
    void delete(int id);
    void update(Product p);
    Product get(int id);
    List list(int cid);
    void setFirstProductImage(Product product);

    void fill(List<Category> cs);

    void fill(Category category);

    void fillByRow(List<Category> categories);
}
