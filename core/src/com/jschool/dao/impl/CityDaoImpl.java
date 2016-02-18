package com.jschool.dao.impl;

import com.jschool.dao.api.CityDao;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.City;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public class CityDaoImpl extends GenericDaoImpl<City> implements CityDao{

    public CityDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public City findUniqueByName(String name) throws DaoException {
        try {
            TypedQuery<City> query =
                    entityManager.createNamedQuery("City.findByName", City.class);
            query.setParameter("name", name);
            List<City> cities = query.getResultList();
            City city = null;
            if (!cities.isEmpty())
                city = cities.get(0);
            return city;
        }catch (Exception e){
            throw  new DaoException(e);
        }
    }

    public List<City> findAll() throws DaoException {
        try {
            TypedQuery<City> query =
                    entityManager.createNamedQuery("City.findAll", City.class);
            return query.getResultList();
        }catch (Exception e){
            throw  new DaoException(e);
        }
    }
}
