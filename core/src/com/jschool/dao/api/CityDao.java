package com.jschool.dao.api;

import com.jschool.entities.City;

import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public interface CityDao extends GenericDao<City>{

    City findUniqueByName(String name);
    List<City> findAll();
}
