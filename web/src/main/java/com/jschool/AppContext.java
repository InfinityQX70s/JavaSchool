package com.jschool;

import com.jschool.controllers.*;
import com.jschool.dao.api.*;
import com.jschool.dao.impl.*;
import com.jschool.services.api.*;
import com.jschool.services.impl.*;
import com.jschool.validator.Validator;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
    private DriverInfoController driverInfoController;
    private Properties errorProperties;

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
            cargoDao = new CargoDaoImpl();
        }
        return cargoDao;
    }

    public synchronized CargoStatusLogDao getCargoStatusLogDao() {
        if (cargoStatusLogDao == null){
            cargoStatusLogDao = new CargoStatusLogDaoImpl();
        }
        return cargoStatusLogDao;
    }

    public synchronized CityDao getCityDao() {
        if (cityDao == null){
            cityDao = new CityDaoImpl();
        }
        return cityDao;
    }

    public synchronized DriverDao getDriverDao() {
        if (driverDao == null){
            driverDao = new DriverDaoImpl();
        }
        return driverDao;
    }

    public synchronized DriverStatisticDao getDriverStatisticDao() {
        if (driverStatisticDao == null){
            driverStatisticDao = new DriverStatisticDaoImpl();
        }
        return driverStatisticDao;
    }

    public synchronized DriverStatusLogDao getDriverStatusLogDao() {
        if (driverStatusLogDao == null){
            driverStatusLogDao = new DriverStatusLogDaoImpl();
        }
        return driverStatusLogDao;
    }

    public synchronized OrdersDao getOrdersDao() {
        if (ordersDao == null){
            ordersDao = new OrdersDaoImpl();
        }
        return ordersDao;
    }

    public synchronized RoutePointDao getRoutePointDao() {
        if (routePointDao == null){
            routePointDao = new RoutePointDaoImpl();
        }
        return routePointDao;
    }

    public synchronized TruckDao getTruckDao() {
        if (truckDao == null){
            truckDao = new TruckDaoImpl();
        }
        return truckDao;
    }

    public synchronized UserDao getUserDao() {
        if (userDao == null){
            userDao = new UserDaoImpl();
        }
        return userDao;
    }


    public synchronized DriverService getDriverService() {
        if (driverService == null){
            driverService = new DriverServiceImpl(getUserDao(),getDriverDao(),
                    getDriverStatisticDao());
        }
        return driverService;
    }

    public synchronized DutyService getDutyService() {
        if (dutyService == null){
            dutyService = new DutyServiceImpl(getDriverDao(), getDriverStatusLogDao(),
                    getDriverStatisticDao());
        }
        return dutyService;
    }

    public synchronized OrderAndCargoService getOrderAndCargoService() {
        if (orderAndCargoService == null){
            orderAndCargoService = new OrderAndCargoServiceImpl(getOrdersDao(),
                    getDriverDao(),getCargoDao(),
                    getRoutePointDao(),getCityDao(),getTruckDao(),getDriverStatisticDao());
        }
        return orderAndCargoService;
    }

    public synchronized OrderManagementService getOrderManagementService() {
        if (orderManagementService == null){
            orderManagementService = new OrderManagementServiceImpl(getOrdersDao(),getDriverDao(),
            getCargoDao(),getCargoStatusLogDao());
        }
        return orderManagementService;
    }

    public synchronized TruckService getTruckService() {
        if (truckService == null){
            truckService = new TruckServiceImpl(getTruckDao());
        }
        return truckService;
    }

//    public synchronized DriverController getDriverController() {
//        if (driverController == null){
//            driverController = new DriverController(getDriverService());
//        }
//        return driverController;
//    }

//    public synchronized LoginController getLoginController() {
//        if (loginController == null){
//            loginController = new LoginController(getErrorProperties(),getDriverService(),getUserService(),getValidator());
//        }
//        return loginController;
//    }

//    public synchronized DriverInfoController getDriverInfoController() {
//        if (driverInfoController == null){
//            driverInfoController = new DriverInfoController(getErrorProperties(),getOrderAndCargoService());
//        }
//        return driverInfoController;
//    }


    public synchronized Validator getValidator() {
        if (validator == null){
            validator = new Validator();
        }
        return validator;
    }

    public synchronized Properties getErrorProperties() {
        if (errorProperties == null){
            try {
                errorProperties = new Properties();
                InputStream in = getClass().getClassLoader().getResourceAsStream("error.properties");
                errorProperties.load(in);
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return errorProperties;
    }
}
