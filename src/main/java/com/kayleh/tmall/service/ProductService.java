package com.kayleh.tmall.service;

import com.kayleh.tmall.pojo.Category;
import com.kayleh.tmall.pojo.Product;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Wizard
 * @Date: 2020/5/3 10:20
 */
@Service
public interface ProductService {
    void add(Product product);
    void delete(int id);
    void update(Product product);
    Product get(int id);
    List list(int cid);
    void setFirstProductImage(Product product);

    void fill(List<Category> categoryList);

    void fill(Category category);

    void fillByRow(List<Category> categories);
    //设置销量和评价数量
    void setSaleAndReviewNumber(Product product);
    void setSaleAndReviewNumber(List<Product> productList);

    List<Product> search(String keyword);
}
