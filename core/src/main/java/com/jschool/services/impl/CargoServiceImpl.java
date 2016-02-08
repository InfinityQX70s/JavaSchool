package com.jschool.services.impl;

import com.jschool.dao.api.CargoDao;
import com.jschool.dao.api.CargoStatusLogDao;
import com.jschool.dao.impl.CargoDaoImpl;
import com.jschool.dao.impl.CargoStatusLogDaoImpl;
import com.jschool.entities.CargoEntity;
import com.jschool.entities.CargoStatusLogEntity;
import com.jschool.entities.status.CargoStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by infinity on 08.02.16.
 */
public class CargoServiceImpl {

    private CargoDao cargoDao;
    private CargoStatusLogDao cargoStatusLogDao;

    public CargoServiceImpl() {
        cargoDao = new CargoDaoImpl();
        cargoStatusLogDao = new CargoStatusLogDaoImpl();
    }

    public void create(CargoEntity cargo){
        cargoDao.create(cargo);
    }

    public void update(CargoEntity cargo){
        cargoDao.update(cargo);
    }

    public void delete(CargoEntity cargo){
        cargoDao.delete(cargo);
    }

    public void setCargoStatus(int number, CargoStatus status){
        cargoDao.setCargoStatus(number,status);
    }

    public CargoEntity findByNumber(int number) {
        return cargoDao.findByNumber(number);
    }

    public List<CargoEntity> findAll(){
        return cargoDao.findAll();
    }
}

