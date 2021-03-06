package com.jschool.services.impl;

import com.jschool.dao.api.CityDao;
import com.jschool.dao.api.TruckDao;
import com.jschool.dao.api.TruckStatisticDao;
import com.jschool.entities.City;
import com.jschool.entities.Order;
import com.jschool.entities.Truck;
import com.jschool.services.api.TruckService;
import com.jschool.services.api.exception.ServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by infinity on 24.02.16.
 */
public class TruckServiceImplTest {

    private TruckDao truckDaoMoc;
    private CityDao cityDao;
    private TruckStatisticDao truckStatisticDao;
    private TruckService truckService;

    @Before
    public void setUp() throws Exception {
        truckDaoMoc = Mockito.mock(TruckDao.class);
        cityDao = Mockito.mock(CityDao.class);
        truckStatisticDao = Mockito.mock(TruckStatisticDao.class);
        truckService = new TruckServiceImpl(truckDaoMoc,cityDao,truckStatisticDao);
    }

    private Truck getTruckForTest(){
        Truck truck = new Truck();
        truck.setNumber("ER45678");
        truck.setCapacity(50);
        truck.setShiftSize(2);
        truck.setRepairState(true);
        truck.setCity(getCityForTest());
        return truck;
    }

    private City getCityForTest(){
        City city = new City();
        city.setName("Orel");
        return city;
    }

    @Test
    public void testAddTruck() throws Exception {
        Mockito.when(truckDaoMoc.findUniqueByNumber(getTruckForTest().getNumber())).thenReturn(null);
        Mockito.when(cityDao.findUniqueByName(getTruckForTest().getCity().getName())).thenReturn(getCityForTest());
        truckService.addTruck(getTruckForTest());
    }

    @Test(expected = ServiceException.class)
    public void testAddTruckTruckExist() throws Exception {
        Mockito.when(truckDaoMoc.findUniqueByNumber(getTruckForTest().getNumber())).thenReturn(getTruckForTest());
        truckService.addTruck(getTruckForTest());
    }

    @Test
    public void testUpdateTruck() throws Exception {
        Mockito.when(truckDaoMoc.findUniqueByNumber(getTruckForTest().getNumber())).thenReturn(getTruckForTest());
        Mockito.when(cityDao.findUniqueByName(getTruckForTest().getCity().getName())).thenReturn(getCityForTest());
        truckService.updateTruck(getTruckForTest());
    }

    @Test(expected = ServiceException.class)
    public void testUpdateTruckNotFound() throws Exception {
        Mockito.when(truckDaoMoc.findUniqueByNumber(getTruckForTest().getNumber())).thenReturn(null);
        truckService.updateTruck(getTruckForTest());
    }

    @Test(expected = ServiceException.class)
    public void testUpdateTruckHasOrder() throws Exception {
        Truck truck = getTruckForTest();
        Order order = new Order();
        order.setNumber(2323);
        truck.setOreder(order);
        Mockito.when(truckDaoMoc.findUniqueByNumber(getTruckForTest().getNumber())).thenReturn(truck);
        truckService.updateTruck(truck);
    }

    @Test
    public void testDeleteTruck() throws Exception {
        Mockito.when(truckDaoMoc.findUniqueByNumber(getTruckForTest().getNumber())).thenReturn(getTruckForTest());
        truckService.deleteTruck(getTruckForTest().getNumber());
    }

    @Test(expected = ServiceException.class)
    public void testDeleteTruckNotFound() throws Exception {
        Mockito.when(truckDaoMoc.findUniqueByNumber(getTruckForTest().getNumber())).thenReturn(null);
        truckService.deleteTruck(getTruckForTest().getNumber());
    }

    @Test(expected = ServiceException.class)
    public void testDeleteTruckHasOrder() throws Exception {
        Truck truck = getTruckForTest();
        Order order = new Order();
        order.setNumber(2323);
        truck.setOreder(order);
        Mockito.when(truckDaoMoc.findUniqueByNumber(getTruckForTest().getNumber())).thenReturn(truck);
        truckService.deleteTruck(truck.getNumber());
    }

    @Test
    public void testFindAllTrucks() throws Exception {
        Mockito.when(truckDaoMoc.findAll()).thenReturn(Arrays.asList(getTruckForTest()));
        List<Truck> trucks = truckService.findAllTrucks();
        Assert.assertEquals(trucks.size(),1);
    }


    @Test
    public void testFindAllAvailableTrucksByMinCapacity() throws Exception {
        Mockito.when(truckDaoMoc.findAllFreeByStateAndGreaterThanCapacity(true,40,"Orel")).thenReturn(Arrays.asList(getTruckForTest()));
        Mockito.when(cityDao.findUniqueByName(getTruckForTest().getCity().getName())).thenReturn(getCityForTest());
        List<Truck> trucks = truckService.findAllAvailableTrucksByMinCapacity(40,"Orel");
        Assert.assertEquals(trucks.size(),1);
    }
}