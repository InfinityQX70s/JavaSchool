package com.jschool.services.impl;

import com.jschool.CustomTransaction;
import com.jschool.TransactionManager;
import com.jschool.dao.api.*;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.*;
import com.jschool.services.api.OrderAndCargoService;
import com.jschool.services.api.exception.ServiceExeption;
import com.jschool.services.api.exception.StatusCode;
import org.hibernate.internal.CriteriaImpl;

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

    public void addOrder(Order order) throws ServiceExeption {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            Order element = ordersDao.findUniqueByNumber(order.getNumber());
            if (element == null) {
                ordersDao.create(order);
                ct.commit();
            }else
                throw new ServiceExeption("Order with such identifier exist", StatusCode.ALREADY_EXIST);
        }catch (DaoException e){
            throw new ServiceExeption("Unknown exception", e, StatusCode.UNKNOWN);
        } finally {
            ct.rollbackIfActive();
        }
    }

    public void updateOrder(Order order) throws ServiceExeption {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            Order element = ordersDao.findUniqueByNumber(order.getNumber());
            if (element != null && element.isDoneState()) {
                order.setId(element.getId());
                ordersDao.update(order);
                ct.commit();
            }
            if (element == null)
                throw new ServiceExeption("Order not found", StatusCode.NOT_FOUND);
            if (!element.isDoneState())
                throw new ServiceExeption("Order did not done", StatusCode.DID_NOT_DONE);
        }catch (DaoException e){
            throw new ServiceExeption("Unknown exception", e, StatusCode.UNKNOWN);
        } finally {
            ct.rollbackIfActive();
        }
    }

    public void deleteOrder(int number) throws ServiceExeption {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            Order element = ordersDao.findUniqueByNumber(number);
            if (element != null && element.isDoneState()) {
                ordersDao.delete(element);
                ct.commit();
            }
            if (element == null)
                throw new ServiceExeption("Order not found", StatusCode.NOT_FOUND);
            if (!element.isDoneState())
                throw new ServiceExeption("Order did not done", StatusCode.DID_NOT_DONE);
        }catch (DaoException e){
            throw new ServiceExeption("Unknown exception", e, StatusCode.UNKNOWN);
        } finally {
            ct.rollbackIfActive();
        }
    }

    public List<Order> findAllOrders() throws ServiceExeption {
        try {
            return ordersDao.findAll();
        }catch (DaoException e){
            throw new ServiceExeption("Unknown exception", e, StatusCode.UNKNOWN);
        }
    }

    public Order getOrderByNumber(int number) throws ServiceExeption {
        try {
            Order order = ordersDao.findUniqueByNumber(number);
            if (order == null)
                throw new ServiceExeption("Order not found", StatusCode.NOT_FOUND);
            return order;
        }catch (DaoException e){
            throw new ServiceExeption("Unknown exception", e, StatusCode.UNKNOWN);
        }
    }

    public void addCargo(int orderNumber, Cargo cargo) throws ServiceExeption {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            Order order = ordersDao.findUniqueByNumber(orderNumber);
            if (order != null) {
                City pickupCity = cityDao.findUniqueByName(cargo.getPickup().getCity().getName());
                City unloadCity = cityDao.findUniqueByName(cargo.getUnload().getCity().getName());
                if (pickupCity != null && unloadCity != null) {
                    RoutePoint pickupRoutePoint = new RoutePoint();
                    pickupRoutePoint.setOrder(order);
                    pickupRoutePoint.setPoint(cargo.getPickup().getPoint());
                    pickupRoutePoint.setCity(pickupCity);
                    pickupRoutePoint.setPickup(cargo);
                    routePointDao.create(pickupRoutePoint);
                    RoutePoint unloadRoutePoint = new RoutePoint();
                    unloadRoutePoint.setOrder(order);
                    unloadRoutePoint.setPoint(cargo.getUnload().getPoint());
                    unloadRoutePoint.setCity(unloadCity);
                    unloadRoutePoint.setUnload(cargo);
                    routePointDao.create(unloadRoutePoint);
                    cargo.setPickup(pickupRoutePoint);
                    cargo.setUnload(unloadRoutePoint);
                    cargoDao.create(cargo);
                    CargoStatusLog cargoStatusLogEntity = new CargoStatusLog();
                    cargoStatusLogEntity.setStatus(CargoStatus.ready);
                    cargoStatusLogEntity.setTimestamp(new Date());
                    cargoStatusLogEntity.setCargo(cargo);
                    cargoStatusLogDao.create(cargoStatusLogEntity);
                    ct.commit();
                }else
                    throw new ServiceExeption("City not found", StatusCode.NOT_FOUND);
            }else
                throw new ServiceExeption("Order not found", StatusCode.NOT_FOUND);
        }catch (DaoException e){
            throw new ServiceExeption("Unknown exception", e, StatusCode.UNKNOWN);
        } finally {
            ct.rollbackIfActive();
        }
    }

    public List<Cargo> findAllCargosByOrderNumber(int number) throws ServiceExeption {
        try {
            Order order = ordersDao.findUniqueByNumber(number);
            if (order != null) {
                List<RoutePoint> routePoints = order.getRoutePoints();
                List<Cargo> cargos = new ArrayList<Cargo>();
                for (RoutePoint routePoint : routePoints) {
                    if (routePoint.getPickup() != null)
                        cargos.add(routePoint.getPickup());
                }
                return cargos;
            }else
                throw new ServiceExeption("Order not found", StatusCode.NOT_FOUND);
        }catch (DaoException e) {
            throw new ServiceExeption("Unknown exception", e, StatusCode.UNKNOWN);
        }
    }

    public void assignTruckToOrder(String truckNumber, int orderNumber) throws ServiceExeption {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            Order order = ordersDao.findUniqueByNumber(orderNumber);
            Truck truck = truckDao.findUniqueByNumber(truckNumber);
            if (order != null && truck != null) {
                order.setTruck(truck);
                ordersDao.update(order);
                ct.commit();
            }else
                throw new ServiceExeption("Order or Truck not found", StatusCode.NOT_FOUND);
        }catch (DaoException e) {
            throw new ServiceExeption("Unknown exception", e, StatusCode.UNKNOWN);
        } finally {
            ct.rollbackIfActive();
        }
    }

    public Truck getAssignedTruckByOrderNumber(int orderNumber) throws ServiceExeption {
        try {
            Order order = ordersDao.findUniqueByNumber(orderNumber);
            if (order != null) {
                Truck truck = order.getTruck();
                if (truck != null)
                    return truck;
                else
                    throw new ServiceExeption("Truck not found", StatusCode.NOT_FOUND);
            }else
                throw new ServiceExeption("Order not found", StatusCode.NOT_FOUND);
        }catch (DaoException e) {
            throw new ServiceExeption("Unknown exception", e, StatusCode.UNKNOWN);
        }
    }

    public void assignDriverToOrder(int driverNumber, int orderNumber) throws ServiceExeption {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            Order order = ordersDao.findUniqueByNumber(orderNumber);
            if (order != null) {
                Truck truck = order.getTruck();
                if (truck != null) {
                    List<Driver> drivers = order.getDrivers();
                    Driver driver = driverDao.findUniqueByNumber(driverNumber);
                    if (driver != null){
                        if (drivers.size() < truck.getShiftSize()) {
                            driver.setOrder(order);
                            driverDao.update(driver);
                            ct.commit();
                        }
                    }else
                        throw new ServiceExeption("Driver not found", StatusCode.NOT_FOUND);
                }else
                    throw new ServiceExeption("Truck do not assign", StatusCode.NOT_FOUND);
            }else
                throw new ServiceExeption("Order not found", StatusCode.NOT_FOUND);
        }catch (DaoException e) {
            throw new ServiceExeption("Unknown exception", e, StatusCode.UNKNOWN);
        } finally {
            ct.rollbackIfActive();
        }

    }

    public List<Driver> getAllAssignedDriversByOrderNumber(int orderNumber) throws ServiceExeption {
        try {
            Order order = ordersDao.findUniqueByNumber(orderNumber);
            if (order != null)
                return order.getDrivers();
            else
                throw new ServiceExeption("Order not found", StatusCode.NOT_FOUND);
        }catch (DaoException e) {
            throw new ServiceExeption("Unknown exception", e, StatusCode.UNKNOWN);
        }
    }

}
