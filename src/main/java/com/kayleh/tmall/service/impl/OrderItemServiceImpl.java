package com.kayleh.tmall.service.impl;

import com.kayleh.tmall.mapper.OrderItemMapper;
import com.kayleh.tmall.pojo.*;
import com.kayleh.tmall.service.OrderItemService;
import com.kayleh.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Wizard
 * @Date: 2020/5/6 8:57
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    ProductService productService;
    @Autowired
    OrderItemMapper orderItemMapper;
    @Override
    public void add(OrderItem orderItem) {
            orderItemMapper.insert(orderItem);
    }

    @Override
    public void delete(int id) {
    orderItemMapper.deleteByPrimaryKey(id);

    }

    @Override
    public void update(OrderItem orderItem) {
        orderItemMapper.updateByPrimaryKeySelective(orderItem);
    }

    @Override
    public OrderItem get(int id) {
        OrderItem orderItem = orderItemMapper.selectByPrimaryKey(id);
        setProduct(orderItem);
        return orderItem;
    }

    @Override
    public List<OrderItem> list() {
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.setOrderByClause("id desc");
        return orderItemMapper.selectByExample(orderItemExample);
    }

    @Override
    public void fill(List<Order> orders) {
        for (Order order : orders) {
            fill(order);
        }
    }

//    @Override
    public void fill(Order order) {
        OrderItemExample example =new OrderItemExample();
        example.createCriteria().andOidEqualTo(order.getId());
        example.setOrderByClause("id desc");
        List<OrderItem> orderItems =orderItemMapper.selectByExample(example);
        setProduct(orderItems);

        float total = 0;//总价
        int totalNumber = 0;//总数
        for (OrderItem orderItem : orderItems) {
            //总价 =  每个订单项的总价 += 每个订单项的购买数量*订单购买的产品的价格
            total += orderItem.getNumber()*orderItem.getProduct().getPromotePrice();
            totalNumber+=orderItem.getNumber();
        }
        order.setOrderItems(orderItems);
        order.setTotal(total);
        order.setTotalNumber(totalNumber);


    }

    /**
     * 销售量
     * @param pid
     * @return
     */
    @Override
    public int getSaleCount(int pid) {
        OrderItemExample orderExample = new OrderItemExample();
        orderExample.createCriteria().andPidEqualTo(pid);
        //根据产品id查询订单项
        List<OrderItem> orderItems = orderItemMapper.selectByExample(orderExample);
        int result = 0;
        for (OrderItem orderItem : orderItems) {
            result += orderItem.getNumber();
        }
        return result;
    }

    public void setProduct(List<OrderItem> orderItems){
        for (OrderItem orderItem : orderItems) {
            //遍历出来的每一个产品添加进订单项
            setProduct(orderItem);
        }
    }
    public void setProduct(OrderItem orderItem){
        Product product = productService.get(orderItem.getPid());
        orderItem.setProduct(product);
    }
}
