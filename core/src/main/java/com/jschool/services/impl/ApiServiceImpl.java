package com.jschool.services.impl;

import com.jschool.dao.api.CityDao;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.City;
import com.jschool.services.api.ApiService;
import com.jschool.services.api.exception.ServiceException;
import com.jschool.services.api.exception.ServiceStatusCode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by infinity on 10.03.16.
 */
@Service
public class ApiServiceImpl implements ApiService{

    private static final Logger LOG = Logger.getLogger(ApiServiceImpl.class);

    private CityDao cityDao;

    @Autowired
    public ApiServiceImpl(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    @Override
    @Transactional(rollbackFor=ServiceException.class)
    public List<City> findAllCitiesByName(String query) throws ServiceException {
        try {
            return cityDao.findAllByName(query);
        }catch (DaoException e){
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }
}
