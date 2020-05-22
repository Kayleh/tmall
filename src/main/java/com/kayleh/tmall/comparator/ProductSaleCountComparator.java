package com.kayleh.tmall.comparator;

import com.kayleh.tmall.pojo.Product;

import java.util.Comparator;

/**
 * @Author: Wizard
 * @Date: 2020/5/8 11:27
 * 把 价格低的放前面
 */
public class ProductSaleCountComparator implements Comparator<Product> {
    @Override
    public int compare(Product product1, Product product2) {
        return product2.getSaleCount()-product1.getSaleCount();
    }
}
