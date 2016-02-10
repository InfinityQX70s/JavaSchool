package com.jschool.services.impl;

import com.jschool.TransactionManager;
import com.jschool.dao.api.*;
import com.jschool.dao.impl.DriverDaoImpl;
import com.jschool.dao.impl.DriverStatisticDaoImpl;
import com.jschool.dao.impl.OrdersDaoImpl;
import com.jschool.dao.impl.TruckDaoImpl;
import com.jschool.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by infinity on 09.02.16.
 */
public class OrderServiceImpl {

    private OrdersDao ordersDao;
    private TruckDao truckDao;
    private DriverDao driverDao;
    private DriverStatisticDao driverStatisticDao;
    private CargoDao cargoDao;
    private CargoStatusLogDao cargoStatusLogDao;
    private RoutePointDao routePointDao;
    private CityDao cityDao;
    private TransactionManager transactionManager;

    public OrderServiceImpl(OrdersDao ordersDao, TruckDao truckDao,
                            DriverDao driverDao, DriverStatisticDao driverStatisticDao,
                            CargoDao cargoDao, CargoStatusLogDao cargoStatusLogDao,
                            RoutePointDao routePointDao, CityDao cityDao,
                            TransactionManager transactionManager) {
        this.ordersDao = ordersDao;
        this.truckDao = truckDao;
        this.driverDao = driverDao;
        this.driverStatisticDao = driverStatisticDao;
        this.cargoDao = cargoDao;
        this.cargoStatusLogDao = cargoStatusLogDao;
        this.routePointDao = routePointDao;
        this.cityDao = cityDao;
        this.transactionManager = transactionManager;
    }

    public void create(Order order) {
        try {
            transactionManager.getTransaction().begin();
            ordersDao.create(order);
            transactionManager.getTransaction().commit();
        } finally {
            transactionManager.getTransaction().rollbackIfActive();
        }
    }

    public void delete(int number) {
        try {
            transactionManager.getTransaction().begin();
            Order element = ordersDao.findUniqueByNumber(number);
            if (element != null && element.isDoneState()) {
                ordersDao.delete(element);
            }
            transactionManager.getTransaction().commit();
        } finally {
            transactionManager.getTransaction().rollbackIfActive();
        }
    }

    public void createCargoAndAssignToOrder(int orderNumber, Cargo cargo, String pickupName, String unloadName, int point) {
        try {
            transactionManager.getTransaction().begin();
            Order order = ordersDao.findUniqueByNumber(orderNumber);
            City pickupCity = cityDao.findUniqueByName(pickupName);
            City unloadCity = cityDao.findUniqueByName(unloadName);
            if (order != null && pickupCity != null && unloadCity != null) {
                RoutePoint pickupRoutePoint = new RoutePoint();
                pickupRoutePoint.setOrder(order);
                pickupRoutePoint.setPoint(point);
                pickupRoutePoint.setCity(pickupCity);
                routePointDao.create(pickupRoutePoint);
                RoutePoint unloadRoutePoint = new RoutePoint();
                unloadRoutePoint.setOrder(order);
                unloadRoutePoint.setPoint(point);
                unloadRoutePoint.setCity(unloadCity);
                routePointDao.create(unloadRoutePoint);
                cargo.setPickup(pickupRoutePoint);
                cargo.setUnload(unloadRoutePoint);
                cargoDao.create(cargo);
                CargoStatusLog cargoStatusLogEntity = new CargoStatusLog();
                cargoStatusLogEntity.setStatus(CargoStatus.ready);
                cargoStatusLogEntity.setTimestamp(new Date());
                cargoStatusLogEntity.setCargo(cargo);
                cargoStatusLogDao.create(cargoStatusLogEntity);
            }
            transactionManager.getTransaction().commit();
        } finally {
            transactionManager.getTransaction().rollbackIfActive();
        }
    }

    public List<Order> findAll() {
        return ordersDao.findAll();
    }

    public Order findUniqueByNumber(int number) {
        return ordersDao.findUniqueByNumber(number);
    }

    public List<Order> findAllByState(boolean isDone) {
        return ordersDao.findAllByState(isDone);
    }

    public List<Truck> findAllFreeByStateAndGreaterThanCapacity(int capacity) {
        return truckDao.findAllFreeByStateAndGreaterThanCapacity(true, capacity);
    }

    public void assignTruckToOrder(String truckNumber, int orderNumber) {
        try {
            transactionManager.getTransaction().begin();
            Order order = ordersDao.findUniqueByNumber(orderNumber);
            Truck truck = truckDao.findUniqueByNumber(truckNumber);
            if (order != null && truck != null) {
                order.setTruck(truck);
                ordersDao.update(order);
            }
            transactionManager.getTransaction().commit();
        } finally {
            transactionManager.getTransaction().rollbackIfActive();
        }
    }

    public Truck findUniqueAssignedTruckByOrderNumber(int orderNumber) {
        Order order = ordersDao.findUniqueByNumber(orderNumber);
        return order.getTruck();
    }

    public void assignDriverToOrder(int driverNumber, int orderNumber) {
        try {
            transactionManager.getTransaction().begin();
            Order order = ordersDao.findUniqueByNumber(orderNumber);
            Truck truck = order.getTruck();
            List<Driver> drivers = order.getDrivers();
            Driver driver = driverDao.findUniqueByNumber(driverNumber);
            if (order != null && driver != null && drivers.size() < truck.getShiftSize()) {
                driver.setOrder(order);
                driverDao.update(driver);
            }
            transactionManager.getTransaction().commit();
        } finally {
            transactionManager.getTransaction().rollbackIfActive();
        }

    }

    public List<Driver> findAllAssignedDriversByOrderNumber(int orderNumber) {
        Order order = ordersDao.findUniqueByNumber(orderNumber);
        return order.getDrivers();
    }

    public List<Driver> findAllFreeDriversByHoursWorked(int hoursWorked) {
        List<Driver> drivers = driverDao.findAllFreeDrivers();
        List<Driver> driverList = new ArrayList<Driver>();
        for (Driver driver : drivers) {
            List<DriverStatistic> driverStatistics = driverStatisticDao.findAllByOneMonth(driver);
            int sum = 0;
            for (DriverStatistic driverStatistic : driverStatistics)
                sum += driverStatistic.getHoursWorked();
            if (sum + hoursWorked <= 176) {
                driverList.add(driver);
            }
        }
        return driverList;
    }
}
