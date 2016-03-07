package com.jschool.services.impl;

import com.jschool.TransactionManager;
import com.jschool.dao.api.UserDao;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.User;
import com.jschool.services.api.UserService;
import com.jschool.services.api.exception.ServiceException;
import com.jschool.services.api.exception.ServiceStatusCode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by infinity on 17.02.16.
 */
@Service
public class UserServiceImpl implements UserService{

    private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);

    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    /**Return unique user by email address or throw ServiceException with status code
     * USER_NOT_FOUND
     * @param email
     * @return
     * @throws ServiceException
     */
    @Override
    public User findUserByEmail(String email) throws ServiceException {
        try {
            User user = userDao.findUniqueByEmail(email);
            if (user == null){
                throw new ServiceException("User not found", ServiceStatusCode.USER_NOT_FOUND);
            }
            return user;
        }catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }
}
