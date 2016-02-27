package com.jschool.services.impl;

import com.jschool.CustomTransaction;
import com.jschool.TransactionManager;
import com.jschool.dao.api.DriverDao;
import com.jschool.dao.api.DriverStatisticDao;
import com.jschool.dao.api.DriverStatusLogDao;
import com.jschool.entities.Driver;
import com.jschool.entities.DriverStatus;
import com.jschool.entities.DriverStatusLog;
import com.jschool.entities.User;
import com.jschool.services.api.DutyService;
import com.jschool.services.api.exception.ServiceException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by infinity on 25.02.16.
 */
public class DutyServiceImplTest {

    private DriverDao driverDaoMoc;
    private DriverStatusLogDao driverStatusLogDaoMoc;
    private DriverStatisticDao driverStatisticDaoMoc;
    private TransactionManager transactionManagerMoc;
    private DutyService dutyService;
    private CustomTransaction customTransactionMoc;

    @Before
    public void setUp() throws Exception {
        driverDaoMoc = Mockito.mock(DriverDao.class);
        driverStatisticDaoMoc = Mockito.mock(DriverStatisticDao.class);
        driverStatusLogDaoMoc = Mockito.mock(DriverStatusLogDao.class);
        transactionManagerMoc = Mockito.mock(TransactionManager.class);
        customTransactionMoc = Mockito.mock(CustomTransaction.class);
        Mockito.when(transactionManagerMoc.getTransaction()).thenReturn(customTransactionMoc);
        dutyService = new DutyServiceImpl(driverDaoMoc,driverStatusLogDaoMoc,driverStatisticDaoMoc,transactionManagerMoc);
    }

    private Driver getDriverForTest(){
        Driver driver = new Driver();
        driver.setNumber(Integer.parseInt("13"));
        driver.setFirstName("Ivan");
        driver.setLastName("Ivanov");
        return driver;
    }

    @Test
    public void testChangeDriverStatusFromDriving() throws Exception {
        Driver driver = getDriverForTest();
        DriverStatusLog driverStatusLog = new DriverStatusLog();
        driverStatusLog.setStatus(DriverStatus.driving);
        driverStatusLog.setTimestamp(new Date(System.currentTimeMillis() - (4 * 60 * 60 * 1000)));
        Mockito.when(driverDaoMoc.findUniqueByNumber(driver.getNumber())).thenReturn(driver);
        Mockito.when(driverStatusLogDaoMoc.findLastStatus(driver)).thenReturn(driverStatusLog);
        dutyService.changeDriverDutyStatusByNumber(driver.getNumber(), DriverStatus.rest);
    }

    @Test
    public void testChangeDriverStatusFromOther() throws Exception {
        Driver driver = getDriverForTest();
        DriverStatusLog driverStatusLog = new DriverStatusLog();
        driverStatusLog.setStatus(DriverStatus.rest);
        driverStatusLog.setTimestamp(new Date(System.currentTimeMillis() - (4 * 60 * 60 * 1000)));
        Mockito.when(driverDaoMoc.findUniqueByNumber(driver.getNumber())).thenReturn(driver);
        Mockito.when(driverStatusLogDaoMoc.findLastStatus(driver)).thenReturn(driverStatusLog);
        dutyService.changeDriverDutyStatusByNumber(driver.getNumber(), DriverStatus.shift);
    }

    @Test
    public void testChangeDriverStatusOnSame() throws Exception {
        Driver driver = getDriverForTest();
        DriverStatusLog driverStatusLog = new DriverStatusLog();
        driverStatusLog.setStatus(DriverStatus.rest);
        Mockito.when(driverDaoMoc.findUniqueByNumber(driver.getNumber())).thenReturn(driver);
        Mockito.when(driverStatusLogDaoMoc.findLastStatus(driver)).thenReturn(driverStatusLog);
        dutyService.changeDriverDutyStatusByNumber(driver.getNumber(), DriverStatus.rest);
    }

    @Test(expected = ServiceException.class)
    public void testChangeDriverStatusError() throws Exception {
        Mockito.when(driverDaoMoc.findUniqueByNumber(getDriverForTest().getNumber())).thenReturn(null);
        dutyService.changeDriverDutyStatusByNumber(getDriverForTest().getNumber(), DriverStatus.driving);
    }

}