package com.kayleh.tmall.comparator;

import com.kayleh.tmall.pojo.Product;

import java.util.Comparator;

/**
 * @Author: Wizard
 * @Date: 2020/5/8 11:17
 * 把 价格低的放前面
 */
public class ProductPriceComparator implements Comparator<Product> {
    @Override
    public int compare(Product product1, Product product2) {
        return (int) (product1.getPromotePrice()-product2.getPromotePrice());
    }
}
