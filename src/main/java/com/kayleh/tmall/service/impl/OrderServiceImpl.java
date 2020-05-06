package com.kayleh.tmall.service.impl;

import com.kayleh.tmall.mapper.OrderMapper;
import com.kayleh.tmall.mapper.UserMapper;
import com.kayleh.tmall.pojo.Order;
import com.kayleh.tmall.pojo.OrderExample;
import com.kayleh.tmall.pojo.User;
import com.kayleh.tmall.service.OrderService;
import com.kayleh.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Wizard
 * @Date: 2020/5/6 9:27
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserService userService;

    @Override
    public void add(Order order) {
        orderMapper.insert(order);
    }

    @Override
    public void delete(int id) {
        orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Order order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public Order get(int id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Order> list() {
        OrderExample example =new OrderExample();
        example.setOrderByClause("id desc");
        List<Order> orders =orderMapper.selectByExample(example);
        setUser(orders);
        return orders;
    }

    public void setUser(List<Order> orders) {
        for (Order order : orders) {
            setUser(order);
        }
    }

    public void setUser(Order order) {
//        int uid = getUid();
        User user = userService.get(order.getUid());
        order.setUser(user);
    }
}
