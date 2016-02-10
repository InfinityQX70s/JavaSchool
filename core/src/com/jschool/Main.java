package com.jschool;

import com.jschool.dao.api.DriverDao;
import com.jschool.dao.api.DriverStatisticDao;
import com.jschool.dao.api.TruckDao;
import com.jschool.dao.impl.*;
import com.jschool.entities.*;
import com.jschool.services.impl.CargoServiceImpl;
import com.jschool.services.impl.DriverServiceImpl;
import com.jschool.services.impl.OrderServiceImpl;
import com.jschool.services.impl.TruckServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by infinity on 06.02.16.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Main");
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Logiweb");
        EntityManager entityManager = entityManagerFactory.createEntityManager( );
        TransactionManager transactionManager = new TransactionManager(entityManager);
//        DriverServiceImpl driverService = new DriverServiceImpl(new UserDaoImpl(entityManager),
//                new DriverDaoImpl(entityManager),
//                new DriverStatusLogDaoImpl(entityManager),
//                new DriverStatisticDaoImpl(entityManager),
//                transactionManager);
        OrderServiceImpl orderService = new OrderServiceImpl(new OrdersDaoImpl(entityManager),
                new TruckDaoImpl(entityManager), new DriverDaoImpl(entityManager),
                new DriverStatisticDaoImpl(entityManager),
                new CargoDaoImpl(entityManager), new CargoStatusLogDaoImpl(entityManager),
                new RoutePointDaoImpl(entityManager), new CityDaoImpl(entityManager),
                transactionManager);
//        User user = new User();
//        user.setEmail("mazu@yandex.ru");
//        user.setPassword("222");
//        user.setRole(true);
//        Driver driver = new Driver();
//        driver.setNumber(234);
//        driver.setFirstName("Valera");
//        driver.setLastName("Mazurkevich");
//        Truck truck = new Truck();
//        truck.setNumber("34DF");
//        truck.setRepairState(true);
//        truck.setShiftSize(3);
//        truck.setCapacity(20);
//        driverService.setStatusByDriverNumberAndStatus(234,DriverStatus.shift);
        Order order = new Order();
        order.setNumber(242);
        order.setDoneState(false);
        Cargo cargo = new Cargo();
        cargo.setNumber(22);
        cargo.setName("absorb");
        cargo.setWeight(30);
        orderService.createCargoAndAssignToOrder(242,cargo,"Moscow","Orel", 1);
        System.out.print("fd");
    }
}
