package com.jschool;

import com.jschool.dao.api.*;
import com.jschool.dao.impl.*;
import com.jschool.services.api.*;
import com.jschool.services.impl.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by infinity on 14.02.16.
 */
public class AppContext {

    private static AppContext instance;

    private static final String PERSISTENCE = "Logiweb";

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    private TransactionManager transactionManager;

    private CargoDao cargoDao;
    private CargoStatusLogDao cargoStatusLogDao;
    private CityDao cityDao;
    private DriverDao driverDao;
    private DriverStatisticDao driverStatisticDao;
    private DriverStatusLogDao driverStatusLogDao;
    private OrdersDao ordersDao;
    private RoutePointDao routePointDao;
    private TruckDao truckDao;
    private UserDao userDao;

    private UserService userService;
    private DriverService driverService;
    private DutyService dutyService;
    private OrderAndCargoService orderAndCargoService;
    private OrderManagementService orderManagementService;
    private TruckService truckService;

    private AppContext() {
    }

    public static synchronized AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }
        return instance;
    }

    public EntityManagerFactory getEntityManagerFactory(){
        if (entityManagerFactory == null){
            entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE);
        }
        return entityManagerFactory;
    }

    public EntityManager getEntityManager(){
        if (entityManager == null  || !entityManager.isOpen()){
            entityManager = getEntityManagerFactory().createEntityManager();
        }
        return entityManager;
    }

    public TransactionManager getTransactionManager() {
        if (transactionManager == null){
            transactionManager = new TransactionManager(getEntityManager());
        }
        return transactionManager;
    }

    public CargoDao getCargoDao(){
        if (cargoDao == null){
            cargoDao = new CargoDaoImpl(getEntityManager());
        }
        return cargoDao;
    }

    public CargoStatusLogDao getCargoStatusLogDao() {
        if (cargoStatusLogDao == null){
            cargoStatusLogDao = new CargoStatusLogDaoImpl(getEntityManager());
        }
        return cargoStatusLogDao;
    }

    public CityDao getCityDao() {
        if (cityDao == null){
            cityDao = new CityDaoImpl(getEntityManager());
        }
        return cityDao;
    }

    public DriverDao getDriverDao() {
        if (driverDao == null){
            driverDao = new DriverDaoImpl(getEntityManager());
        }
        return driverDao;
    }

    public DriverStatisticDao getDriverStatisticDao() {
        if (driverStatisticDao == null){
            driverStatisticDao = new DriverStatisticDaoImpl(getEntityManager());
        }
        return driverStatisticDao;
    }

    public DriverStatusLogDao getDriverStatusLogDao() {
        if (driverStatusLogDao == null){
            driverStatusLogDao = new DriverStatusLogDaoImpl(getEntityManager());
        }
        return driverStatusLogDao;
    }

    public OrdersDao getOrdersDao() {
        if (ordersDao == null){
            ordersDao = new OrdersDaoImpl(getEntityManager());
        }
        return ordersDao;
    }

    public RoutePointDao getRoutePointDao() {
        if (routePointDao == null){
            routePointDao = new RoutePointDaoImpl(getEntityManager());
        }
        return routePointDao;
    }

    public TruckDao getTruckDao() {
        if (truckDao == null){
            truckDao = new TruckDaoImpl(getEntityManager());
        }
        return truckDao;
    }

    public UserDao getUserDao() {
        if (userDao == null){
            userDao = new UserDaoImpl(getEntityManager());
        }
        return userDao;
    }

    public UserService getUserService() {
        if (userService == null){
            userService = new UserServiceImpl(getUserDao(), getTransactionManager());
        }
        return userService;
    }

    public DriverService getDriverService() {
        if (driverService == null){
            driverService = new DriverServiceImpl(getUserDao(),getDriverDao(),getDriverStatusLogDao(),
                    getDriverStatisticDao(), getTransactionManager());
        }
        return driverService;
    }

    public DutyService getDutyService() {
        if (dutyService == null){
            dutyService = new DutyServiceImpl(getDriverDao(), getDriverStatusLogDao(),
                    getDriverStatisticDao(), getTransactionManager());
        }
        return dutyService;
    }

    public OrderAndCargoService getOrderAndCargoService() {
        if (orderAndCargoService == null){
            orderAndCargoService = new OrderAndCargoServiceImpl(getOrdersDao(),getTruckDao(),
                    getDriverDao(),getCargoDao(),getCargoStatusLogDao(),
                    getRoutePointDao(),getCityDao(),getTransactionManager());
        }
        return orderAndCargoService;
    }

    public OrderManagementService getOrderManagementService() {
        if (orderManagementService == null){
            orderManagementService = new OrderManagementServiceImpl(getOrdersDao(),getDriverDao(),
            getCargoDao(),getCargoStatusLogDao(),getTransactionManager());
        }
        return orderManagementService;
    }

    public TruckService getTruckService() {
        if (truckService == null){
            truckService = new TruckServiceImpl(getTruckDao(),getTransactionManager());
        }
        return truckService;
    }
}
