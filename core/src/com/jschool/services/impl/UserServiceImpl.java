package com.jschool.services.impl;

import com.jschool.dao.api.UserDao;
import com.jschool.dao.impl.UserDaoImpl;
import com.jschool.entities.User;

import java.util.List;

/**
 * Created by infinity on 08.02.16.
 */
public class UserServiceImpl {

    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public void create(User user){
        userDao.create(user);
    }

    public void update(User user){
        userDao.update(user);
    }

    public void delete(User user){
        userDao.delete(user);
    }

    public User findByEmail(String email){
        return userDao.findUniqueByEmail(email);
    }

    public List<User> findAll(){
        return userDao.findAll();
    }

    public List<User> findByRole(boolean isDriver){
        return userDao.findAllByRole(isDriver);
    }
}
