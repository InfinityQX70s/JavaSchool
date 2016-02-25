package com.jschool.services.impl;

import com.jschool.CustomTransaction;
import com.jschool.TransactionManager;
import com.jschool.dao.api.DriverDao;
import com.jschool.dao.api.DriverStatisticDao;
import com.jschool.dao.api.UserDao;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.*;
import com.jschool.services.api.DriverService;
import com.jschool.services.api.exception.ServiceException;
import com.jschool.services.api.exception.ServiceStatusCode;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by infinity on 09.02.16.
 */
public class DriverServiceImpl implements DriverService{

    private static final Logger LOG = Logger.getLogger(DriverServiceImpl.class);

    private UserDao userDao;
    private DriverDao driverDao;
    private DriverStatisticDao driverStatisticDao;
    private TransactionManager transactionManager;

    public DriverServiceImpl(UserDao userDao, DriverDao driverDao,
                             DriverStatisticDao driverStatisticDao,
                             TransactionManager transactionManager) {
        this.userDao = userDao;
        this.driverDao = driverDao;
        this.driverStatisticDao = driverStatisticDao;
        this.transactionManager = transactionManager;
    }

    @Override
    public void addDriver(Driver driver) throws ServiceException {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            if (userDao.findUniqueByEmail(driver.getUser().getEmail()) == null
                    && driverDao.findUniqueByNumber(driver.getNumber()) == null) {
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
            }else {
                throw new ServiceException("User or Driver with such identifier exist", ServiceStatusCode.ALREADY_EXIST);
            }
        }catch (DaoException e){
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }finally {
            ct.rollbackIfActive();
        }
    }

    @Override
    public void updateDrive(Driver driver) throws ServiceException {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            Driver driverElement = driverDao.findUniqueByNumber(driver.getNumber());
            if (driverElement != null && driverElement.getOrder() == null) {
                driverElement.setFirstName(driver.getFirstName());
                driverElement.setLastName(driver.getLastName());
                driverDao.update(driverElement);
                ct.commit();
            }
            if (driverElement == null) {
                throw new ServiceException("Driver not found", ServiceStatusCode.NOT_FOUND);
            }
            if (driverElement.getOrder() != null) {
                throw new ServiceException("Driver has an order", ServiceStatusCode.ASSIGNED_ORDER);
            }
        }catch (DaoException e){
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }finally {
            ct.rollbackIfActive();
        }
    }

    @Override
    public void deleteDriver(int number) throws ServiceException {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            Driver driver = driverDao.findUniqueByNumber(number);
            if (driver != null && driver.getOrder() == null) {
                User user = driver.getUser();
                driverDao.delete(driver);
                userDao.delete(user);
                ct.commit();
            }
            if (driver == null){
                throw new ServiceException("Driver not found", ServiceStatusCode.NOT_FOUND);
            }
            if (driver.getOrder() != null){
                throw new ServiceException("Driver has an order", ServiceStatusCode.ASSIGNED_ORDER);
            }
        }catch (DaoException e){
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }finally {
            ct.rollbackIfActive();
        }
    }

    @Override
    public Driver getDriverByPersonalNumber(int number) throws ServiceException {
        try {
            Driver driver = driverDao.findUniqueByNumber(number);
            if (driver == null){
                throw new ServiceException("Driver not found", ServiceStatusCode.NOT_FOUND);
            }
            return driver;
        }catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    @Override
    public List<Driver> findAllDrivers() throws ServiceException {
        try {
            return driverDao.findAll();
        }catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    @Override
    public Map<Driver,Integer> findAllAvailableDrivers(int hoursWorked) throws ServiceException {
        try {
            List<Driver> drivers = driverDao.findAllFreeDrivers();
            Map<Driver,Integer> driverHoursList = new HashMap<>();
            for (Driver driver : drivers) {
                List<DriverStatistic> driverStatistics = driverStatisticDao.findAllByOneMonth(driver);
                int sum = 0;
                for (DriverStatistic driverStatistic : driverStatistics)
                    sum += driverStatistic.getHoursWorked();
                if (sum + hoursWorked <= 176) {
                    driverHoursList.put(driver,sum);
                }
            }
            return driverHoursList;
        }catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }
}
