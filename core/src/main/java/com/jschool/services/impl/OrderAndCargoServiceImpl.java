package com.jschool.services.impl;

import com.jschool.dao.api.*;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.*;
import com.jschool.services.api.OrderAndCargoService;
import com.jschool.services.api.exception.ServiceException;
import com.jschool.services.api.exception.ServiceStatusCode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by infinity on 09.02.16.
 */
@Service
public class OrderAndCargoServiceImpl implements OrderAndCargoService {

    private static final Logger LOG = Logger.getLogger(OrderAndCargoServiceImpl.class);

    private OrdersDao ordersDao;
    private DriverDao driverDao;
    private CargoDao cargoDao;
    private RoutePointDao routePointDao;
    private CityDao cityDao;
    private TruckDao truckDao;
    private DriverStatisticDao driverStatisticDao;

    @Autowired
    public OrderAndCargoServiceImpl(OrdersDao ordersDao,
                                    DriverDao driverDao, CargoDao cargoDao,
                                    RoutePointDao routePointDao, CityDao cityDao,
                                    TruckDao truckDao, DriverStatisticDao driverStatisticDao) {
        this.ordersDao = ordersDao;
        this.driverDao = driverDao;
        this.cargoDao = cargoDao;
        this.routePointDao = routePointDao;
        this.cityDao = cityDao;
        this.truckDao = truckDao;
        this.driverStatisticDao = driverStatisticDao;
    }

    /**
     * Add full order in db, large transaction with adding cargos, crago status,
     * route pints, assign driver and truck
     *
     * @param order    entity with filled fields
     * @param cargos   list of cargos for order
     * @param duration of order
     * @throws ServiceException with status code TRUCK_ASSIGNED_ORDER - Truck has an order,
     *                          ORDER_ALREADY_EXIST - order with such identifier exist in db
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void addOrder(Order order, List<Cargo> cargos, int duration, int maxWeight) throws ServiceException {
        try {
            Order element = ordersDao.findUniqueByNumber(order.getNumber());
            if (element == null) {
                // find truck and check that it did not has order
                Truck truck = truckDao.findUniqueByNumber(order.getTruck().getNumber());
                if (truck.getOreder() == null) {
                    if (truck.getCity().getName().equals(cargos.get(0).getPickup().getCity().getName())) {
                        // get list of drivers we want to assign
                        if (truck.isRepairState()) {
                            List<Driver> drivers = order.getDrivers();
                            order.setRoutePoints(null);
                            order.setDrivers(null);
                            order.setTruck(truck);
                            ordersDao.create(order);
                            //method assign drivers to order
                            assignDrivers(order, drivers, duration);
                            // method add cargos to order and check capacity
                            assignCargos(order, cargos, truck.getCapacity(), maxWeight);
                        }else{
                            throw new ServiceException("Truck is in broken state", ServiceStatusCode.TRUCK_BROKEN);
                        }
                    } else {
                        throw new ServiceException("Truck not in same city", ServiceStatusCode.TRUCK_NOT_IN_SAME_CITY);
                    }
                } else
                    throw new ServiceException("Truck has an order", ServiceStatusCode.TRUCK_ASSIGNED_ORDER);
            } else
                throw new ServiceException("Order with such identifier exist", ServiceStatusCode.ORDER_ALREADY_EXIST);
        } catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    /**
     * Add cargoes to order and check that all cargoes enter in current truck
     *
     * @param order
     * @param cargos   list of cargoes and route points for order
     * @param capacity of truck
     * @throws DaoException
     * @throws ServiceException status CITY_NOT_FOUND, CARGO_ALREADY_EXIST, TRUCK_WEIGHT_NOT_ENOUGH
     */
    private void assignCargos(Order order, List<Cargo> cargos, int capacity, int maxWeight) throws DaoException, ServiceException {
        for (Cargo cargo : cargos) {
            //check cargo has unique number
            Cargo check = cargoDao.findUniqueByNumber(cargo.getNumber());
            if (check == null) {
                //create  pick up route point for current cargo
                RoutePoint pickupRoutePoint = cargo.getPickup();
                City city = cityDao.findUniqueByName(pickupRoutePoint.getCity().getName());
                if (city == null)
                    throw new ServiceException("City with such name not found", ServiceStatusCode.CITY_NOT_FOUND);
                pickupRoutePoint.setCity(city);
                pickupRoutePoint.setOrder(order);
                routePointDao.create(pickupRoutePoint);

                // create unload route point for cargo
                RoutePoint unloadRoutePoint = cargo.getUnload();
                city = cityDao.findUniqueByName(unloadRoutePoint.getCity().getName());
                if (city == null)
                    throw new ServiceException("City with such name not found", ServiceStatusCode.CITY_NOT_FOUND);
                unloadRoutePoint.setCity(city);
                unloadRoutePoint.setOrder(order);
                routePointDao.create(unloadRoutePoint);

                //set points to cargo
                cargo.setPickup(pickupRoutePoint);
                cargo.setUnload(unloadRoutePoint);

                //set cargo default status "ready"
                List<CargoStatusLog> cargoStatusLogs = new ArrayList<>();
                CargoStatusLog cargoStatusLogEntity = new CargoStatusLog();
                cargoStatusLogEntity.setStatus(CargoStatus.ready);
                cargoStatusLogEntity.setTimestamp(new Date());
                cargoStatusLogEntity.setCargo(cargo);
                cargoStatusLogs.add(cargoStatusLogEntity);

                cargo.setStatusLogs(cargoStatusLogs);
                cargoDao.create(cargo);
            } else {
                throw new ServiceException("Cargo with such identifier exist", ServiceStatusCode.CARGO_ALREADY_EXIST);
            }
        }
        //check capacity of truck
        if (capacity < maxWeight)
            throw new ServiceException("Truck weight not enough", ServiceStatusCode.TRUCK_WEIGHT_NOT_ENOUGH);

    }

