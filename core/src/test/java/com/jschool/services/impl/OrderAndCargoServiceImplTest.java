package com.jschool.services.impl;

import com.jschool.dao.api.*;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.*;
import com.jschool.services.api.OrderAndCargoService;
import com.jschool.services.api.exception.ServiceException;
import com.jschool.services.api.exception.ServiceStatusCode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Or;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by infinity on 25.02.16.
 */
public class OrderAndCargoServiceImplTest {

    private OrdersDao ordersDaoMoc;
    private DriverDao driverDaoMoc;
    private CargoDao cargoDaoMoc;
    private RoutePointDao routePointDaoMoc;
    private CityDao cityDaoMoc;
    private TruckDao truckDao;
    private DriverStatisticDao driverStatisticDao;
    private OrderAndCargoService orderAndCargoService;


    @Before
    public void setUp() throws Exception {
        ordersDaoMoc = Mockito.mock(OrdersDao.class);
        driverDaoMoc = Mockito.mock(DriverDao.class);
        cargoDaoMoc = Mockito.mock(CargoDao.class);
        routePointDaoMoc = Mockito.mock(RoutePointDao.class);
        cityDaoMoc = Mockito.mock(CityDao.class);
        truckDao = Mockito.mock(TruckDao.class);
        driverStatisticDao = Mockito.mock(DriverStatisticDao.class);
        orderAndCargoService = new OrderAndCargoServiceImpl(ordersDaoMoc,driverDaoMoc,cargoDaoMoc,
                routePointDaoMoc,cityDaoMoc,truckDao,driverStatisticDao);

    }

    private Order getOrder() {
        Order order = new Order();
        order.setNumber(13);
        order.setDoneState(false);
        List<Driver> drivers = new ArrayList<>();
        drivers.add(getDriver());
        order.setTruck(getTruck());
        order.setDrivers(drivers);
        return order;
    }

    private Truck getTruck(){
        Truck truck = new Truck();
        truck.setNumber("EW23456");
        truck.setCapacity(50);
        truck.setShiftSize(1);
        City city = new City();
        city.setName("Орел");
        truck.setCity(city);
        return truck;
    }

    private Truck getTruckShiftSizeException(){
        Truck truck = new Truck();
        truck.setNumber("EW23456");
        truck.setCapacity(50);
        truck.setShiftSize(0);
        City city = new City();
        city.setName("Орел");
        truck.setCity(city);
        return truck;
    }

    private Truck getTruckAssignedOrder(){
        Truck truck = new Truck();
        truck.setNumber("EW23456");
        truck.setCapacity(50);
        truck.setShiftSize(1);
        Order order = new Order();
        order.setNumber(2344);
        truck.setOreder(order);
        return truck;
    }

    private Truck getTruckCapacityException(){
        Truck truck = new Truck();
        truck.setNumber("EW23456");
        truck.setCapacity(40);
        truck.setShiftSize(1);
        City city = new City();
        city.setName("Орел");
        truck.setCity(city);
        return truck;
    }

    private Driver getDriver(){
        Driver driver = new Driver();
        driver.setNumber(13);
        driver.setFirstName("Ivan");
        driver.setLastName("Ivanov");
        City city = new City();
        city.setName("Орел");
        driver.setCity(city);
        return driver;
    }

    private Driver getDriverAssignedOrder(){
        Driver driver = new Driver();
        driver.setNumber(13);
        driver.setFirstName("Ivan");
        driver.setLastName("Ivanov");
        Order order = new Order();
        order.setNumber(2344);
        driver.setOrder(order);
        return driver;
    }

    private List<DriverStatistic> getDriverStatistic(){
        List<DriverStatistic> driverStatistics = new ArrayList<>();
        DriverStatistic driverStatistic = new DriverStatistic();
        driverStatistic.setHoursWorked(30);
        driverStatistic.setTimestamp(new Date());
        driverStatistics.add(driverStatistic);
        return driverStatistics;
    }

