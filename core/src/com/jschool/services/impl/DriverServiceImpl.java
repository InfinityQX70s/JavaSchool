package com.jschool.services.impl;

import com.jschool.TransactionManager;
import com.jschool.dao.api.DriverDao;
import com.jschool.dao.api.DriverStatisticDao;
import com.jschool.dao.api.DriverStatusLogDao;
import com.jschool.dao.api.UserDao;
import com.jschool.dao.impl.DriverDaoImpl;
import com.jschool.dao.impl.DriverStatisticDaoImpl;
import com.jschool.dao.impl.DriverStatusLogDaoImpl;
import com.jschool.dao.impl.UserDaoImpl;
import com.jschool.entities.*;
import com.jschool.services.api.DriverService;

import java.time.Period;
import java.util.Date;
import java.util.List;

/**
 * Created by infinity on 09.02.16.
 */
public class DriverServiceImpl implements DriverService{

    private UserDao userDao;
    private DriverDao driverDao;
    private DriverStatusLogDao driverStatusLogDao;
    private DriverStatisticDao driverStatisticDao;
    private TransactionManager transactionManager;

    public DriverServiceImpl(UserDao userDao, DriverDao driverDao,
                             DriverStatusLogDao driverStatusLogDao,
                             DriverStatisticDao driverStatisticDao,
                             TransactionManager transactionManager) {
        this.userDao = userDao;
        this.driverDao = driverDao;
        this.driverStatusLogDao = driverStatusLogDao;
        this.driverStatisticDao = driverStatisticDao;
        this.transactionManager = transactionManager;
    }


    public void create(Driver driver, User user){
        try {
            transactionManager.getTransaction().begin();
            userDao.create(user);
            driver.setUser(user);
            driverDao.create(driver);
            DriverStatusLog driverStatusLog = new DriverStatusLog();
            driverStatusLog.setStatus(DriverStatus.rest);
            driverStatusLog.setTimestamp(new Date());
            driverStatusLog.setDriver(driver);
            driverStatusLogDao.create(driverStatusLog);
            transactionManager.getTransaction().commit();
        }finally {
            transactionManager.getTransaction().rollbackIfActive();
        }

    }

    public void update(Driver driver, User user){
        try {
            transactionManager.getTransaction().begin();
            User userElement = userDao.findUniqueByEmail(user.getEmail());
            Driver driverElement = driverDao.findUniqueByNumber(driver.getNumber());
            if (userElement != null && driverElement != null){
                user.setId(userElement.getId());
                driver.setId(driverElement.getId());
                userDao.update(user);
                driver.setUser(user);
                driverDao.update(driver);
            }
            transactionManager.getTransaction().commit();
        }finally {
            transactionManager.getTransaction().rollbackIfActive();
        }

    }

    public void delete(int number){
        try {
            transactionManager.getTransaction().begin();
            Driver driver = driverDao.findUniqueByNumber(number);
            if (driver != null && driver.getOrder() == null){
                User user = driver.getUser();
                driverDao.delete(driver);
                userDao.delete(user);
            }
            transactionManager.getTransaction().commit();
        }finally {
            transactionManager.getTransaction().rollbackIfActive();
        }
    }

    public Driver findUniqueByNumber(int number){
        return driverDao.findUniqueByNumber(number);
    }

    public List<Driver> findAll(){
        return driverDao.findAll();
    }

    public void setStatusByDriverNumberAndStatus(int number, DriverStatus status){
        try {
            transactionManager.getTransaction().begin();
            Driver driver = driverDao.findUniqueByNumber(number);
            if (driver != null){
                DriverStatusLog statusLog = driverStatusLogDao.findLastStatus(driver);
                if (statusLog.getStatus() != status){
                    if (statusLog.getStatus() == DriverStatus.driving){
                        long diff = new Date().getTime() - statusLog.getTimestamp().getTime();
                        int diffHours = (int) (diff / (60 * 60 * 1000));
                        DriverStatistic driverStatistic = new DriverStatistic();
                        driverStatistic.setTimestamp(new Date());
                        driverStatistic.setHoursWorked(diffHours);
                        driverStatistic.setDriver(driver);
                        driverStatisticDao.create(driverStatistic);
                    }
                }
                DriverStatusLog driverStatusLog = new DriverStatusLog();
                driverStatusLog.setStatus(status);
                driverStatusLog.setTimestamp(new Date());
                driverStatusLog.setDriver(driver);
                driverStatusLogDao.create(driverStatusLog);
            }
            transactionManager.getTransaction().commit();
        }finally {
            transactionManager.getTransaction().rollbackIfActive();
        }

    }
}
