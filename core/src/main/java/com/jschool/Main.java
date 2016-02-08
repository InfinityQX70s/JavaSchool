package com.jschool;

import com.jschool.dao.api.CargoDao;
import com.jschool.dao.api.CargoStatusLogDao;
import com.jschool.dao.impl.CargoDaoImpl;
import com.jschool.dao.impl.CargoStatusLogDaoImpl;
import com.jschool.dao.impl.UserDaoImpl;
import com.jschool.entities.CargoEntity;
import com.jschool.entities.CargoStatusLogEntity;
import com.jschool.entities.UserEntity;
import com.jschool.entities.status.CargoStatus;
import com.jschool.services.impl.CargoServiceImpl;
import com.jschool.services.impl.TruckServiceImpl;
import com.jschool.services.impl.UserServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by infinity on 06.02.16.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Main");
        CargoServiceImpl a = new CargoServiceImpl();
        a.setCargoStatus(9,CargoStatus.delivered);
    }
}
