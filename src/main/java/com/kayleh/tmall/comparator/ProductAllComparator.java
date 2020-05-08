package com.kayleh.tmall.comparator;

import com.kayleh.tmall.pojo.Product;

import java.util.Comparator;

/**
 * @Author: Wizard
 * @Date: 2020/5/8 11:03
 * 综合比较器 销量*评价高的放前面
 */
public class ProductAllComparator implements Comparator<Product> {
    @Override
    public int compare(Product product1, Product product2) {
        return product2.getReviewCount()*product2.getSaleCount()-product1.getReviewCount()*product2.getSaleCount();
    }


}
