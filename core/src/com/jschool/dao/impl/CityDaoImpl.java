package com.jschool.dao.impl;

import com.jschool.dao.api.CityDao;
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

    public City findUniqueByName(String name) {
        TypedQuery<City> query =
                entityManager.createNamedQuery("City.findByName", City.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    public List<City> findAll() {
        TypedQuery<City> query =
                entityManager.createNamedQuery("City.findAll", City.class);
        return query.getResultList();
    }
}
