package com.jschool.services.impl;

import com.jschool.CustomTransaction;
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
import org.apache.commons.codec.digest.DigestUtils;

import java.time.Period;
import java.util.ArrayList;
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

    public void addDriver(Driver driver) {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            User user = driver.getUser();
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
            userDao.create(user);
            List<DriverStatusLog> driverStatusLogs = new ArrayList<>();
            DriverStatusLog driverStatusLog = new DriverStatusLog();
            driverStatusLog.setStatus(DriverStatus.rest);
            driverStatusLog.setTimestamp(new Date());
            driverStatusLog.setDriver(driver);
            driverStatusLogs.add(driverStatusLog);
            driver.setStatusLogs(driverStatusLogs);
            driverDao.create(driver);
            //driverStatusLogDao.create(driverStatusLog);
            ct.commit();
        }finally {
            ct.rollbackIfActive();
        }
    }

    public void updateDrive(Driver driver) {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            Driver driverElement = driverDao.findUniqueByNumber(driver.getNumber());
            if (driverElement != null){
                driverElement.setFirstName(driver.getFirstName());
                driverElement.setLastName(driver.getLastName());
                driverDao.update(driverElement);
            }
            ct.commit();
        }finally {
            ct.rollbackIfActive();
        }
    }

    public void deleteDriver(int number) {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            Driver driver = driverDao.findUniqueByNumber(number);
            if (driver != null && driver.getOrder() == null){
                User user = driver.getUser();
                driverDao.delete(driver);
                userDao.delete(user);
            }
            ct.commit();
        }finally {
            ct.rollbackIfActive();
        }
    }

    public Driver getDriverByPersonalNumber(int number) {
        return driverDao.findUniqueByNumber(number);
    }

    public List<Driver> findAllDrivers() {
        return driverDao.findAll();
    }

    public List<Driver> findAllAvailableDrivers(int hoursWorked) {
        List<Driver> drivers = driverDao.findAllFreeDrivers();
        List<Driver> driverList = new ArrayList<Driver>();
        for (Driver driver : drivers) {
            List<DriverStatistic> driverStatistics = driverStatisticDao.findAllByOneMonth(driver);
            int sum = 0;
            for (DriverStatistic driverStatistic : driverStatistics)
                sum += driverStatistic.getHoursWorked();
            if (sum + hoursWorked <= 176) {
                driverList.add(driver);
            }
        }
        return driverList;
    }
}
