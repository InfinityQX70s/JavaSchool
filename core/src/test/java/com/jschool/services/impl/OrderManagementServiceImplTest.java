package com.jschool.services.impl;

import com.jschool.CustomTransaction;
import com.jschool.TransactionManager;
import com.jschool.dao.api.*;
import com.jschool.entities.*;
import com.jschool.services.api.OrderAndCargoService;
import com.jschool.services.api.OrderManagementService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by infinity on 25.02.16.
 */
public class OrderManagementServiceImplTest {

    private OrdersDao ordersDaoMoc;
    private DriverDao driverDaoMoc;
    private CargoDao cargoDaoMoc;
    private CargoStatusLogDao cargoStatusLogDao;
    private TransactionManager transactionManagerMoc;
    private CustomTransaction customTransactionMoc;
    private OrderManagementService orderManagementService;

    private Cargo getCargoFroTest(){
        Cargo cargo = new Cargo();
        cargo.setNumber(13);
        cargo.setName("Bricks");
        cargo.setWeight(50);

        City pickCity = new City();
        pickCity.setName("Орел");

        City unloadCity = new City();
        unloadCity.setName("Москва");

        List<RoutePoint> routePoints = new ArrayList<>();

        RoutePoint unloadRoute = new RoutePoint();
        unloadRoute.setPoint(0);
        unloadRoute.setCity(unloadCity);
        unloadRoute.setPickup(cargo);

        routePoints.add(unloadRoute);

        Driver driver = new Driver();
        driver.setNumber(13);
        driver.setFirstName("Ivan");
        driver.setLastName("Ivanov");

        Order order = new Order();
        order.setNumber(13);
        order.setDoneState(false);
        order.setRoutePoints(routePoints);
        order.setDrivers(Arrays.asList(driver));

        RoutePoint pickRoute = new RoutePoint();
        pickRoute.setPoint(0);
        pickRoute.setCity(pickCity);
        pickRoute.setOrder(order);


        cargo.setPickup(pickRoute);
        cargo.setUnload(unloadRoute);

        CargoStatusLog cargoStatusLogEntity = new CargoStatusLog();
        cargoStatusLogEntity.setStatus(CargoStatus.delivered);
        cargoStatusLogEntity.setTimestamp(new Date());

        cargo.setStatusLogs(Arrays.asList(cargoStatusLogEntity));
        return cargo;
    }

    @Before
    public void setUp() throws Exception {
        ordersDaoMoc = Mockito.mock(OrdersDao.class);
        driverDaoMoc = Mockito.mock(DriverDao.class);
        cargoDaoMoc = Mockito.mock(CargoDao.class);
        cargoStatusLogDao = Mockito.mock(CargoStatusLogDao.class);
        transactionManagerMoc = Mockito.mock(TransactionManager.class);
        customTransactionMoc = Mockito.mock(CustomTransaction.class);
        Mockito.when(transactionManagerMoc.getTransaction()).thenReturn(customTransactionMoc);
        orderManagementService = new OrderManagementServiceImpl(ordersDaoMoc,driverDaoMoc,cargoDaoMoc,
                cargoStatusLogDao,transactionManagerMoc);

    }

    @Test
    public void testChangeCargoStatusByNumber() throws Exception {
        Mockito.when(cargoDaoMoc.findUniqueByNumber(13)).thenReturn(getCargoFroTest());
        orderManagementService.changeCargoStatusByNumber(13, CargoStatus.delivered);
    }
}