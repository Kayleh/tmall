package com.kayleh.tmall.service.impl;

import com.kayleh.tmall.mapper.ReviewMapper;
import com.kayleh.tmall.pojo.Review;
import com.kayleh.tmall.pojo.ReviewExample;
import com.kayleh.tmall.pojo.User;
import com.kayleh.tmall.service.ReviewService;
import com.kayleh.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: Wizard
 * @Date: 2020/5/7 18:01
 */
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewMapper reviewMapper;

    @Autowired
    UserService userService;

    @Override
    public void add(Review review) {
        reviewMapper.insert(review);
    }

    @Override
    public void delete(int id) {
        reviewMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Review review) {
        reviewMapper.updateByPrimaryKeySelective(review);
    }

    @Override
    public Review get(int id) {
        return reviewMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据产品product id 获取评价集合
     * @param pid
     * @return
     */
    @Override
    public List list(int pid) {
        ReviewExample reviewExample = new ReviewExample();
        reviewExample.createCriteria().andPidEqualTo(pid);
        reviewExample.setOrderByClause("id desc");
        List<Review> reviews = reviewMapper.selectByExample(reviewExample);
        return reviews;
    }

    /**
     * 评价数
     * @param pid
     * @return
     */
    @Override
    public int getCount(int pid) {
        return list(pid).size();
    }

    public void setUser(Review review){
        Integer uid = review.getUid();
        User user = userService.get(uid);
        review.setUser(user);
    }
    //评价集合遍历调用↑
    public  void SetUser(List<Review> reviewList){

        for (Review review : reviewList) {
            setUser(review);
        }

    }


}
