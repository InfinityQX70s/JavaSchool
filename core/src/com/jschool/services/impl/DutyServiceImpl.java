package com.jschool.services.impl;

import com.jschool.CustomTransaction;
import com.jschool.TransactionManager;
import com.jschool.dao.api.DriverDao;
import com.jschool.dao.api.DriverStatisticDao;
import com.jschool.dao.api.DriverStatusLogDao;
import com.jschool.dao.api.UserDao;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.Driver;
import com.jschool.entities.DriverStatistic;
import com.jschool.entities.DriverStatus;
import com.jschool.entities.DriverStatusLog;
import com.jschool.services.api.DutyService;
import com.jschool.services.api.exception.ServiceExeption;

import java.util.Date;

/**
 * Created by infinity on 13.02.16.
 */
public class DutyServiceImpl implements DutyService {

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

    public void loginDriverByNumber(int number, DriverStatus dutyStatus) throws ServiceExeption {
        setDriverStatus(number,dutyStatus);
    }

    public void changeDriverDutyStatusByNumber(int number, DriverStatus dutyStatus) throws ServiceExeption {
        setDriverStatus(number,dutyStatus);
    }

    public void logoutDriverByNumber(int number) throws ServiceExeption {
        setDriverStatus(number, DriverStatus.rest);
    }

    private void setDriverStatus(int number, DriverStatus dutyStatus) throws ServiceExeption {
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
            }
            ct.commit();
        }catch (DaoException e) {
            throw new ServiceExeption(e);
        }finally {
            ct.rollbackIfActive();
        }
    }
}
