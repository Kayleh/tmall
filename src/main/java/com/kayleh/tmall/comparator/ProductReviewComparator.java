package com.kayleh.tmall.comparator;

import com.kayleh.tmall.pojo.Product;

import java.util.Comparator;

/**
 * @Author: Wizard
 * @Date: 2020/5/8 11:25
 * 把 评价数量多的放前面
 */
public class ProductReviewComparator implements Comparator<Product> {
    @Override
    public int compare(Product product1, Product product2) {
        return product2.getReviewCount()-product1.getReviewCount();
    }
}
