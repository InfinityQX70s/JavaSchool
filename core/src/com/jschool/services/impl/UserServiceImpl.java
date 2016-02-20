package com.jschool.services.impl;

import com.jschool.TransactionManager;
import com.jschool.dao.api.UserDao;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.User;
import com.jschool.services.api.UserService;
import com.jschool.services.api.exception.ServiceException;
import com.jschool.services.api.exception.ServiceStatusCode;

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
    public User findUserByEmail(String email) throws ServiceException {
        try {
            User user = userDao.findUniqueByEmail(email);
            if (user == null){
                throw new ServiceException("User not found", ServiceStatusCode.NOT_FOUND);
            }
            return user;
        }catch (DaoException e) {
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }
}
