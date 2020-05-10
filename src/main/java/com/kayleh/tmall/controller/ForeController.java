package com.kayleh.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.kayleh.tmall.comparator.*;
import com.kayleh.tmall.pojo.*;
import com.kayleh.tmall.service.*;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    ReviewService reviewService;

    @RequestMapping(value = {"","forehome"})
    public String home(Model model) {
        //获取分类集合
        List<Category> categoryList = categoryService.list();
        //根据分类填满产品
        productService.fill(categoryList);
        //根据分类填满推荐产品
        productService.fillByRow(categoryList);
        model.addAttribute("cs", categoryList);
        return "fore/home";
    }

    @RequestMapping("foreregister")
    public String register(User user, Model model) {

        String name = user.getName();
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);
        boolean exist = userService.isExist(name);
        if (exist) {
            //注册失败，用户名存在
            String message = "用户名已经被使用,不能使用";
            model.addAttribute("msg", message);
            model.addAttribute("user", null);
            return "fore/register";
        }

        //注册成功，持久化操作，重定向到注册成功页面
        userService.add(user);
        return "redirect:registerSuccessPage";
    }

    /**
     * 登录
     *
     * @param name
     * @param password
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("forelogin")
    public String login(@RequestParam("name") String name,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {
        name = HtmlUtils.htmlEscape(name);
        User user = userService.get(name, password);
        if (user == null) {
            model.addAttribute("msg", "账号密码错误");
            return "fore/login";
        }
        session.setAttribute("user", user);
        return "redirect:forehome";
    }

    /**
     * 退出登录
     *
     * @param session
     * @return
     */
    @RequestMapping("forelogout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:forehome";
    }

    @RequestMapping("foreproduct")
    public String profuct(int pid, Model model) {
        Product product = productService.get(pid);
        List<ProductImage> singeImage = productImageService.list(product.getId(), ProductImageService.type_single);
        List<ProductImage> detailImage = productImageService.list(product.getId(), ProductImageService.type_detail);
        product.setProductSingleImages(singeImage);
        product.setProductDetailImages(detailImage);

        List<PropertyValue> propertyValueList = propertyValueService.list(product.getId());
        List<Review> reviewList = reviewService.list(product.getId());
        productService.setSaleAndReviewNumber(product);
        model.addAttribute("reviews", reviewList);
        model.addAttribute("p", product);
        model.addAttribute("pvs", propertyValueList);
        return "fore/product";
    }

    // ajax阿贾克斯检查登录
    @RequestMapping("forecheckLogin")
    @ResponseBody
    public String checkLogin(HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (null != user)
            return "success";
        return "fail";

    }

    @RequestMapping("foreloginAjax")
    @ResponseBody
    public String loginAjax(@RequestParam("name") String name, @RequestParam("password") String password, HttpSession session) {
        name = HtmlUtils.htmlEscape(name);
        User user = userService.get(name, password);

        if (null == user) {
            return "fail";
        }
        session.setAttribute("user", user);
        return "success";
    }

    @RequestMapping("forecategory")
    public String category(int cid, String sort, Model model) {
        //根据cid查询分类
        Category category = categoryService.get(cid);
        //根据分类对象填充产品
        productService.fill(category);
        // 给产品填充销量和评价数据
        productService.setSaleAndReviewNumber(category.getProducts());
        if (null != sort) {
            switch (sort) {
                case "review":
                    Collections.sort(category.getProducts(), new ProductReviewComparator());
                    break;
                case "date":
                    Collections.sort(category.getProducts(), new ProductDateComparator());
                    break;
                case "saleCount":
                    Collections.sort(category.getProducts(), new ProductSaleCountComparator());
                    break;
                case "price":
                    Collections.sort(category.getProducts(), new ProductPriceComparator());
                    break;
                case "all":
                    Collections.sort(category.getProducts(), new ProductAllComparator());
                    break;
            }
        }
        model.addAttribute("c", category);
        return "fore/category";
    }

    @RequestMapping("foresearch")
    public String search(String keyword, Model model) {
        PageHelper.offsetPage(0, 20);
        List<Product> productList = productService.search(keyword);
        productService.setSaleAndReviewNumber(productList);

        model.addAttribute("ps", productList);
        return "fore/searchResult";
    }

    @RequestMapping("forebuyone")
    public String buyone(int pid, int num, HttpSession session) {
        //获取产品
        Product product = productService.get(pid);
        //订单号
        int oiid = 0;
        User user = (User) session.getAttribute("user");
        boolean found = false;
        List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
        for (OrderItem orderItem : orderItems) {
            //判断订单是否存在此产品
            if (orderItem.getProduct().getId().intValue() == product.getId().intValue()) {
                //订单项的此产品数量加一
                orderItem.setNumber(orderItem.getNumber() + num);
                //更新订单项！！
                orderItemService.update(orderItem);
                found = true;
                oiid = orderItem.getId();
                break;
            }
        }
        if (!found) {
            OrderItem orderItem = new OrderItem();
            orderItem.setUid(user.getId());
            orderItem.setNumber(num);
            orderItem.setPid(pid);
            //持久化操作
            orderItemService.add(orderItem);
            oiid = orderItem.getId();
        }
        return "redirect:forebuy?oiids=" + oiid;
    }

    @RequestMapping("forebuy")
    public String buy(HttpSession session, String[] oiids, Model model) {

        List<OrderItem> orderItemList = new ArrayList<>();
        float total = 0;
        for (String oiid : oiids) {
            //获取每个订单项 价格数量递增
            int id = Integer.parseInt(oiid);
            OrderItem orderItem = orderItemService.get(id);
            total += orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
            //保存到订单项集合
            orderItemList.add(orderItem);
        }
        //订单项集合保存到session域中
        session.setAttribute("ois", orderItemList);
        model.addAttribute("total", total);
        return "fore/buy";


    }


    @RequestMapping("foreaddCart")
    @ResponseBody
    public String addCart(int pid, int num, Model model, HttpSession session) {

        Product product = productService.get(pid);
        User user = (User) session.getAttribute("user");
        boolean found = false;
        List<OrderItem> orderItemList = orderItemService.listByUser(product.getId());
        for (OrderItem orderItem : orderItemList) {
            if (orderItem.getProduct().getId().intValue() == product.getId().intValue()) {
                //如果订单项存在该产品
                orderItem.setNumber(orderItem.getNumber() + num);
                orderItemService.update(orderItem);
                found = true;
                break;
            }
        }
        if (!found) {
            OrderItem orderItem = new OrderItem();
            orderItem.setNumber(num);
            orderItem.setUid(product.getId());
            orderItem.setPid(pid);
            orderItemService.add(orderItem);
        }
        return "success";

    }

    @RequestMapping("forecart")
    public String cart(Model model, HttpSession session) {

        User user = (User) session.getAttribute("user");
        List<OrderItem> orderItemList = orderItemService.listByUser(user.getId());
        model.addAttribute("ois", orderItemList);
        return "fore/cart";

    }

    @RequestMapping("forechangeOrderItem")
    public String changeOrderItem(Model model, HttpSession session, int pid, int number) {

        User user = (User) session.getAttribute("user");
        if (null == user) {
            return "fail";
        }
        List<OrderItem> orderItemList = orderItemService.listByUser(user.getId());
        for (OrderItem orderItem : orderItemList) {
            if (orderItem.getProduct().getId().intValue() == pid) {
                //根据传进来的number设置number的值
                orderItem.setNumber(number);
                orderItemService.update(orderItem);
                break;
            }
        }
        return "success";

    }

    @RequestMapping("foredeleteOrderItem")
    public String deleteOrderItem(Model model, HttpSession session, int orderItemId) {

        User user = (User) session.getAttribute("user");
        if (null != user) {
            return "fail";
        }

        orderItemService.delete(orderItemId);
        return "success";

    }


    @RequestMapping("forecreateOrder")
    public String createOrder(Model model,Order order,HttpSession session){

        User user = (User) session.getAttribute("user");
        //    订单扣
        String orderCode=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+RandomUtils.nextInt(10000);
        order.setCreateDate(new Date());
        order.setOrderCode(orderCode);
        //新创建的订单为等待付款
        order.setStatus(OrderService.waitPay);
        List<OrderItem> orderItemList = (List<OrderItem>) session.getAttribute("ois");

        float total = orderService.add(order,orderItemList);

        return "redirect:forealipay?oid="+order.getId() +"&total="+total;
    }

    @RequestMapping("forepayed")
    public String payed(int oid,float total,Model model){

        Order order = orderService.get(oid);
        order.setStatus(OrderService.waitDelivery);
        order.setPayDate(new Date());
        orderService.update(order);
        model.addAttribute("o",order);
        return "fore/payed";

    }
    @RequestMapping("forebought")
    public String bought(HttpSession session,Model model){

        User user = (User) session.getAttribute("user");

        List<Order> orderList = orderService.list(user.getId(), OrderService.delete);
        //填充订单
        orderItemService.fill(orderList);

        model.addAttribute("os",orderList);

        return "fore/bought";
    }
    @RequestMapping("foreconfirmPay")
    public String confirmPay(int oid,Model model){

        Order order = orderService.get(oid);
        orderItemService.fill(order);
        model.addAttribute("o",order);
        return "foce/comfirmPay";

    }
    @RequestMapping("foreorderConfirmed")
    public String orderConfirmed(int oid,Model model){

        Order order = orderService.get(oid);
        //等待评价
        order.setStatus(OrderService.waitReview);
        //完成订单时间
        order.setConfirmDate(new Date());
        orderService.update(order);
        return "fore/orderConfirmed";
    }

    @RequestMapping("foredeleteOrder")
    @ResponseBody
    public String deleteOrder(Model model,int oid){

        Order order = orderService.get(oid);
        order.setStatus(OrderService.delete);
        orderService.update(order);
        return "success";
    }
    @RequestMapping("forereview")
    public String review(int oid,Model model){

//        Order order = orderService.get(oid);
//
//        orderItemService.fill(order);
//
//        Product product = order.getOrderItems().get(0).getProduct();
//
//        List<Review> review = reviewService.list(product.getId());
//
//        model.addAttribute("p",product);
//
//        model.addAttribute("o",order);
//
//        model.addAttribute("review",review);
//
//        return "fore/review";

        orderItemService.fill(orderService.get(oid));

        model.addAttribute("p", orderService.get(oid).getOrderItems().get(0).getProduct());

        model.addAttribute("o", orderService.get(oid));

        model.addAttribute("review", reviewService.list(orderService.get(oid).getOrderItems().get(0).getProduct().getId()));

        return "fore/review";


    }



}



