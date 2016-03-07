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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by infinity on 13.02.16.
 */
@Service
public class DutyServiceImpl implements DutyService {

    private static final Logger LOG = Logger.getLogger(DutyServiceImpl.class);

    private DriverDao driverDao;
    private DriverStatusLogDao driverStatusLogDao;
    private DriverStatisticDao driverStatisticDao;

    @Autowired
    public DutyServiceImpl(DriverDao driverDao, DriverStatusLogDao driverStatusLogDao,
                           DriverStatisticDao driverStatisticDao) {
        this.driverDao = driverDao;
        this.driverStatusLogDao = driverStatusLogDao;
        this.driverStatisticDao = driverStatisticDao;
    }

    /** Login driver and change his status
     * @param number  personal number of driver in db
     * @param dutyStatus status of driver
     * @throws ServiceException
     */
    @Override
    public void loginDriverByNumber(int number, DriverStatus dutyStatus) throws ServiceException {
        setDriverStatus(number,dutyStatus);
    }

    /**Change driver status then he is on work
     * @param number driver personal number
     * @param dutyStatus his current status
     * @throws ServiceException
     */
    @Override
    public void changeDriverDutyStatusByNumber(int number, DriverStatus dutyStatus) throws ServiceException {
        setDriverStatus(number,dutyStatus);
    }

    /**Logout driver and set his curretn status to rest
     * @param number personal number of driver
     * @throws ServiceException
     */
    @Override
    public void logoutDriverByNumber(int number) throws ServiceException {
        setDriverStatus(number, DriverStatus.rest);
    }

    /** Util method set driver status
     * @param number of driver
     * @param dutyStatus driver's current status
     * @throws ServiceException with status code DRIVER_NOT_FOUND if driver not exist in db
     */
    @Transactional
    private void setDriverStatus(int number, DriverStatus dutyStatus) throws ServiceException {
        try {
            Driver driver = driverDao.findUniqueByNumber(number);
            if (driver != null){
                // find last status of driver and check if it is same as current then do nothing
                DriverStatusLog statusLog = driverStatusLogDao.findLastStatus(driver);
                if (statusLog.getStatus() != dutyStatus){
                    //check if last status is "driving" then set new status and count hours of worked and
                    // insert this information in table driver statistic with current date and hours worked
                    if (statusLog.getStatus() == DriverStatus.driving){
                        long diff = new Date().getTime() - statusLog.getTimestamp().getTime();
                        int diffHours = (int) (diff / (60 * 60 * 1000));
                        DriverStatistic driverStatistic = new DriverStatistic();
                        driverStatistic.setTimestamp(new Date());
                        driverStatistic.setHoursWorked(diffHours);
                        driverStatistic.setDriver(driver);
                        driverStatisticDao.create(driverStatistic);
                    }else {                                        //insert status in db for current driver
                        DriverStatusLog driverStatusLog = new DriverStatusLog();
                        driverStatusLog.setStatus(dutyStatus);
                        driverStatusLog.setTimestamp(new Date());
                        driverStatusLog.setDriver(driver);
                        driverStatusLogDao.create(driverStatusLog);
                    }
                }
            }else {
                throw new ServiceException("Driver not found", ServiceStatusCode.DRIVER_NOT_FOUND);
            }
        }catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }
}
