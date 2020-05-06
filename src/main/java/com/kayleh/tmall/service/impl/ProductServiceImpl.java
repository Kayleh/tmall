package com.kayleh.tmall.service.impl;


import com.kayleh.tmall.mapper.ProductMapper;
import com.kayleh.tmall.pojo.Category;
import com.kayleh.tmall.pojo.Product;
import com.kayleh.tmall.pojo.ProductExample;
import com.kayleh.tmall.pojo.ProductImage;
import com.kayleh.tmall.service.CategoryService;
import com.kayleh.tmall.service.ProductImageService;
import com.kayleh.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductImageService productImageService;

    @Override
    public void add(Product p) {
        productMapper.insert(p);
    }

    @Override
    public void delete(int id) {
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Product p) {
        productMapper.updateByPrimaryKeySelective(p);
    }

    @Override
    public Product get(int id) {
        Product p = productMapper.selectByPrimaryKey(id);
        setFirstProductImage(p);
        setCategory(p);
        return p;
    }

    public void setCategory(List<Product> ps) {
        for (Product p : ps)
            setCategory(p);
    }

    public void setCategory(Product p) {
        int cid = p.getCid();
        Category c = categoryService.get(cid);
        p.setCategory(c);
    }

    @Override
    public List list(int cid) {
        ProductExample example = new ProductExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        List result = productMapper.selectByExample(example);
        setCategory(result);
        setFirstProductImage(result);
        return result;
    }

    @Override
    public void setFirstProductImage(Product p) {
        List<ProductImage> pis = productImageService.list(p.getId(), ProductImageService.type_single);
        if (!pis.isEmpty()) {
            ProductImage pi = pis.get(0);
            p.setFirstProductImage(pi);
        }
    }

    public void setFirstProductImage(List<Product> ps) {
        for (Product p : ps) {
            setFirstProductImage(p);
        }
    }

    //为分类填充产品集合
    @Override
    public void fill(List<Category> categories) {
        for (Category category : categories) {
            fill(category);
        }
    }

    //
    @Override
    public void fill(Category category) {
        List<Product> productList = list(category.getId());
        category.setProducts(productList);
    }

    //为多个分类填充推荐产品集合，即把分类下的产品集合，按照8个为一行，拆成多行，以利于后续页面上进行显示
    @Override
    public void fillByRow(List<Category> categories) {
        //每行显示8个
        int productNumberEachRow = 8;
        for (Category category : categories) {
            //分类下的产品集合
            List<Product> productList = category.getProducts();
            //每一行的数据保存为二维数组
            List<List<Product>> productByRow = new ArrayList<>();
            //遍历把每一个行集合放入二维数组
            for (int i=0;i<productList.size();i+=productNumberEachRow){
                int size = productNumberEachRow+i;
                size=size>productList.size()?productList.size():size;
                //截取 i - size 索引中的产品 放进一个二维数组  每个数组为一行
                List<Product> productsOfEachRow = productList.subList(i, size);
                productByRow.add(productsOfEachRow);
            }
            //把产品集合保存到该分类下
            category.setProductsByRow(productByRow);
        }

    }


}