package com.kayleh.tmall.service.impl;

import com.kayleh.tmall.mapper.UserMapper;
import com.kayleh.tmall.pojo.User;
import com.kayleh.tmall.pojo.UserExample;
import com.kayleh.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Wizard
 * @Date: 2020/5/5 21:05
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public void add(User user) {
        userMapper.insert(user);
    }

    @Override
    public void delete(int id) {
    userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);

    }

    @Override
    public User get(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List list() {
        UserExample example = new UserExample();
        example.setOrderByClause("id desc");
        return userMapper.selectByExample(example);
    }
}
