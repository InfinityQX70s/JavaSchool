package com.jschool.services.impl;

import com.jschool.dao.api.*;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.*;
import com.jschool.services.api.DriverService;
import com.jschool.services.api.exception.ServiceException;
import com.jschool.services.api.exception.ServiceStatusCode;
import com.jschool.utils.MailUtil;
import com.jschool.utils.SmsUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.*;

/**
 * Created by infinity on 09.02.16.
 */
@Service
public class DriverServiceImpl implements DriverService {

    private static final Logger LOG = Logger.getLogger(DriverServiceImpl.class);

    private UserDao userDao;
    private CityDao cityDao;
    private DriverDao driverDao;
    private DriverStatisticDao driverStatisticDao;
    private DriverAuthCodeDao driverAuthCodeDao;
    private DriverStatusLogDao driverStatusLogDao;
    private SmsUtil smsUtil;
    private MailUtil mailUtil;

    @Autowired
    public DriverServiceImpl(UserDao userDao, DriverDao driverDao,
                             DriverStatisticDao driverStatisticDao, CityDao cityDao,
                             DriverAuthCodeDao driverAuthCodeDao, DriverStatusLogDao driverStatusLogDao, SmsUtil smsUtil, MailUtil mailUtil) {
        this.userDao = userDao;
        this.driverDao = driverDao;
        this.driverStatisticDao = driverStatisticDao;
        this.driverAuthCodeDao = driverAuthCodeDao;
        this.driverStatusLogDao = driverStatusLogDao;
        this.cityDao = cityDao;
        this.smsUtil = smsUtil;
        this.mailUtil = mailUtil;
    }