    /**
     * Assign drivers to order and check hours of work for every driver
     *
     * @param order
     * @param drivers  list of drivers
     * @param duration of order
     * @throws ServiceException status codes DRIVER_ASSIGNED_ORDER, DRIVER_HOURS_LIMIT, DRIVER_AND_SHIFT_SIZE_NOT_EQUAL
     * @throws DaoException
     */
    private void assignDrivers(Order order, List<Driver> drivers, int duration) throws ServiceException, DaoException {
        Truck truck = order.getTruck();
        //check that driver's count equals truck shift size
        if (truck.getShiftSize() == drivers.size()) {
            for (Driver driver : drivers) {
                //check driver do not has order
                if (driverDao.findUniqueByNumber(driver.getNumber()).getOrder() == null) {
                    if (truck.getCity().getName().equals(driver.getCity().getName())) {
                        // count hours of work for current driver per month
                        List<DriverStatistic> driverStatistics = driverStatisticDao.findAllByOneMonth(driver);
                        int sum = 0;
                        for (DriverStatistic driverStatistic : driverStatistics)
                            sum += driverStatistic.getHoursWorked();
                        if (sum + duration <= 176) {
                            driver.setOrder(order);
                            // assign driver to order
                            driverDao.update(driver);
                        } else {
                            throw new ServiceException("Driver hours limit is exhausted", ServiceStatusCode.DRIVER_HOURS_LIMIT);
                        }
                    } else
                        throw new ServiceException("Driver not in same city", ServiceStatusCode.DRIVER_NOT_IN_SAME_CITY);
                } else {
                    throw new ServiceException("Driver has an order", ServiceStatusCode.DRIVER_ASSIGNED_ORDER);
                }
            }
        } else {
            throw new ServiceException("Shift size and drivers count do not equals", ServiceStatusCode.DRIVER_AND_SHIFT_SIZE_NOT_EQUAL);
        }
    }

