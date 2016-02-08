package com.jschool.services.impl;

import com.jschool.dao.api.CargoDao;
import com.jschool.dao.api.CargoStatusLogDao;
import com.jschool.dao.impl.CargoDaoImpl;
import com.jschool.dao.impl.CargoStatusLogDaoImpl;
import com.jschool.entities.Cargo;
import com.jschool.entities.CargoStatus;
import com.jschool.entities.CargoStatusLog;

import java.util.Date;
import java.util.List;

/**
 * Created by infinity on 08.02.16.
 */
public class CargoServiceImpl {

    private CargoDao cargoDao;
    private CargoStatusLogDao cargoStatusLogDao;

    public CargoServiceImpl(CargoDao cargoDao, CargoStatusLogDao cargoStatusLogDao) {
        this.cargoDao = cargoDao;
        this.cargoStatusLogDao = cargoStatusLogDao;
    }

    public void create(Cargo cargo){
        cargoDao.create(cargo);
        CargoStatusLog cargoStatusLogEntity = new CargoStatusLog();
        cargoStatusLogEntity.setStatus(CargoStatus.ready);
        cargoStatusLogEntity.setTimestamp(new Date());
        cargoStatusLogEntity.setCargo(cargo);
        cargoStatusLogDao.create(cargoStatusLogEntity);
    }

    public void update(Cargo cargo){
        cargoDao.update(cargo);
    }

    public void delete(Cargo cargo){
        cargoDao.delete(cargo);
    }

    public void setCargoStatus(int number, CargoStatus status){
        Cargo cargo = findByNumber(number);
        CargoStatusLog cargoStatusLogEntity = new CargoStatusLog();
        cargoStatusLogEntity.setStatus(status);
        cargoStatusLogEntity.setTimestamp(new Date());
        cargoStatusLogEntity.setCargo(cargo);
        cargoStatusLogDao.create(cargoStatusLogEntity);
    }

    public Cargo findByNumber(int number) {
        return cargoDao.findUniqueByNumber(number);
    }

    public List<Cargo> findAll(){
        return cargoDao.findAll();
    }
}

