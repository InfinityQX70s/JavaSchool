package com.jschool.services.impl;

import com.jschool.CustomTransaction;
import com.jschool.TransactionManager;
import com.jschool.dao.api.*;
import com.jschool.entities.*;
import com.jschool.services.api.OrderAndCargoService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by infinity on 25.02.16.
 */
public class OrderAndCargoServiceImplTest {

    private OrdersDao ordersDaoMoc;
    private DriverDao driverDaoMoc;
    private CargoDao cargoDaoMoc;
    private RoutePointDao routePointDaoMoc;
    private CityDao cityDaoMoc;
    private TransactionManager transactionManagerMoc;
    private CustomTransaction customTransactionMoc;
    private OrderAndCargoService orderAndCargoService;


    @Before
    public void setUp() throws Exception {
        ordersDaoMoc = Mockito.mock(OrdersDao.class);
        driverDaoMoc = Mockito.mock(DriverDao.class);
        cargoDaoMoc = Mockito.mock(CargoDao.class);
        routePointDaoMoc = Mockito.mock(RoutePointDao.class);
        cityDaoMoc = Mockito.mock(CityDao.class);
        transactionManagerMoc = Mockito.mock(TransactionManager.class);
        customTransactionMoc = Mockito.mock(CustomTransaction.class);
        Mockito.when(transactionManagerMoc.getTransaction()).thenReturn(customTransactionMoc);
        orderAndCargoService = new OrderAndCargoServiceImpl(ordersDaoMoc,driverDaoMoc,cargoDaoMoc,
                routePointDaoMoc,cityDaoMoc,transactionManagerMoc);

    }

    private Order getOrderForTest() {
        Order order = new Order();
        order.setNumber(13);
        order.setDoneState(false);
        Truck truck = new Truck();
        truck.setNumber("EW23456");
        truck.setCapacity(50);
        truck.setShiftSize(1);
        List<Driver> drivers = new ArrayList<>();
        Driver driver = new Driver();
        driver.setNumber(13);
        driver.setFirstName("Ivan");
        driver.setLastName("Ivanov");
        drivers.add(driver);
        order.setTruck(truck);
        order.setDrivers(drivers);
        return order;
    }


    private List<Cargo> getCargoFroTest(){
        List<Cargo> cargos = new ArrayList<>();
        Cargo cargo = new Cargo();
        cargo.setNumber(13);
        cargo.setName("Bricks");
        cargo.setWeight(50);

        City pickCity = new City();
        pickCity.setName("Орел");

        City unloadCity = new City();
        unloadCity.setName("Москва");

        RoutePoint pickRoute = new RoutePoint();
        pickRoute.setPoint(0);
        pickRoute.setCity(pickCity);

        RoutePoint unloadRoute = new RoutePoint();
        unloadRoute.setPoint(0);
        unloadRoute.setCity(unloadCity);

        cargo.setPickup(pickRoute);
        cargo.setUnload(unloadRoute);
        cargos.add(cargo);
        return cargos;
    }
    @Test
    public void testAddOrder() throws Exception {
        Mockito.when(ordersDaoMoc.findUniqueByNumber(13)).thenReturn(null);
        Mockito.when(cargoDaoMoc.findUniqueByNumber(13)).thenReturn(null);
        City pickCity = new City();
        pickCity.setName("Орел");
        Mockito.when(cityDaoMoc.findUniqueByName("Орел")).thenReturn(pickCity);
        City unloadCity = new City();
        unloadCity.setName("Москва");
        Mockito.when(cityDaoMoc.findUniqueByName("Москва")).thenReturn(unloadCity);
        orderAndCargoService.addOrder(getOrderForTest(),getCargoFroTest());
    }

}