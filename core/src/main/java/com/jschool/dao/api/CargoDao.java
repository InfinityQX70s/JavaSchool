package com.jschool.dao.api;

import com.jschool.entities.CargoEntity;
import com.jschool.entities.status.CargoStatus;

import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public interface CargoDao extends GenericDao<CargoEntity> {

    void setCargoStatus(int number, CargoStatus status);
    CargoEntity findByNumber(int number);
    CargoEntity findByName(String name);
    List<CargoEntity> findAll();
}
