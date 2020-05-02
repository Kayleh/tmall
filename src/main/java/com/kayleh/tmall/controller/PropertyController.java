package com.kayleh.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kayleh.tmall.pojo.Category;
import com.kayleh.tmall.pojo.Property;
import com.kayleh.tmall.service.CategoryService;
import com.kayleh.tmall.service.PropertyService;
import com.kayleh.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author: Wizard
 * @Date: 2020/5/2 17:52
 */
//@Controller
//@RequestMapping("")
//public class PropertyController {
//    @Autowired
//    CategoryService categoryService;
//    @Autowired
//    PropertyService propertyService;
//
//    @RequestMapping("admin_property_add")
//    public String add(Property property, Model model) {
//        propertyService.add(property);
//        return "redirect:admin_property_list?cid=" + property.getCid();
//    }
//
//    @RequestMapping("admin_property_delete")
//    public String delete(int id) {
//        Property p = propertyService.get(id);
//        propertyService.delete(id);
//        return "redirect:admin_property_list?cid=" + p.getCid();
//    }
//
//    @RequestMapping("admin_property_update")
//    public String uodate(Property p) {
//        propertyService.update(p);
//        return "redirect:admin_property_list?cid=" + p.getCid();
//    }
//
//    @RequestMapping("admin_property_edit")
//    public String edit(Model model, int id) {
//        Property property = propertyService.get(id);
//        Category category = categoryService.get(property.getId());
//        property.setCategory(category);
//        model.addAttribute("p", property);
//        return "admin/editProperty";
//    }
//
//    @RequestMapping("admin_property_list")
//    public String list(Model model, int cid, Page page) {
//        //根据cid查询到属性的分类
//        Category category = categoryService.get(cid);
//        //通过PageHelper设置分页参数
//        PageHelper.offsetPage(page.getStart(), page.getCount());
//        // 基于cid，获取当前分类下的属性集合
//        List<Property> propertyList = propertyService.list(cid);
//        // 把总数设置给分页page对象
//        int total = (int) new PageInfo<>(propertyList).getTotal();
//        page.setTotal(total);
//        //拼接字符串"&cid="+c.getId()，设置给page对象的Param值。
//        //因为属性分页都是基于当前分类下的分页，所以分页的时候需要传递这个cid
//        page.setParam("&cid" + category.getId());
//
//        model.addAttribute("ps", propertyList);
//        model.addAttribute("c", category);
//        model.addAttribute("page", page);
//        return "admin/listProperty";
//    }
//}

@Controller
@RequestMapping("")
public class PropertyController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    PropertyService propertyService;

    @RequestMapping("admin_property_add")
    public String add(Model model, Property p) {
        propertyService.add(p);
        return "redirect:admin_property_list?cid=" + p.getCid();
    }

    @RequestMapping("admin_property_delete")
    public String delete(int id) {
        Property p = propertyService.get(id);
        propertyService.delete(id);
        return "redirect:admin_property_list?cid=" + p.getCid();
    }

    @RequestMapping("admin_property_edit")
    public String edit(Model model, int id) {
        Property p = propertyService.get(id);
        Category c = categoryService.get(p.getCid());
        p.setCategory(c);
        model.addAttribute("p", p);
        return "admin/editProperty";
    }

    @RequestMapping("admin_property_update")
    public String update(Property p) {
        propertyService.update(p);
        return "redirect:admin_property_list?cid=" + p.getCid();
    }

    @RequestMapping("admin_property_list")
    public String list(int cid, Model model, Page page) {
        Category c = categoryService.get(cid);

        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Property> ps = propertyService.list(cid);

        int total = (int) new PageInfo<>(ps).getTotal();
        page.setTotal(total);
        page.setParam("&cid=" + c.getId());

        model.addAttribute("ps", ps);
        model.addAttribute("c", c);
        model.addAttribute("page", page);

        return "admin/listProperty";
    }
}