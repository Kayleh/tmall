package com.kayleh.tmall.service.impl;

import com.kayleh.tmall.mapper.CategoryMapper;
import com.kayleh.tmall.mapper.ProductMapper;
import com.kayleh.tmall.pojo.Category;
import com.kayleh.tmall.pojo.Product;
import com.kayleh.tmall.pojo.ProductExample;
import com.kayleh.tmall.service.CategoryService;
import com.kayleh.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Wizard
 * @Date: 2020/5/3 10:22
 */
@Service
public class ProductImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryService categoryService;
    @Override
    public void add(Product product) {
        productMapper.insert(product);
    }

    @Override
    public void delete(int id) {
productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Product product) {
productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public Product get(int id) {
        Product product = productMapper.selectByPrimaryKey(id);
        setCategory(product);
        return product;
    }

    public  void setCategory(List<Product> ps){
        for (Product p : ps)
            setCategory(p);
    }
    public void setCategory(Product p){
        int cid = p.getCid();
        Category c = categoryService.get(cid);
    }

    @Override
    public List list(int cid) {
        ProductExample example = new ProductExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        List result = productMapper.selectByExample(example);
        setCategory(result);
        return result;
    }
}
