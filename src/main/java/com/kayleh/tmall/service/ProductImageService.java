package com.kayleh.tmall.service;

import com.kayleh.tmall.pojo.ProductImage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Wizard
 * @Date: 2020/5/4 17:23
 */
@Service
public interface ProductImageService {
    //单个图片
    String type_single = "type_single";
    //详情图片
    String type_detail = "type_detail";

    void add(ProductImage productImage);
    void delete(int id);
    void update(ProductImage productImage);
    ProductImage get(int id);
    List list(int pid,String type);
}
