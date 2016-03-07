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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by infinity on 09.02.16.
 */
@Service
public class DriverServiceImpl implements DriverService{

    private static final Logger LOG = Logger.getLogger(DriverServiceImpl.class);

    private UserDao userDao;
    private DriverDao driverDao;
    private DriverStatisticDao driverStatisticDao;

    @Autowired
    public DriverServiceImpl(UserDao userDao, DriverDao driverDao,
                             DriverStatisticDao driverStatisticDao) {
        this.userDao = userDao;
        this.driverDao = driverDao;
        this.driverStatisticDao = driverStatisticDao;
    }

    /**Create driver and user bended with him in DB and set driver status on
     * rest in table of DriverStatuses
     * @param driver entity with correct fields
     * @throws ServiceException with status code USER_OR_DRIVER_ALREADY_EXIST
     * if driver or user with such identifier already in DB
     */
    @Override
    @Transactional
    public void addDriver(Driver driver) throws ServiceException {
        try {
            //check that we have no users or drivers with such identifier in DB
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
            }else {
                throw new ServiceException("User or Driver with such identifier exist", ServiceStatusCode.USER_OR_DRIVER_ALREADY_EXIST);
            }
        }catch (DaoException e){
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    /** Update driver which do not have order and exist in db
     * @param driver entity with filling fields
     * @throws ServiceException with status code DRIVER_ASSIGNED_ORDER if driver has
     * an order and we can not change it, DRIVER_NOT_FOUND if driver do not exist in db
     */
    @Override
    @Transactional
    public void updateDrive(Driver driver) throws ServiceException {
        try {
            //check is driver in db
            Driver driverElement = driverDao.findUniqueByNumber(driver.getNumber());
            if (driverElement != null && driverElement.getOrder() == null) {
                driverElement.setFirstName(driver.getFirstName());
                driverElement.setLastName(driver.getLastName());
                driverDao.update(driverElement);
            }
            if (driverElement == null) {
                throw new ServiceException("Driver not found", ServiceStatusCode.DRIVER_NOT_FOUND);
            }
            if (driverElement.getOrder() != null) {
                throw new ServiceException("Driver has an order", ServiceStatusCode.DRIVER_ASSIGNED_ORDER);
            }
        }catch (DaoException e){
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    /**Delete driver which do not have order and exist in db
     * @param number of driver we want to delete
     * @throws ServiceException with status code DRIVER_ASSIGNED_ORDER if driver has
     * an order and we can not change it, DRIVER_NOT_FOUND if driver do not exist in db
     */
    @Override
    @Transactional
    public void deleteDriver(int number) throws ServiceException {
        try {
            Driver driver = driverDao.findUniqueByNumber(number);
            if (driver != null && driver.getOrder() == null) {
                User user = driver.getUser();
                driverDao.delete(driver);
                userDao.delete(user);
            }
            if (driver == null){
                throw new ServiceException("Driver not found", ServiceStatusCode.DRIVER_NOT_FOUND);
            }
            if (driver.getOrder() != null){
                throw new ServiceException("Driver has an order", ServiceStatusCode.DRIVER_ASSIGNED_ORDER);
            }
        }catch (DaoException e){
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    /** Return driver from db by personal number
     * @param number of driver
     * @return
     * @throws ServiceException with status code DRIVER_NOT_FOUND if driver do not exist in db
     */
    @Override
    public Driver getDriverByPersonalNumber(int number) throws ServiceException {
        try {
            Driver driver = driverDao.findUniqueByNumber(number);
            if (driver == null){
                throw new ServiceException("Driver not found", ServiceStatusCode.DRIVER_NOT_FOUND);
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
    public List<Driver> findAllDriversByOffset(int offset, int limit) throws ServiceException {
        try {
            return driverDao.findAllByOffset(offset,limit);
        }catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    /** Return map of drivers which do not have order right now and
     * hours of work in this month with duration of order <= 176 hours per month
     * @param hoursWorked duration of current order
     * @return map with driver which do not have order and hours of worked <= 176
     * @throws ServiceException
     */
    @Override
    public Map<Driver,Integer> findAllAvailableDrivers(int hoursWorked) throws ServiceException {
        try {
            //get all free drivers
            List<Driver> drivers = driverDao.findAllFreeDrivers();
            Map<Driver,Integer> driverHoursList = new HashMap<>();
            for (Driver driver : drivers) {
                //count hours of work per month for every free driver
                List<DriverStatistic> driverStatistics = driverStatisticDao.findAllByOneMonth(driver);
                int sum = 0;
                for (DriverStatistic driverStatistic : driverStatistics)
                    sum += driverStatistic.getHoursWorked();
                if (sum + hoursWorked <= 176) {
                    //if hours <= 176 put driver and count hours in map
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
