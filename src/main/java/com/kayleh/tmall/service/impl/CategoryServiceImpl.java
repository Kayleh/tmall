package com.kayleh.tmall.service.impl;

import com.kayleh.tmall.mapper.CategoryMapper;
import com.kayleh.tmall.pojo.Category;
import com.kayleh.tmall.service.CategoryService;
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
    public List<Category> list() {
        return categoryMapper.list();
    }
}
