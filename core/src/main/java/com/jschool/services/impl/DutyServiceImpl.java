package com.jschool.services.impl;

import com.jschool.CustomTransaction;
import com.jschool.TransactionManager;
import com.jschool.dao.api.DriverDao;
import com.jschool.dao.api.DriverStatisticDao;
import com.jschool.dao.api.DriverStatusLogDao;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.Driver;
import com.jschool.entities.DriverStatistic;
import com.jschool.entities.DriverStatus;
import com.jschool.entities.DriverStatusLog;
import com.jschool.services.api.DutyService;
import com.jschool.services.api.exception.ServiceException;
import com.jschool.services.api.exception.ServiceStatusCode;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Created by infinity on 13.02.16.
 */
public class DutyServiceImpl implements DutyService {

    private static final Logger LOG = Logger.getLogger(DutyServiceImpl.class);

    private DriverDao driverDao;
    private DriverStatusLogDao driverStatusLogDao;
    private DriverStatisticDao driverStatisticDao;
    private TransactionManager transactionManager;

    public DutyServiceImpl(DriverDao driverDao, DriverStatusLogDao driverStatusLogDao,
                           DriverStatisticDao driverStatisticDao, TransactionManager transactionManager) {
        this.driverDao = driverDao;
        this.driverStatusLogDao = driverStatusLogDao;
        this.driverStatisticDao = driverStatisticDao;
        this.transactionManager = transactionManager;
    }

    @Override
    public void loginDriverByNumber(int number, DriverStatus dutyStatus) throws ServiceException {
        setDriverStatus(number,dutyStatus);
    }

    @Override
    public void changeDriverDutyStatusByNumber(int number, DriverStatus dutyStatus) throws ServiceException {
        setDriverStatus(number,dutyStatus);
    }

    @Override
    public void logoutDriverByNumber(int number) throws ServiceException {
        setDriverStatus(number, DriverStatus.rest);
    }

    private void setDriverStatus(int number, DriverStatus dutyStatus) throws ServiceException {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            Driver driver = driverDao.findUniqueByNumber(number);
            if (driver != null){
                DriverStatusLog statusLog = driverStatusLogDao.findLastStatus(driver);
                if (statusLog.getStatus() != dutyStatus){
                    if (statusLog.getStatus() == DriverStatus.driving){
                        long diff = new Date().getTime() - statusLog.getTimestamp().getTime();
                        int diffHours = (int) (diff / (60 * 60 * 1000));
                        DriverStatistic driverStatistic = new DriverStatistic();
                        driverStatistic.setTimestamp(new Date());
                        driverStatistic.setHoursWorked(diffHours);
                        driverStatistic.setDriver(driver);
                        driverStatisticDao.create(driverStatistic);
                    }else {
                        DriverStatusLog driverStatusLog = new DriverStatusLog();
                        driverStatusLog.setStatus(dutyStatus);
                        driverStatusLog.setTimestamp(new Date());
                        driverStatusLog.setDriver(driver);
                        driverStatusLogDao.create(driverStatusLog);
                    }
                }
                ct.commit();
            }else {
                throw new ServiceException("Driver not found", ServiceStatusCode.NOT_FOUND);
            }
        }catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }finally {
            ct.rollbackIfActive();
        }
    }
}
