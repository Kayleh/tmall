package com.kayleh.tmall.service;

import com.kayleh.tmall.pojo.Order;
import com.kayleh.tmall.pojo.OrderItem;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Wizard
 * @Date: 2020/5/6 8:55
 */
@Service
public interface OrderItemService {
    void add(OrderItem orderItem);

    void delete(int id);
    void update(OrderItem orderItem);
    OrderItem get(int id);
    List list();

    void fill(List<Order> orders);

    void fill(Order order);

    //获取产品的销售量
    int getSaleCount(int pid);

    //用户的订单项
    List<OrderItem> listByUser(int uid);
}
