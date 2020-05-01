package com.kayleh.tmall.service.impl;

import com.kayleh.tmall.mapper.CategoryMapper;
import com.kayleh.tmall.pojo.Category;
import com.kayleh.tmall.service.CategoryService;
import com.kayleh.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Wizard
 * @Date: 2020/4/30 16:03
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;


    @Override
    public List<Category> list(Page page) {
        return categoryMapper.list(page);
    }

    @Override
    public int total() {
        return categoryMapper.total();
    }

    @Override
    public void add(Category category) {
        categoryMapper.add(category);
    }

    @Override
    public void delete(int id) {
        categoryMapper.delete(id);
    }

    @Override
    public Category get(int id) {
        return categoryMapper.get(id);
    }
}
