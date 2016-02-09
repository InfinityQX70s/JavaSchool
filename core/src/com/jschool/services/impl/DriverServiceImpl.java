package com.jschool.services.impl;

import com.jschool.dao.api.DriverDao;
import com.jschool.dao.api.UserDao;
import com.jschool.dao.impl.DriverDaoImpl;
import com.jschool.dao.impl.UserDaoImpl;
import com.jschool.entities.Driver;
import com.jschool.entities.User;

/**
 * Created by infinity on 09.02.16.
 */
public class DriverServiceImpl {

    UserDao userDao = new UserDaoImpl();
    DriverDao driverDao = new DriverDaoImpl();

    public void create(Driver driver, User user){
        User userElement = userDao.findUniqueByEmail(user.getEmail());
        Driver driverElement = driverDao.findUniqueByNumber(driver.getNumber());
        if (userElement == null && driverElement == null){
            userDao.create(user);
            driver.setUser(user);
            driverDao.create(driver);
        }
    }

    public void update(Driver driver, User user){
        
    }
}
