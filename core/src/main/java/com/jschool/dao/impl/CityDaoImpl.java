package com.jschool.dao.impl;

import com.jschool.dao.api.CityDao;
import com.jschool.entities.CityEntity;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public class CityDaoImpl extends GenericDaoImpl<CityEntity> implements CityDao{

    public CityEntity findByName(String name) {
        TypedQuery<CityEntity> query =
                entityManager.createNamedQuery("City.findByName", CityEntity.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    public List<CityEntity> findAll() {
        TypedQuery<CityEntity> query =
                entityManager.createNamedQuery("City.findAll", CityEntity.class);
        return query.getResultList();
    }
}
