package com.jschool.services.impl;

import com.jschool.dao.api.CityDao;
import com.jschool.entities.City;
import com.jschool.services.api.CityService;

import java.util.List;

/**
 * Created by infinity on 13.02.16.
 */
public class CityServiceImpl implements CityService {

    private CityDao cityDao;

    public CityServiceImpl(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    public List<City> findAllCities() {
        return cityDao.findAll();
    }
}
