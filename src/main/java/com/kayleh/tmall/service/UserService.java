package com.kayleh.tmall.service;

import com.kayleh.tmall.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Wizard
 * @Date: 2020/5/5 21:04
 */
@Service
public interface UserService {
    void add(User user);
    void delete(int id);
    void update(User user);
    User get(int id);
    List list();
    boolean isExist(String name);

    User get(String name, String password);
}