    /**
     * Update order if it is exist and done. Do not use right now
     *
     * @param order
     * @throws ServiceException statuses ORDER_NOT_FOUND,ORDER_DID_NOT_DONE
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void updateOrder(Order order) throws ServiceException {
        try {
            Order element = ordersDao.findUniqueByNumber(order.getNumber());
            if (element != null && element.isDoneState()) {
                order.setId(element.getId());
                ordersDao.update(order);
            }
            if (element == null)
                throw new ServiceException("Order not found", ServiceStatusCode.ORDER_NOT_FOUND);
            if (!element.isDoneState())
                throw new ServiceException("Order did not done", ServiceStatusCode.ORDER_DID_NOT_DONE);
        } catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    /**
     * Delete order if it is exist and done. Do not use right now
     *
     * @param number of order
     * @throws ServiceException statuses ORDER_NOT_FOUND,ORDER_DID_NOT_DONE
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void deleteOrder(int number) throws ServiceException {
        try {
            Order element = ordersDao.findUniqueByNumber(number);
            if (element != null && element.isDoneState()) {
                ordersDao.delete(element);
            }
            if (element == null)
                throw new ServiceException("Order not found", ServiceStatusCode.ORDER_NOT_FOUND);
            if (!element.isDoneState())
                throw new ServiceException("Order did not done", ServiceStatusCode.ORDER_DID_NOT_DONE);
        } catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public List<Order> findAllOrders() throws ServiceException {
        try {
            return ordersDao.findAll();
        } catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public Order getOrderByNumber(int number) throws ServiceException {
        try {
            Order order = ordersDao.findUniqueByNumber(number);
            if (order == null)
                throw new ServiceException("Order not found", ServiceStatusCode.ORDER_NOT_FOUND);
            return order;
        } catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    /**
     * Return list of cargoes assign with order
     *
     * @param number of order
     * @return list of cargoes assign with order
     * @throws ServiceException status ORDER_NOT_FOUND
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
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
            } else
                throw new ServiceException("Order not found", ServiceStatusCode.ORDER_NOT_FOUND);
        } catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    @Override
    @Transactional(rollbackFor=ServiceException.class)
    public List<RoutePoint> findAllRoutePointsByOrderNumber(Order order) throws ServiceException {
        try {
            return routePointDao.findByOrder(order);
        }catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public Truck getAssignedTruckByOrderNumber(int orderNumber) throws ServiceException {
        try {
            Order order = ordersDao.findUniqueByNumber(orderNumber);
            if (order != null) {
                Truck truck = order.getTruck();
                if (truck != null)
                    return truck;
                else
                    throw new ServiceException("Truck do not assign", ServiceStatusCode.TRUCK_DID_NOT_ASSIGNED_ORDER);
            } else
                throw new ServiceException("Order not found", ServiceStatusCode.ORDER_NOT_FOUND);
        } catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public List<Driver> getAllAssignedDriversByOrderNumber(int orderNumber) throws ServiceException {
        try {
            Order order = ordersDao.findUniqueByNumber(orderNumber);
            if (order != null)
                return order.getDrivers();
            else
                throw new ServiceException("Order not found", ServiceStatusCode.ORDER_NOT_FOUND);
        } catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public List<String> getMaxWeight(String[] cargoWeight, String[] pickup, String[] unload){
        int maxWeight = 0;
        String maxCity = "";
        for (int i = 0; i < cargoWeight.length; i++) {
            int j = i;
            int maxWeightLocal = 0;
            while (j < cargoWeight.length && pickup[i].equals(pickup[j])) {
                maxWeightLocal += Integer.parseInt(cargoWeight[j]);
                j++;
            }
            if (maxWeightLocal > maxWeight) {
                maxWeight = maxWeightLocal;
                maxCity = pickup[i];
            }
            j = i;
            maxWeightLocal = 0;
            while (j < cargoWeight.length && unload[i].equals(unload[j])) {
                maxWeightLocal += Integer.parseInt(cargoWeight[j]);
                j++;
            }
            if (maxWeightLocal > maxWeight) {
                maxWeight = maxWeightLocal;
                maxCity = unload[i];
            }
        }
        List<String> info = new ArrayList<>();
        info.add(String.valueOf(maxWeight));
        info.add(maxCity);
        return info;
    }

    public void fillRoute(List<String> cities, List<Integer> countOfUse, String[] pickupCity, String[] unloadCity){
        int i = 0;
        while (i<pickupCity.length){
            cities.add(pickupCity[i]);
            cities.add(unloadCity[i]);
            countOfUse.add(0);
            countOfUse.add(0);
            int j = i;
            while (j < pickupCity.length && pickupCity[i].equals(pickupCity[j]) && unloadCity[i].equals(unloadCity[j])){
                countOfUse.set(countOfUse.size()-1,countOfUse.get(countOfUse.size()-1)+1);
                countOfUse.set(countOfUse.size()-2,countOfUse.get(countOfUse.size()-2)+1);
                j++;
            }
            if (j < pickupCity.length && pickupCity[i].equals(pickupCity[j]) && !unloadCity[i].equals(unloadCity[j])){
                int position = countOfUse.size()-1;
                while (j < pickupCity.length && pickupCity[i].equals(pickupCity[j]) && !unloadCity[i].equals(unloadCity[j])){
                    cities.add(unloadCity[j]);
                    countOfUse.set(position,countOfUse.get(position)+1);
                    countOfUse.add(1);
                    j++;
                }
            }else if (j < pickupCity.length && !pickupCity[i].equals(pickupCity[j]) && unloadCity[i].equals(unloadCity[j])){
                while (j < pickupCity.length && !pickupCity[i].equals(pickupCity[j]) && unloadCity[i].equals(unloadCity[j])){
                    String helpCity = cities.get(cities.size()-1);
                    cities.set(cities.size()-1,pickupCity[j]);
                    cities.add(helpCity);
                    Integer value = countOfUse.get(countOfUse.size()-1);
                    countOfUse.set(countOfUse.size()-1,1);
                    countOfUse.add(value + 1);
                    j++;
                }
            }
            i=j;
        }
    }


}
