package com.jschool.services.api;

import com.jschool.entities.City;
import com.jschool.services.api.exception.ServiceException;

import java.util.List;

/**
 * Created by infinity on 10.03.16.
 */
public interface ApiService {

    List<City> findAllCitiesByName(String query) throws ServiceException;
}
