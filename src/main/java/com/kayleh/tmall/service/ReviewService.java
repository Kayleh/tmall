package com.kayleh.tmall.service;

import com.kayleh.tmall.pojo.Review;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Wizard
 * @Date: 2020/5/7 17:48
 */
@Service
public interface ReviewService {
    void add(Review review);

    void delete(int id);
    void update(Review review);
    Review get(int id);
    List list(int pid);
    //通过产品id获取评价
    int getCount(int pid);
}
