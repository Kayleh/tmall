package com.kayleh.tmall.mapper;

import com.kayleh.tmall.pojo.Category;
import com.kayleh.tmall.util.Page;

import java.util.List;

/**
 * @Author: Wizard
 * @Date: 2020/4/30 16:00
 */
public interface CategoryMapper {
    //分页查询
    public List<Category> list(Page page);
    //查询总数
    public int total();

    public void add(Category category);

    public void delete(int id);

    public Category get(int id);
}
