package com.kayleh.tmall.comparator;

import com.kayleh.tmall.pojo.Product;

import java.util.Comparator;

/**
 * @Author: Wizard
 * @Date: 2020/5/8 11:08
 * 比较产品发布时间 ， 新品
 * 把 创建日期晚的放前面
 * compareTo
 * 如果参数字符串等于此字符串，则返回值 0；
 * 如果此字符串小于字符串参数，则返回一个小于 0 的值；
 * 如果此字符串大于字符串参数，则返回一个大于 0 的值。
 */
public class ProductDateComparator implements Comparator<Product> {
    @Override
    public int compare(Product product1, Product product2) {
        return product2.getCreateDate().compareTo(product1.getCreateDate());
    }
}
