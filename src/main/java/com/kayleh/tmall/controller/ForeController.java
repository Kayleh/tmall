package com.kayleh.tmall.controller;

import com.kayleh.tmall.pojo.Category;
import com.kayleh.tmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author: Wizard
 * @Date: 2020/5/6 19:45
 */
@Controller
@RequestMapping("")
public class ForeController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;

    @RequestMapping("forehome")
    public String home(Model model) {
        //获取分类集合
        List<Category> categoryList = categoryService.list();
        //根据分类填满产品
        productService.fill(categoryList);
        //根据分类填满推荐产品
        productService.fillByRow(categoryList);
        model.addAttribute("cs",categoryList);
        return "fore/home";
    }

}
