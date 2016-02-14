package com.jschool.services.impl;

import com.jschool.CustomTransaction;
import com.jschool.TransactionManager;
import com.jschool.dao.api.*;
import com.jschool.entities.*;
import com.jschool.services.api.OrderAndCargoService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by infinity on 09.02.16.
 */
public class OrderAndCargoServiceImpl implements OrderAndCargoService {

    private OrdersDao ordersDao;
    private TruckDao truckDao;
    private DriverDao driverDao;
    private CargoDao cargoDao;
    private CargoStatusLogDao cargoStatusLogDao;
    private RoutePointDao routePointDao;
    private CityDao cityDao;
    private TransactionManager transactionManager;

    public OrderAndCargoServiceImpl(OrdersDao ordersDao, TruckDao truckDao,
                                    DriverDao driverDao, CargoDao cargoDao,
                                    CargoStatusLogDao cargoStatusLogDao,
                                    RoutePointDao routePointDao, CityDao cityDao,
                                    TransactionManager transactionManager) {
        this.ordersDao = ordersDao;
        this.truckDao = truckDao;
        this.driverDao = driverDao;
        this.cargoDao = cargoDao;
        this.cargoStatusLogDao = cargoStatusLogDao;
        this.routePointDao = routePointDao;
        this.cityDao = cityDao;
        this.transactionManager = transactionManager;
    }

    public void addOrder(Order order) {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            ordersDao.create(order);
            ct.commit();
        } finally {
            ct.rollbackIfActive();
        }
    }

    public void updateOrder(Order order) {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            Order element = ordersDao.findUniqueByNumber(order.getNumber());
            if (element != null && element.isDoneState()) {
                order.setId(element.getId());
                ordersDao.update(order);
            }
            ct.commit();
        } finally {
            ct.rollbackIfActive();
        }
    }

    public void deleteOrder(int number) {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            Order element = ordersDao.findUniqueByNumber(number);
            if (element != null && element.isDoneState()) {
                ordersDao.delete(element);
            }
            ct.commit();
        } finally {
            ct.rollbackIfActive();
        }
    }

    public List<Order> findAllOrders() {
        return ordersDao.findAll();
    }

    public Order getOrderByNumber(int number) {
        return ordersDao.findUniqueByNumber(number);
    }

    public void addCargo(int orderNumber, Cargo cargo) {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            Order order = ordersDao.findUniqueByNumber(orderNumber);
            City pickupCity = cityDao.findUniqueByName(cargo.getPickup().getCity().getName());
            City unloadCity = cityDao.findUniqueByName(cargo.getUnload().getCity().getName());
            if (order != null && pickupCity != null && unloadCity != null) {
                RoutePoint pickupRoutePoint = new RoutePoint();
                pickupRoutePoint.setOrder(order);
                pickupRoutePoint.setPoint(cargo.getPickup().getPoint());
                pickupRoutePoint.setCity(pickupCity);
                routePointDao.create(pickupRoutePoint);
                RoutePoint unloadRoutePoint = new RoutePoint();
                unloadRoutePoint.setOrder(order);
                unloadRoutePoint.setPoint(cargo.getUnload().getPoint());
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
            ct.commit();
        } finally {
            ct.rollbackIfActive();
        }
    }

    public List<Cargo> findAllCargosByOrderNumber(int number) {
        Order order = ordersDao.findUniqueByNumber(number);
        List<RoutePoint> routePoints = order.getRoutePoints();
        List<Cargo> cargos = new ArrayList<Cargo>();
        for (RoutePoint routePoint : routePoints) {
            if (routePoint.getPickup() != null)
                cargos.add(routePoint.getPickup());
        }
        return cargos;
    }

    public void assignTruckToOrder(String truckNumber, int orderNumber) {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            Order order = ordersDao.findUniqueByNumber(orderNumber);
            Truck truck = truckDao.findUniqueByNumber(truckNumber);
            if (order != null && truck != null) {
                order.setTruck(truck);
                ordersDao.update(order);
            }
            ct.commit();
        } finally {
            ct.rollbackIfActive();
        }
    }

    public Truck getAssignedTruckByOrderNumber(int orderNumber) {
        Order order = ordersDao.findUniqueByNumber(orderNumber);
        return order.getTruck();
    }

    public void assignDriverToOrder(int driverNumber, int orderNumber) {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            Order order = ordersDao.findUniqueByNumber(orderNumber);
            Truck truck = order.getTruck();
            List<Driver> drivers = order.getDrivers();
            Driver driver = driverDao.findUniqueByNumber(driverNumber);
            if (order != null && driver != null && drivers.size() < truck.getShiftSize()) {
                driver.setOrder(order);
                driverDao.update(driver);
            }
            ct.commit();
        } finally {
            ct.rollbackIfActive();
        }

    }

    public List<Driver> getAllAssignedDriversByOrderNumber(int orderNumber) {
        Order order = ordersDao.findUniqueByNumber(orderNumber);
        return order.getDrivers();
    }

}
