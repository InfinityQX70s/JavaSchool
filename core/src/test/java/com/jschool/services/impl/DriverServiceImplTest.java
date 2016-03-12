package com.jschool.services.impl;

import com.jschool.dao.api.DriverDao;
import com.jschool.dao.api.DriverStatisticDao;
import com.jschool.dao.api.UserDao;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.Driver;
import com.jschool.entities.DriverStatistic;
import com.jschool.entities.Order;
import com.jschool.entities.User;
import com.jschool.services.api.DriverService;
import com.jschool.services.api.exception.ServiceException;
import com.jschool.services.api.exception.ServiceStatusCode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.util.*;


/**
 * Created by infinity on 24.02.16.
 */
public class DriverServiceImplTest {

    private UserDao userDaoMoc;
    private DriverDao driverDaoMoc;
    private DriverStatisticDao driverStatisticDaoMoc;
    private DriverService driverService;

    @Before
    public void setUp() throws Exception {
        userDaoMoc = Mockito.mock(UserDao.class);
        driverDaoMoc = Mockito.mock(DriverDao.class);
        driverStatisticDaoMoc = Mockito.mock(DriverStatisticDao.class);
        driverService = new DriverServiceImpl(userDaoMoc,driverDaoMoc,driverStatisticDaoMoc);
    }

    private Driver getDriverForTest(){
        User user = new User();
        user.setEmail("Test@gmail.com");
        user.setPassword("Test@gmail.com");
        user.setRole(true);
        Driver driver = new Driver();
        driver.setNumber(Integer.parseInt("13"));
        driver.setFirstName("Ivan");
        driver.setLastName("Ivanov");
        driver.setUser(user);
        return driver;
    }


    private List<Driver> getDriversList(){
        List<Driver> drivers = new ArrayList<>();
        Driver driver = new Driver();
        driver.setNumber(Integer.parseInt("13"));
        driver.setFirstName("Ivan");
        driver.setLastName("Ivanov");
        drivers.add(driver);
        Driver driver1 = new Driver();
        driver1.setNumber(Integer.parseInt("14"));
        driver1.setFirstName("I");
        driver1.setLastName("vanov");
        drivers.add(driver1);
        return drivers;
    }

    @Test
    public void testAddDriver() throws Exception {
        Mockito.when(userDaoMoc.findUniqueByEmail("Test@gmail.com")).thenReturn(null);
        Mockito.when(driverDaoMoc.findUniqueByNumber(13)).thenReturn(null);
        driverService.addDriver(getDriverForTest());
    }

    @Test
    public void testAddDriverUserExist(){
        try {
            Mockito.when(userDaoMoc.findUniqueByEmail("Test@gmail.com")).thenReturn(getDriverForTest().getUser());
            driverService.addDriver(getDriverForTest());
        } catch (ServiceException e) {
            Assert.assertEquals(ServiceStatusCode.USER_OR_DRIVER_ALREADY_EXIST,e.getStatusCode());
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testAddDriverDriverExist() {
        try {
            Mockito.when(userDaoMoc.findUniqueByEmail("Test@gmail.com")).thenReturn(null);
            Mockito.when(driverDaoMoc.findUniqueByNumber(13)).thenReturn(getDriverForTest());
            driverService.addDriver(getDriverForTest());
        } catch (ServiceException e) {
            Assert.assertEquals(ServiceStatusCode.USER_OR_DRIVER_ALREADY_EXIST,e.getStatusCode());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testUpdateDrive() throws Exception {
        Mockito.when(driverDaoMoc.findUniqueByNumber(13)).thenReturn(getDriverForTest());
        driverService.updateDrive(getDriverForTest());
    }


    @Test
    public void testUpdateDriveNotFound() {
        try {
            Mockito.when(driverDaoMoc.findUniqueByNumber(13)).thenReturn(null);
            driverService.updateDrive(getDriverForTest());
        } catch (ServiceException e) {
            Assert.assertEquals(ServiceStatusCode.DRIVER_NOT_FOUND,e.getStatusCode());
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testUpdateDriveHasOrder(){
        try {
            Driver driver = getDriverForTest();
            Order order = new Order();
            order.setNumber(2323);
            driver.setOrder(order);
            Mockito.when(driverDaoMoc.findUniqueByNumber(13)).thenReturn(driver);
            driverService.updateDrive(driver);
        } catch (ServiceException e) {
            Assert.assertEquals(ServiceStatusCode.DRIVER_ASSIGNED_ORDER,e.getStatusCode());
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testDeleteDrive() throws Exception {
        Mockito.when(driverDaoMoc.findUniqueByNumber(13)).thenReturn(getDriverForTest());
        driverService.deleteDriver(getDriverForTest().getNumber());

    }

    @Test
    public void testDeleteDriveNotFound(){
        try {
            Mockito.when(driverDaoMoc.findUniqueByNumber(13)).thenReturn(null);
            driverService.deleteDriver(getDriverForTest().getNumber());
        } catch (ServiceException e) {
            Assert.assertEquals(ServiceStatusCode.DRIVER_NOT_FOUND,e.getStatusCode());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteDriveHasOrder() throws Exception {
        try {
            Driver driver = getDriverForTest();
            Order order = new Order();
            order.setNumber(2323);
            driver.setOrder(order);
            Mockito.when(driverDaoMoc.findUniqueByNumber(13)).thenReturn(driver);
            driverService.deleteDriver(driver.getNumber());
        } catch (ServiceException e) {
            Assert.assertEquals(ServiceStatusCode.DRIVER_ASSIGNED_ORDER,e.getStatusCode());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindAllAvailableDrivers() throws Exception {
        List<DriverStatistic> driverStatistics = new ArrayList<>();
        DriverStatistic driverStatistic = new DriverStatistic();
        driverStatistic.setHoursWorked(30);
        driverStatistic.setTimestamp(new Date());
        driverStatistics.add(driverStatistic);

        DriverStatistic driverStatistic1 = new DriverStatistic();
        driverStatistic1.setHoursWorked(40);
        driverStatistic1.setTimestamp(new Date());
        driverStatistics.add(driverStatistic1);
        Driver driver = getDriverForTest();
        Mockito.when(driverDaoMoc.findAllFreeDrivers()).thenReturn(Arrays.asList(driver));
        Mockito.when(driverStatisticDaoMoc.findAllByOneMonth(driver)).thenReturn(driverStatistics);
        Map<Driver,Integer> map = driverService.findAllAvailableDrivers(30);
        Assert.assertEquals(map.get(driver).intValue(),70);
        map = driverService.findAllAvailableDrivers(120);
        Assert.assertEquals(map.isEmpty(),true);


    }
}