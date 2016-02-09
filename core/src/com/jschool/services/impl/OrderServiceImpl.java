package com.jschool.services.impl;

import com.jschool.dao.api.DriverDao;
import com.jschool.dao.api.DriverStatisticDao;
import com.jschool.dao.api.OrdersDao;
import com.jschool.dao.api.TruckDao;
import com.jschool.dao.impl.DriverDaoImpl;
import com.jschool.dao.impl.DriverStatisticDaoImpl;
import com.jschool.dao.impl.OrdersDaoImpl;
import com.jschool.dao.impl.TruckDaoImpl;
import com.jschool.entities.Driver;
import com.jschool.entities.DriverStatistic;
import com.jschool.entities.Order;
import com.jschool.entities.Truck;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infinity on 09.02.16.
 */
public class OrderServiceImpl {

    private OrdersDao ordersDao;
    private TruckDao truckDao;
    private DriverDao driverDao;
    private DriverStatisticDao driverStatisticDao;

    public OrderServiceImpl(DriverStatisticDao driverStatisticDao,
                            OrdersDao ordersDao, TruckDao truckDao, DriverDao driverDao) {
        this.driverStatisticDao = driverStatisticDao;
        this.ordersDao = ordersDao;
        this.truckDao = truckDao;
        this.driverDao = driverDao;
    }

    public void create(Order order){
        Order element = ordersDao.findUniqueByNumber(order.getNumber());
        if (element == null){
            ordersDao.create(order);
        }
    }

    public void delete(Order order){
        Order element = ordersDao.findUniqueByNumber(order.getNumber());
        if (element != null && element.isDoneState()){
            ordersDao.delete(order);
        }
    }

    public List<Order> findAll(){
        return ordersDao.findAll();
    }

    public Order findUniqueByNumber(int number){
        return ordersDao.findUniqueByNumber(number);
    }

    public List<Order> findAllByState(boolean isDone) {
        return ordersDao.findAllByState(isDone);
    }

    public List<Truck> findAllFreeByStateAndGreaterThanCapacity(int capacity){
        return truckDao.findAllFreeByStateAndGreaterThanCapacity(true,capacity);
    }

    public void assignTruckToOrder(String truckNumber, int orderNumber){
        Order order = ordersDao.findUniqueByNumber(orderNumber);
        Truck truck = truckDao.findUniqueByNumber(truckNumber);
        if (order != null && truck != null){
            order.setTruck(truck);
            ordersDao.update(order);
        }
    }

    public Truck findUniqueAssignedTruckByOrderNumber(int orderNumber){
        Order order = ordersDao.findUniqueByNumber(orderNumber);
        return order.getTruck();
    }

    public void assignDriverToOrder(int driverNumber, int orderNumber){
        Order order = ordersDao.findUniqueByNumber(orderNumber);
        Truck truck = order.getTruck();
        List<Driver> drivers = order.getDrivers();
        Driver driver = driverDao.findUniqueByNumber(driverNumber);
        if (order != null && driver != null && drivers.size() < truck.getShiftSize()){
            driver.setOrder(order);
            driverDao.update(driver);
        }
    }

    public List<Driver> findAllAssignedDriversByOrderNumber(int orderNumber){
        Order order = ordersDao.findUniqueByNumber(orderNumber);
        return order.getDrivers();
    }

    public List<Driver> findAllFreeDriversByHoursWorked(int hoursWorked){
        List<Driver> drivers = driverDao.findAllFreeDrivers();
        List<Driver> driverList = new ArrayList<Driver>();
        for (Driver driver : drivers){
            List<DriverStatistic> driverStatistics = driverStatisticDao.findAllByOneMonth(driver);
            int sum = 0;
            for (DriverStatistic driverStatistic : driverStatistics)
                sum += driverStatistic.getHoursWorked();
            if (sum + hoursWorked <= 176){
                driverList.add(driver);
            }
        }
        return driverList;
    }
}
