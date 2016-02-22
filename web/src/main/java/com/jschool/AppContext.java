package com.jschool;

import com.jschool.controllers.*;
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

    private DriverController driverController;
    private OrderController orderController;
    private TruckController truckController;
    private LoginController loginController;
    private DriverInfoController driverInfoController;
    private ErrorController errorController;

    private ControllerFactory controllerFactory;

    private Validator validator;

    private AppContext() {
    }

    public static synchronized AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }
        return instance;
    }

    public synchronized EntityManagerFactory getEntityManagerFactory(){
        if (entityManagerFactory == null){
            entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE);
        }
        return entityManagerFactory;
    }

    public synchronized EntityManager getEntityManager(){
        if (entityManager == null  || !entityManager.isOpen()){
            entityManager = getEntityManagerFactory().createEntityManager();
        }
        return entityManager;
    }

    public synchronized TransactionManager getTransactionManager() {
        if (transactionManager == null){
            transactionManager = new TransactionManager(getEntityManager());
        }
        return transactionManager;
    }

    public synchronized CargoDao getCargoDao(){
        if (cargoDao == null){
            cargoDao = new CargoDaoImpl(getEntityManager());
        }
        return cargoDao;
    }

    public synchronized CargoStatusLogDao getCargoStatusLogDao() {
        if (cargoStatusLogDao == null){
            cargoStatusLogDao = new CargoStatusLogDaoImpl(getEntityManager());
        }
        return cargoStatusLogDao;
    }

    public synchronized CityDao getCityDao() {
        if (cityDao == null){
            cityDao = new CityDaoImpl(getEntityManager());
        }
        return cityDao;
    }

    public synchronized DriverDao getDriverDao() {
        if (driverDao == null){
            driverDao = new DriverDaoImpl(getEntityManager());
        }
        return driverDao;
    }

    public synchronized DriverStatisticDao getDriverStatisticDao() {
        if (driverStatisticDao == null){
            driverStatisticDao = new DriverStatisticDaoImpl(getEntityManager());
        }
        return driverStatisticDao;
    }

    public synchronized DriverStatusLogDao getDriverStatusLogDao() {
        if (driverStatusLogDao == null){
            driverStatusLogDao = new DriverStatusLogDaoImpl(getEntityManager());
        }
        return driverStatusLogDao;
    }

    public synchronized OrdersDao getOrdersDao() {
        if (ordersDao == null){
            ordersDao = new OrdersDaoImpl(getEntityManager());
        }
        return ordersDao;
    }

    public synchronized RoutePointDao getRoutePointDao() {
        if (routePointDao == null){
            routePointDao = new RoutePointDaoImpl(getEntityManager());
        }
        return routePointDao;
    }

    public synchronized TruckDao getTruckDao() {
        if (truckDao == null){
            truckDao = new TruckDaoImpl(getEntityManager());
        }
        return truckDao;
    }

    public synchronized UserDao getUserDao() {
        if (userDao == null){
            userDao = new UserDaoImpl(getEntityManager());
        }
        return userDao;
    }

    public synchronized UserService getUserService() {
        if (userService == null){
            userService = new UserServiceImpl(getUserDao(), getTransactionManager());
        }
        return userService;
    }

    public synchronized DriverService getDriverService() {
        if (driverService == null){
            driverService = new DriverServiceImpl(getUserDao(),getDriverDao(),
                    getDriverStatisticDao(), getTransactionManager());
        }
        return driverService;
    }

    public synchronized DutyService getDutyService() {
        if (dutyService == null){
            dutyService = new DutyServiceImpl(getDriverDao(), getDriverStatusLogDao(),
                    getDriverStatisticDao(), getTransactionManager());
        }
        return dutyService;
    }

    public synchronized OrderAndCargoService getOrderAndCargoService() {
        if (orderAndCargoService == null){
            orderAndCargoService = new OrderAndCargoServiceImpl(getOrdersDao(),
                    getDriverDao(),getCargoDao(),
                    getRoutePointDao(),getCityDao(),getTransactionManager());
        }
        return orderAndCargoService;
    }

    public synchronized OrderManagementService getOrderManagementService() {
        if (orderManagementService == null){
            orderManagementService = new OrderManagementServiceImpl(getOrdersDao(),getDriverDao(),
            getCargoDao(),getCargoStatusLogDao(),getTransactionManager());
        }
        return orderManagementService;
    }

    public synchronized TruckService getTruckService() {
        if (truckService == null){
            truckService = new TruckServiceImpl(getTruckDao(),getTransactionManager());
        }
        return truckService;
    }

    public synchronized DriverController getDriverController() {
        if (driverController == null){
            driverController = new DriverController();
        }
        return driverController;
    }

    public synchronized OrderController getOrderController() {
        if (orderController == null){
            orderController = new OrderController();
        }
        return orderController;
    }

    public synchronized TruckController getTruckController() {
        if (truckController == null){
            truckController = new TruckController();
        }
        return truckController;
    }

    public synchronized LoginController getLoginController() {
        if (loginController == null){
            loginController = new LoginController();
        }
        return loginController;
    }

    public synchronized DriverInfoController getDriverInfoController() {
        if (driverInfoController == null){
            driverInfoController = new DriverInfoController();
        }
        return driverInfoController;
    }

    public synchronized ErrorController getErrorController() {
        if (errorController == null){
            errorController = new ErrorController();
        }
        return errorController;
    }

    public synchronized ControllerFactory getControllerFactory() {
        if (controllerFactory == null){
            controllerFactory = new ControllerFactory();
        }
        return controllerFactory;
    }

    public synchronized Validator getValidator() {
        if (validator == null){
            validator = new Validator();
        }
        return validator;
    }
}
