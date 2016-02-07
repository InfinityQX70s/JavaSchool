package com.jschool.dao.api;

import com.jschool.entities.CargoEntity;

import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public interface CargoDao extends GenericDao<CargoEntity> {

    CargoEntity findByNumber(int number);
    CargoEntity findByName(String name);
    List<CargoEntity> findAll();
}
