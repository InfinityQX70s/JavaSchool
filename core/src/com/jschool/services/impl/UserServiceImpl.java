package com.jschool.services.impl;

import com.jschool.TransactionManager;
import com.jschool.dao.api.UserDao;
import com.jschool.entities.User;
import com.jschool.services.api.UserService;

/**
 * Created by infinity on 17.02.16.
 */
public class UserServiceImpl implements UserService{

    private UserDao userDao;
    private TransactionManager transactionManager;

    public UserServiceImpl(UserDao userDao, TransactionManager transactionManager) {
        this.userDao = userDao;
        this.transactionManager = transactionManager;
    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.findUniqueByEmail(email);
    }
}
