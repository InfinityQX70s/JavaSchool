package com.jschool.dao.api;

import com.jschool.entities.CityEntity;

import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public interface CityDao extends GenericDao<CityEntity>{

    CityEntity findByName(String name);
    List<CityEntity> findAll();
}
