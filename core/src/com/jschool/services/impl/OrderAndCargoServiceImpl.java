package com.jschool.services.impl;

import com.jschool.CustomTransaction;
import com.jschool.TransactionManager;
import com.jschool.dao.api.*;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.*;
import com.jschool.services.api.OrderAndCargoService;
import com.jschool.services.api.exception.ServiceException;
import com.jschool.services.api.exception.ServiceStatusCode;

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

    public void addOrder(Order order, List<Cargo> cargos) throws ServiceException {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            Order element = ordersDao.findUniqueByNumber(order.getNumber());
            if (element == null) {
                List<Driver> drivers = order.getDrivers();
                order.setRoutePoints(null);
                order.setDrivers(null);
                ordersDao.create(order);
                assignDrivers(order,drivers);
                assignCargos(order,cargos);
                ct.commit();
            }else
                throw new ServiceException("Order with such identifier exist", ServiceStatusCode.ALREADY_EXIST);
        }catch (DaoException e){
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        } finally {
            ct.rollbackIfActive();
        }
    }

    private void assignCargos(Order order, List<Cargo> cargos) throws DaoException, ServiceException {
        for (Cargo cargo : cargos){
            Cargo check = cargoDao.findUniqueByNumber(cargo.getNumber());
            if (check == null) {
                RoutePoint pickupRoutePoint = cargo.getPickup();
                City city = cityDao.findUniqueByName(pickupRoutePoint.getCity().getName());
                if (city == null)
                    throw new ServiceException("City with such name not found", ServiceStatusCode.ALREADY_EXIST);
                pickupRoutePoint.setCity(city);
                pickupRoutePoint.setOrder(order);
                routePointDao.create(pickupRoutePoint);
                RoutePoint unloadRoutePoint = cargo.getUnload();
                city = cityDao.findUniqueByName(unloadRoutePoint.getCity().getName());
                if (city == null)
                    throw new ServiceException("City with such name not found", ServiceStatusCode.ALREADY_EXIST);
                unloadRoutePoint.setCity(city);
                unloadRoutePoint.setOrder(order);
                routePointDao.create(unloadRoutePoint);
                cargo.setPickup(pickupRoutePoint);
                cargo.setUnload(unloadRoutePoint);
                List<CargoStatusLog> cargoStatusLogs = new ArrayList<>();
                CargoStatusLog cargoStatusLogEntity = new CargoStatusLog();
                cargoStatusLogEntity.setStatus(CargoStatus.ready);
                cargoStatusLogEntity.setTimestamp(new Date());
                cargoStatusLogEntity.setCargo(cargo);
                cargoStatusLogs.add(cargoStatusLogEntity);
                cargo.setStatusLogs(cargoStatusLogs);
                cargoDao.create(cargo);
            }else {
                throw new ServiceException("Cargo with such identifier exist", ServiceStatusCode.ALREADY_EXIST);
            }
        }
    }

    private void assignDrivers(Order order, List<Driver> drivers) throws ServiceException, DaoException {
        Truck truck = order.getTruck();
        if (truck.getShiftSize()==drivers.size()){
            for (Driver driver : drivers) {
                driver.setOrder(order);
                driverDao.update(driver);
            }
        }else {
            throw new ServiceException("Shift size and drivers count do not equals", ServiceStatusCode.NOT_EQUAL);
        }
    }

    public void updateOrder(Order order) throws ServiceException {
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
                throw new ServiceException("Order not found", ServiceStatusCode.NOT_FOUND);
            if (!element.isDoneState())
                throw new ServiceException("Order did not done", ServiceStatusCode.DID_NOT_DONE);
        }catch (DaoException e){
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        } finally {
            ct.rollbackIfActive();
        }
    }

    public void deleteOrder(int number) throws ServiceException {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            Order element = ordersDao.findUniqueByNumber(number);
            if (element != null && element.isDoneState()) {
                ordersDao.delete(element);
                ct.commit();
            }
            if (element == null)
                throw new ServiceException("Order not found", ServiceStatusCode.NOT_FOUND);
            if (!element.isDoneState())
                throw new ServiceException("Order did not done", ServiceStatusCode.DID_NOT_DONE);
        }catch (DaoException e){
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        } finally {
            ct.rollbackIfActive();
        }
    }

    public List<Order> findAllOrders() throws ServiceException {
        try {
            return ordersDao.findAll();
        }catch (DaoException e){
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public Order getOrderByNumber(int number) throws ServiceException {
        try {
            Order order = ordersDao.findUniqueByNumber(number);
            if (order == null)
                throw new ServiceException("Order not found", ServiceStatusCode.NOT_FOUND);
            return order;
        }catch (DaoException e){
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public List<Cargo> findAllCargosByOrderNumber(int number) throws ServiceException {
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
                throw new ServiceException("Order not found", ServiceStatusCode.NOT_FOUND);
        }catch (DaoException e) {
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public Truck getAssignedTruckByOrderNumber(int orderNumber) throws ServiceException {
        try {
            Order order = ordersDao.findUniqueByNumber(orderNumber);
            if (order != null) {
                Truck truck = order.getTruck();
                if (truck != null)
                    return truck;
                else
                    throw new ServiceException("Truck do not assign", ServiceStatusCode.NOT_FOUND);
            }else
                throw new ServiceException("Order not found", ServiceStatusCode.NOT_FOUND);
        }catch (DaoException e) {
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public List<Driver> getAllAssignedDriversByOrderNumber(int orderNumber) throws ServiceException {
        try {
            Order order = ordersDao.findUniqueByNumber(orderNumber);
            if (order != null)
                return order.getDrivers();
            else
                throw new ServiceException("Order not found", ServiceStatusCode.NOT_FOUND);
        }catch (DaoException e) {
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

}
