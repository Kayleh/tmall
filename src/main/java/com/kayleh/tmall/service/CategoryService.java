package com.kayleh.tmall.service;

import com.kayleh.tmall.pojo.Category;
import com.kayleh.tmall.util.Page;

import java.util.List;

/**
 * @Author: Wizard
 * @Date: 2020/4/30 16:01
 */
public interface CategoryService {
    List<Category> list(Page page);
    int total();
    void add(Category category);
    void delete(int id);
    Category get(int id);
}
