package com.jschool.services.impl;

import com.jschool.TransactionManager;
import com.jschool.dao.api.CargoDao;
import com.jschool.dao.api.CargoStatusLogDao;
import com.jschool.dao.api.DriverDao;
import com.jschool.dao.api.OrdersDao;
import com.jschool.dao.impl.CargoDaoImpl;
import com.jschool.dao.impl.CargoStatusLogDaoImpl;
import com.jschool.entities.*;
import com.jschool.services.api.CargoService;

import java.util.Date;
import java.util.List;

/**
 * Created by infinity on 08.02.16.
 */
public class CargoServiceImpl implements CargoService{

    private CargoDao cargoDao;
    private CargoStatusLogDao cargoStatusLogDao;
    private OrdersDao ordersDao;
    private DriverDao driverDao;
    private TransactionManager transactionManager;

    public CargoServiceImpl(CargoDao cargoDao, CargoStatusLogDao cargoStatusLogDao,
                            OrdersDao ordersDao, DriverDao driverDao,
                            TransactionManager transactionManager) {
        this.cargoDao = cargoDao;
        this.cargoStatusLogDao = cargoStatusLogDao;
        this.ordersDao = ordersDao;
        this.driverDao = driverDao;
        this.transactionManager = transactionManager;
    }

    public void setCargoStatus(int number, CargoStatus status) {
        try {
            transactionManager.getTransaction().begin();
            Cargo cargo = findByNumber(number);
            CargoStatusLog cargoStatusLogEntity = new CargoStatusLog();
            cargoStatusLogEntity.setStatus(status);
            cargoStatusLogEntity.setTimestamp(new Date());
            cargoStatusLogEntity.setCargo(cargo);
            cargoStatusLogDao.create(cargoStatusLogEntity);
            if (status == CargoStatus.delivered) {
                Order order = cargo.getPickup().getOrder();
                List<RoutePoint> routePoints = order.getRoutePoints();
                boolean isAllDelivered = true;
                for (RoutePoint routePoint : routePoints) {
                    Cargo element;
                    if (routePoint.getPickup() == null)
                        element = routePoint.getUnload();
                    else
                        element = routePoint.getPickup();
                    List<CargoStatusLog> cargoStatusLogs = element.getStatusLogs();
                    if (cargoStatusLogs.get(cargoStatusLogs.size() - 1).getStatus() != CargoStatus.delivered)
                        isAllDelivered = false;
                }
                if (isAllDelivered) {
                    order.setDoneState(true);
                    order.setTruck(null);
                    ordersDao.update(order);
                    List<Driver> drivers = order.getDrivers();
                    for (Driver driver : drivers){
                        driver.setOrder(null);
                        driverDao.update(driver);
                    }
                }
            }
            transactionManager.getTransaction().commit();
        } finally {
            transactionManager.getTransaction().rollbackIfActive();
        }
    }

    public Cargo findByNumber(int number) {
        return cargoDao.findUniqueByNumber(number);
    }

    public List<Cargo> findAll() {
        return cargoDao.findAll();
    }
}

