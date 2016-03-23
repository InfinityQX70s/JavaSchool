package com.jschool.services.impl;

import com.jschool.dao.api.DriverDao;
import com.jschool.dao.api.DriverStatisticDao;
import com.jschool.dao.api.DriverStatusLogDao;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.Driver;
import com.jschool.entities.DriverStatus;
import com.jschool.entities.DriverStatusLog;
import com.jschool.entities.User;
import com.jschool.services.api.DutyService;
import com.jschool.services.api.exception.ServiceException;
import com.jschool.services.api.exception.ServiceStatusCode;
import org.junit.Assert;
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
    private DutyService dutyService;

    @Before
    public void setUp() throws Exception {
        driverDaoMoc = Mockito.mock(DriverDao.class);
        driverStatisticDaoMoc = Mockito.mock(DriverStatisticDao.class);
        driverStatusLogDaoMoc = Mockito.mock(DriverStatusLogDao.class);
        dutyService = new DutyServiceImpl(driverDaoMoc, driverStatusLogDaoMoc, driverStatisticDaoMoc);
    }

    private Driver getDriverForTest() {
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
    public void testChangeDriverStatusOnSame() {
        Driver driver = getDriverForTest();
        DriverStatusLog driverStatusLog = new DriverStatusLog();
        driverStatusLog.setStatus(DriverStatus.rest);
        try {
            Mockito.when(driverDaoMoc.findUniqueByNumber(driver.getNumber())).thenReturn(driver);
            Mockito.when(driverStatusLogDaoMoc.findLastStatus(driver)).thenReturn(driverStatusLog);
            dutyService.changeDriverDutyStatusByNumber(driver.getNumber(), DriverStatus.rest);
        } catch (ServiceException e) {
            Assert.assertEquals(ServiceStatusCode.DRIVER_HAS_STAUS,e.getStatusCode());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = ServiceException.class)
    public void testChangeDriverStatusError() throws Exception {
        Mockito.when(driverDaoMoc.findUniqueByNumber(getDriverForTest().getNumber())).thenReturn(null);
        dutyService.changeDriverDutyStatusByNumber(getDriverForTest().getNumber(), DriverStatus.driving);
    }

}