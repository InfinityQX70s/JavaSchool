package com.jschool.services.impl;

import com.jschool.dao.api.UserDao;
import com.jschool.dao.impl.UserDaoImpl;
import com.jschool.entities.UserEntity;

import java.util.List;

/**
 * Created by infinity on 08.02.16.
 */
public class UserServiceImpl {

    private UserDao userDao;

    public UserServiceImpl() {
        userDao = new UserDaoImpl();
    }

    public void create(UserEntity user){
        userDao.create(user);
    }

    public void update(UserEntity user){
        userDao.update(user);
    }

    public void delete(UserEntity user){
        userDao.delete(user);
    }

    public UserEntity findByEmail(String email){
        return userDao.findByEmail(email);
    }

    public List<UserEntity> findAll(){
        return userDao.findAll();
    }

    public List<UserEntity> findByRole(boolean isDriver){
        return userDao.findByRole(isDriver);
    }
}
