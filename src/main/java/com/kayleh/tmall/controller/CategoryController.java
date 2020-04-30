package com.kayleh.tmall.controller;

import com.kayleh.tmall.pojo.Category;
import com.kayleh.tmall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author: Wizard
 * @Date: 2020/4/30 16:06
 */
@Controller
@RequestMapping("")//访问时不需要额外路径
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_category_list")
    public String list(Model model){
        List<Category> cs = categoryService.list();
        model.addAttribute("cs", cs);
        return "admin/listCategory";
    }

}
