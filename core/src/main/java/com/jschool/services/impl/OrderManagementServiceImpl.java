package com.jschool.services.impl;

import com.jschool.dao.api.*;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.*;
import com.jschool.services.api.DutyService;
import com.jschool.services.api.OrderManagementService;
import com.jschool.services.api.exception.ServiceException;
import com.jschool.services.api.exception.ServiceStatusCode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by infinity on 13.02.16.
 */
@Service
public class OrderManagementServiceImpl implements OrderManagementService {

    private static final Logger LOG = Logger.getLogger(OrderManagementServiceImpl.class);

    private OrdersDao ordersDao;
    private DriverDao driverDao;
    private CargoDao cargoDao;
    private TruckDao truckDao;
    private TruckStatisticDao truckStatisticDao;
    private CargoStatusLogDao cargoStatusLogDao;

    @Autowired
    public OrderManagementServiceImpl(OrdersDao ordersDao, DriverDao driverDao,
                                      CargoDao cargoDao, CargoStatusLogDao cargoStatusLogDao, TruckDao truckDao,
                                      TruckStatisticDao truckStatisticDao) {
        this.ordersDao = ordersDao;
        this.driverDao = driverDao;
        this.cargoDao = cargoDao;
        this.cargoStatusLogDao = cargoStatusLogDao;
        this.truckDao = truckDao;
        this.truckStatisticDao = truckStatisticDao;
    }

    /**Set status for cargo
     * @param cargoNumber
     * @param cargoStatus
     * @throws ServiceException statuse code CARGO_NOT_FOUND
     */
    @Override
    @Transactional(rollbackFor=ServiceException.class)
    public void changeCargoStatusByNumber(int cargoNumber, CargoStatus cargoStatus) throws ServiceException {
        try {
            //check that cargo is exist
            Cargo cargo = cargoDao.findUniqueByNumber(cargoNumber);
            if (cargo != null) {
                Order checkOrder = cargo.getPickup().getOrder();
                if (!checkOrder.isDoneState()) {
                    CargoStatusLog cargoStatusLogEntity = new CargoStatusLog();
                    cargoStatusLogEntity.setStatus(cargoStatus);
                    cargoStatusLogEntity.setTimestamp(new Date());
                    cargoStatusLogEntity.setCargo(cargo);
                    //set cargo status in db
                    cargoStatusLogDao.create(cargoStatusLogEntity);

                    if (cargoStatus == CargoStatus.loaded) {
                        Order order = cargo.getPickup().getOrder();
                        City city = cargo.getPickup().getCity();
                        List<Driver> drivers = order.getDrivers();
                        for (Driver driver : drivers) {
                            driver.setCity(city);
                            driverDao.update(driver);
                        }
                        Truck truck = order.getTruck();
                        if (!truck.getCity().getName().equals(city.getName())) {
                            TruckStatistic truckStatistic = new TruckStatistic();
                            truckStatistic.setTimestamp(new Date());
                            truckStatistic.setTruck(truck);
                            truckStatistic.setCity(city);
                            truckStatisticDao.create(truckStatistic);
                        }
                        truck.setCity(city);
                        truckDao.update(truck);
                    }
                    if (cargoStatus == CargoStatus.delivered) {
                        Order order = cargo.getPickup().getOrder();
                        City city = cargo.getUnload().getCity();
                        List<Driver> drivers = order.getDrivers();
                        for (Driver driver : drivers) {
                            driver.setCity(city);
                            driverDao.update(driver);
                        }
                        Truck truck = order.getTruck();
                        if (!truck.getCity().getName().equals(city.getName())) {
                            TruckStatistic truckStatistic = new TruckStatistic();
                            truckStatistic.setTimestamp(new Date());
                            truckStatistic.setTruck(truck);
                            truckStatistic.setCity(city);
                            truckStatisticDao.create(truckStatistic);
                        }
                        truck.setCity(city);
                        truckDao.update(truck);
                    }
                    //check if cargo status is "delivered" then check all other cargo's status
                    //and if they all are delivered, set order status "done" and free drivers and truck
                    if (cargoStatus == CargoStatus.delivered) {
                        Order order = cargo.getPickup().getOrder();
                        List<RoutePoint> routePoints = order.getRoutePoints();
                        boolean isAllDelivered = true;
                        for (RoutePoint routePoint : routePoints) {
                            if (routePoint.getPickup() != null) {
                                Cargo element = routePoint.getPickup();
                                if (element.getNumber() != cargo.getNumber()) {
                                    List<CargoStatusLog> cargoStatusLogs = element.getStatusLogs();
                                    //check last status for all cargoes in order
                                    if (cargoStatusLogs.get(cargoStatusLogs.size() - 1).getStatus() != CargoStatus.delivered)
                                        isAllDelivered = false;
                                }
                            }
                        }
                        if (isAllDelivered) {
                            order.setDoneState(true);
                            //free truck
                            order.setTruck(null);
                            ordersDao.update(order);
                            List<Driver> drivers = order.getDrivers();
                            for (Driver driver : drivers) {
                                // free drivers
                                driver.setOrder(null);
                                driverDao.update(driver);
                            }
                        }
                    }
                }else
                    throw new ServiceException("Order already done", ServiceStatusCode.ORDER_IS_DONE);
            }else
                throw new ServiceException("Cargo not found", ServiceStatusCode.CARGO_NOT_FOUND);
        }catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }
}
