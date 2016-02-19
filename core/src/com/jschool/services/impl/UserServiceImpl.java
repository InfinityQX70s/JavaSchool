package com.jschool.services.impl;

import com.jschool.TransactionManager;
import com.jschool.dao.api.UserDao;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.User;
import com.jschool.services.api.UserService;
import com.jschool.services.api.exception.ServiceExeption;
import com.jschool.services.api.exception.StatusCode;

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
    public User findUserByEmail(String email) throws ServiceExeption {
        try {
            User user = userDao.findUniqueByEmail(email);
            if (user == null){
                throw new ServiceExeption("User not found", StatusCode.NOT_FOUND);
            }
            return user;
        }catch (DaoException e) {
            throw new ServiceExeption("Unknown exception", e, StatusCode.UNKNOWN);
        }
    }
}
