package com.kayleh.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kayleh.tmall.pojo.Order;
import com.kayleh.tmall.service.OrderItemService;
import com.kayleh.tmall.service.OrderService;
import com.kayleh.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @Author: Wizard
 * @Date: 2020/5/6 9:41
 */
@Controller
@RequestMapping("")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;

    @RequestMapping("admin_order_list")
    public String list(Model model, Page page){

        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Order> orders = orderService.list();
        int total = (int) new PageInfo<>(orders).getTotal();
        page.setTotal(total);

        orderItemService.fill(orders);
        model.addAttribute("page",page);
        model.addAttribute("os",orders);
        return "admin/listOrder";

    }

    /**
     * 发货
     * @return
     */
    @RequestMapping("admin_order_delivery")
    public String delivery(Order order) throws IOException {

        order.setDeliveryDate(new Date());
        order.setStatus(OrderService.waitConfirm);
        orderService.update(order);
        return "redirect:admin_order_list";

    }
}