    private List<DriverStatistic> getDriverStatisticHoursException(){
        List<DriverStatistic> driverStatistics = new ArrayList<>();
        DriverStatistic driverStatistic = new DriverStatistic();
        driverStatistic.setHoursWorked(100);
        driverStatistic.setTimestamp(new Date());
        driverStatistics.add(driverStatistic);
        return driverStatistics;
    }

    private List<Cargo> getCargoList(){
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
        Order order = getOrder();
        Driver driver = order.getDrivers().get(0);
        Mockito.when(driverStatisticDao.findAllByOneMonth(driver)).thenReturn(getDriverStatistic());
        Mockito.when(ordersDaoMoc.findUniqueByNumber(13)).thenReturn(null);
        Mockito.when(cargoDaoMoc.findUniqueByNumber(13)).thenReturn(null);
        Mockito.when(truckDao.findUniqueByNumber(getTruck().getNumber())).thenReturn(getTruck());
        Mockito.when(driverDaoMoc.findUniqueByNumber(13)).thenReturn(getDriver());

        City pickCity = new City();
        pickCity.setName("Орел");
        Mockito.when(cityDaoMoc.findUniqueByName("Орел")).thenReturn(pickCity);

        City unloadCity = new City();
        unloadCity.setName("Москва");
        Mockito.when(cityDaoMoc.findUniqueByName("Москва")).thenReturn(unloadCity);

        orderAndCargoService.addOrder(order,getCargoList(), Integer.parseInt("30"),30);
    }

