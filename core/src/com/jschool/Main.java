package com.jschool;

import com.jschool.dao.api.DriverDao;
import com.jschool.dao.api.DriverStatisticDao;
import com.jschool.dao.impl.DriverDaoImpl;
import com.jschool.dao.impl.DriverStatisticDaoImpl;
import com.jschool.entities.Cargo;
import com.jschool.entities.Driver;
import com.jschool.entities.DriverStatistic;
import com.jschool.services.impl.CargoServiceImpl;

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
        DriverStatisticDao driverStatisticDao = new DriverStatisticDaoImpl(entityManager);
        DriverDao driverDao = new DriverDaoImpl(entityManager);
        Driver driver = driverDao.findUniqueByNumber(1);
        List<DriverStatistic> driverStatistics = driverStatisticDao.findAllByOneMonth(driver);
        for (DriverStatistic driverStatistic : driverStatistics){
            System.out.println(driverStatistic.getHoursWorked());
        }
    }
}
