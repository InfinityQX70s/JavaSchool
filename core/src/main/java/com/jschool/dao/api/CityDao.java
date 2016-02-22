package com.jschool.dao.api;

import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.City;

import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public interface CityDao extends GenericDao<City>{

    City findUniqueByName(String name) throws DaoException;
    List<City> findAll() throws DaoException;
}