    /**
     * Create driver and user bended with him in DB and set driver status on
     * rest in table of DriverStatuses
     *
     * @param driver entity with correct fields
     * @throws ServiceException with status code USER_OR_DRIVER_ALREADY_EXIST
     *                          if driver or user with such identifier already in DB
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void addDriver(Driver driver) throws ServiceException {
        try {
            //check that we have no users or drivers with such identifier in DB
            if (userDao.findUniqueByEmail(driver.getUser().getEmail()) == null
                    && driverDao.findUniqueByNumber(driver.getNumber()) == null) {
                City city = cityDao.findUniqueByName(driver.getCity().getName());
                if (city != null) {
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
                    driver.setCity(city);
                    driverDao.create(driver);
                } else {
                    throw new ServiceException("City with such name not found", ServiceStatusCode.CITY_NOT_FOUND);
                }
            } else {
                throw new ServiceException("User or Driver with such identifier exist", ServiceStatusCode.USER_OR_DRIVER_ALREADY_EXIST);
            }
        } catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    /**
     * Update driver which do not have order and exist in db
     *
     * @param driver entity with filling fields
     * @throws ServiceException with status code DRIVER_ASSIGNED_ORDER if driver has
     *                          an order and we can not change it, DRIVER_NOT_FOUND if driver do not exist in db
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void updateDrive(Driver driver) throws ServiceException {
        try {
            //check is driver in db
            Driver driverElement = driverDao.findUniqueByNumber(driver.getNumber());
            User user = userDao.findUniqueByEmail(driver.getUser().getEmail());
            City city = cityDao.findUniqueByName(driver.getCity().getName());
            if (driverElement != null && driverElement.getOrder() == null && (user == null || driverElement.getUser().getEmail().equals(driver.getUser().getEmail()))) {
                if (city != null) {
                    driverElement.setFirstName(driver.getFirstName());
                    driverElement.setLastName(driver.getLastName());
                    driverElement.getUser().setEmail(driver.getUser().getEmail());
                    driverElement.getUser().setPassword(DigestUtils.md5Hex(driver.getUser().getEmail()));
                    driverElement.setCity(city);
                    driverDao.update(driverElement);
                } else {
                    throw new ServiceException("City with such name not found", ServiceStatusCode.CITY_NOT_FOUND);
                }
            } else if (driverElement == null) {
                throw new ServiceException("Driver not found", ServiceStatusCode.DRIVER_NOT_FOUND);
            } else if (driverElement.getOrder() != null) {
                throw new ServiceException("Driver has an order", ServiceStatusCode.DRIVER_ASSIGNED_ORDER);
            } else if (user != null) {
                throw new ServiceException("User already exist", ServiceStatusCode.USER_ALREADY_EXIST);
            }
        } catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    /**
     * Delete driver which do not have order and exist in db
     *
     * @param number of driver we want to delete
     * @throws ServiceException with status code DRIVER_ASSIGNED_ORDER if driver has
     *                          an order and we can not change it, DRIVER_NOT_FOUND if driver do not exist in db
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void deleteDriver(int number) throws ServiceException {
        try {
            Driver driver = driverDao.findUniqueByNumber(number);
            if (driver != null && driver.getOrder() == null) {
                User user = driver.getUser();
                driverDao.delete(driver);
                userDao.delete(user);
            } else if (driver == null) {
                throw new ServiceException("Driver not found", ServiceStatusCode.DRIVER_NOT_FOUND);
            } else if (driver.getOrder() != null) {
                throw new ServiceException("Driver has an order", ServiceStatusCode.DRIVER_ASSIGNED_ORDER);
            }
        } catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    /**
     * Return driver from db by personal number
     *
     * @param number of driver
     * @return
     * @throws ServiceException with status code DRIVER_NOT_FOUND if driver do not exist in db
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public Driver getDriverByPersonalNumber(int number) throws ServiceException {
        try {
            Driver driver = driverDao.findUniqueByNumber(number);
            if (driver == null) {
                throw new ServiceException("Driver not found", ServiceStatusCode.DRIVER_NOT_FOUND);
            }
            return driver;
        } catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public List<Driver> findAllDrivers() throws ServiceException {
        try {
            return driverDao.findAll();
        } catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public List<Driver> findAllDriversByOffset(int offset, int limit) throws ServiceException {
        try {
            return driverDao.findAllByOffset(offset, limit);
        } catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    /**
     * Return map of drivers which do not have order right now and
     * hours of work in this month with duration of order <= 176 hours per month
     *
     * @param hoursWorked duration of current order
     * @return map with driver which do not have order and hours of worked <= 176
     * @throws ServiceException
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public Map<Driver, Integer> findAllAvailableDrivers(int hoursWorked, String city) throws ServiceException {
        try {
            //get all free drivers
            List<Driver> drivers = driverDao.findAllFreeDrivers(city);
            City element = cityDao.findUniqueByName(city);
            if (element != null) {
                Map<Driver, Integer> driverHoursList = new HashMap<>();
                for (Driver driver : drivers) {
                    //count hours of work per month for every free driver
                    List<DriverStatistic> driverStatistics = driverStatisticDao.findAllByOneMonth(driver);
                    int sum = 0;
                    for (DriverStatistic driverStatistic : driverStatistics)
                        sum += driverStatistic.getHoursWorked();
                    if (sum + hoursWorked <= 176) {
                        //if hours <= 176 put driver and count hours in map
                        driverHoursList.put(driver, sum);
                    }
                }
                return driverHoursList;
            } else
                throw new ServiceException("City with such name not found", ServiceStatusCode.CITY_NOT_FOUND);
        } catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public List<DriverStatistic> findDriversMonthStatistic(Driver driver) throws ServiceException {
        try {
            return driverStatisticDao.findAllByOneMonth(driver);
        } catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public DriverStatusLog findLastStatus(Driver driver) throws ServiceException {
        try {
            return driverStatusLogDao.findLastStatus(driver);
        } catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }


    @Override
    public void sendOrderSms(Driver driver, int orderNumber) throws ServiceException {
        try {
            if (driver != null) {
                smsUtil.sendSms(driver.getPhoneNumber(), "You successfully assign at order " + orderNumber);
            } else
                throw new ServiceException("Driver not found", ServiceStatusCode.DRIVER_NOT_FOUND);
        } catch (IOException | InterruptedException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Problem with sending sms", e, ServiceStatusCode.TWILIO_EXCEPTION);
        }
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void sendDriverVerifyCode(int number) throws ServiceException {
        try {
            Driver driver = driverDao.findUniqueByNumber(number);
            if (driver != null) {
                Random random = new Random();
                int code = 100000 + random.nextInt(900000);
                smsUtil.sendSms(driver.getPhoneNumber(), "Your verification code: " + code);
                DriverAuthCode driverAuthCode = new DriverAuthCode();
                driverAuthCode.setCode(code);
                driverAuthCode.setTimestamp(new Date());
                driverAuthCode.setDriver(driver);
                driverAuthCodeDao.create(driverAuthCode);
            } else
                throw new ServiceException("Driver not found", ServiceStatusCode.DRIVER_NOT_FOUND);
        } catch (IOException | InterruptedException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Problem with sending sms", e, ServiceStatusCode.TWILIO_EXCEPTION);
        } catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    @Override
    public void sendInvitatinMail(String driverEmail) throws ServiceException {
        try {
            mailUtil.sendInvitationMail(driverEmail);
        } catch (MessagingException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Problem with sending email", e, ServiceStatusCode.MAIL_EXCEPTION);
        }
    }

    @Override
    @Transactional
    public void sendOrderInfoMail(Driver driver) throws ServiceException {
        try {
            String token = DigestUtils.md5Hex(driver.getOrder().getNumber() + driver.getUser().getEmail());
            driver.setToken(token);
            driverDao.update(driver);
            mailUtil.sendShareMail(driver);
        } catch (MessagingException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Problem with sending email", e, ServiceStatusCode.MAIL_EXCEPTION);
        } catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }
}