    @Test
    public void testAddOrderExeptipnTrickCapacity(){
        try {
            Mockito.when(driverStatisticDao.findAllByOneMonth(getDriver())).thenReturn(getDriverStatistic());
            Mockito.when(ordersDaoMoc.findUniqueByNumber(13)).thenReturn(null);
            Mockito.when(cargoDaoMoc.findUniqueByNumber(13)).thenReturn(null);
            Mockito.when(truckDao.findUniqueByNumber(getTruck().getNumber())).thenReturn(getTruckCapacityException());
            Mockito.when(driverDaoMoc.findUniqueByNumber(13)).thenReturn(getDriver());

            City pickCity = new City();
            pickCity.setName("Орел");
            Mockito.when(cityDaoMoc.findUniqueByName("Орел")).thenReturn(pickCity);

            City unloadCity = new City();
            unloadCity.setName("Москва");
            Mockito.when(cityDaoMoc.findUniqueByName("Москва")).thenReturn(unloadCity);

            orderAndCargoService.addOrder(getOrder(),getCargoList(), Integer.parseInt("30"),30);
        } catch (ServiceException e) {
            Assert.assertEquals(ServiceStatusCode.TRUCK_WEIGHT_NOT_ENOUGH,e.getStatusCode());
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testAddOrderExeptipnDriverHours() {
        try {
            Order order = getOrder();
            Driver driver = order.getDrivers().get(0);
            Mockito.when(driverStatisticDao.findAllByOneMonth(driver)).thenReturn(getDriverStatisticHoursException());
            Mockito.when(ordersDaoMoc.findUniqueByNumber(13)).thenReturn(null);
            Mockito.when(cargoDaoMoc.findUniqueByNumber(13)).thenReturn(null);
            Mockito.when(truckDao.findUniqueByNumber(getTruck().getNumber())).thenReturn(getTruck());
            Mockito.when(driverDaoMoc.findUniqueByNumber(13)).thenReturn(getDriver());

            City pickCity = new City();
            pickCity.setName("Орел");
            Mockito.when(cityDaoMoc.findUniqueByName("Орел")).thenReturn(pickCity);

            City unloadCity = new City();
            unloadCity.setName("Москва");
            Mockito.when(cityDaoMoc.findUniqueByName("Москва")).thenReturn(unloadCity);

            orderAndCargoService.addOrder(order, getCargoList(), Integer.parseInt("100"),40);
        } catch (ServiceException e) {
            Assert.assertEquals(ServiceStatusCode.DRIVER_HOURS_LIMIT,e.getStatusCode());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddOrderExeptipnDriverAssignedOrder(){
        try {
            Order order = getOrder();
            Driver driver = order.getDrivers().get(0);
            Mockito.when(driverStatisticDao.findAllByOneMonth(driver)).thenReturn(getDriverStatistic());
            Mockito.when(ordersDaoMoc.findUniqueByNumber(13)).thenReturn(null);
            Mockito.when(cargoDaoMoc.findUniqueByNumber(13)).thenReturn(null);
            Mockito.when(truckDao.findUniqueByNumber(getTruck().getNumber())).thenReturn(getTruck());
            Mockito.when(driverDaoMoc.findUniqueByNumber(13)).thenReturn(getDriverAssignedOrder());

            City pickCity = new City();
            pickCity.setName("Орел");
            Mockito.when(cityDaoMoc.findUniqueByName("Орел")).thenReturn(pickCity);

            City unloadCity = new City();
            unloadCity.setName("Москва");
            Mockito.when(cityDaoMoc.findUniqueByName("Москва")).thenReturn(unloadCity);

            orderAndCargoService.addOrder(order, getCargoList(), Integer.parseInt("100"),100);
        } catch (ServiceException e) {
            Assert.assertEquals(ServiceStatusCode.DRIVER_ASSIGNED_ORDER,e.getStatusCode());
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testAddOrderExeptipnTruckAssignedOrder(){
        try {
            Order order = getOrder();
            Driver driver = order.getDrivers().get(0);
            Mockito.when(driverStatisticDao.findAllByOneMonth(driver)).thenReturn(getDriverStatistic());
            Mockito.when(ordersDaoMoc.findUniqueByNumber(13)).thenReturn(null);
            Mockito.when(cargoDaoMoc.findUniqueByNumber(13)).thenReturn(null);
            Mockito.when(truckDao.findUniqueByNumber(getTruck().getNumber())).thenReturn(getTruckAssignedOrder());
            Mockito.when(driverDaoMoc.findUniqueByNumber(13)).thenReturn(getDriverAssignedOrder());

            City pickCity = new City();
            pickCity.setName("Орел");
            Mockito.when(cityDaoMoc.findUniqueByName("Орел")).thenReturn(pickCity);

            City unloadCity = new City();
            unloadCity.setName("Москва");
            Mockito.when(cityDaoMoc.findUniqueByName("Москва")).thenReturn(unloadCity);

            orderAndCargoService.addOrder(order, getCargoList(), Integer.parseInt("100"),100);
        } catch (ServiceException e) {
            Assert.assertEquals(ServiceStatusCode.TRUCK_ASSIGNED_ORDER,e.getStatusCode());
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testAddOrderExeptipnTruckShiftSize(){
        try {
            Order order = getOrder();
            Driver driver = order.getDrivers().get(0);
            Mockito.when(driverStatisticDao.findAllByOneMonth(driver)).thenReturn(getDriverStatistic());
            Mockito.when(ordersDaoMoc.findUniqueByNumber(13)).thenReturn(null);
            Mockito.when(cargoDaoMoc.findUniqueByNumber(13)).thenReturn(null);
            Mockito.when(truckDao.findUniqueByNumber(getTruck().getNumber())).thenReturn(getTruckShiftSizeException());
            Mockito.when(driverDaoMoc.findUniqueByNumber(13)).thenReturn(getDriverAssignedOrder());

            City pickCity = new City();
            pickCity.setName("Орел");
            Mockito.when(cityDaoMoc.findUniqueByName("Орел")).thenReturn(pickCity);

            City unloadCity = new City();
            unloadCity.setName("Москва");
            Mockito.when(cityDaoMoc.findUniqueByName("Москва")).thenReturn(unloadCity);

            orderAndCargoService.addOrder(order, getCargoList(), Integer.parseInt("100"),100);
        } catch (ServiceException e) {
            Assert.assertEquals(ServiceStatusCode.DRIVER_AND_SHIFT_SIZE_NOT_EQUAL,e.getStatusCode());
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }
}