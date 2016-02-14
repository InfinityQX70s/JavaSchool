package com.jschool.services.api;

import com.jschool.entities.City;

import java.util.List;

/**
 * Created by infinity on 13.02.16.
 */
public interface CityService {

    List<City> findAllCities();
}
