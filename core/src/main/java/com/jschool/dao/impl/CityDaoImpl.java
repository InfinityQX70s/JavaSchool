package com.jschool.dao.impl;

import com.jschool.dao.api.CityDao;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.City;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public class CityDaoImpl extends GenericDaoImpl<City> implements CityDao{

    private static final Logger LOG = Logger.getLogger(CityDaoImpl.class);

    public CityDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }


    /**Return unique city find by name.
     * @param name
     * @return
     * @throws DaoException if something goes wrong with JDBC
     */
    @Override
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
            LOG.error("Unexpected DB exception", e);
            throw  new DaoException(e);
        }
    }

    /**Return all cities in DB
     * @return
     * @throws DaoException
     */
    @Override
    public List<City> findAll() throws DaoException {
        try {
            TypedQuery<City> query =
                    entityManager.createNamedQuery("City.findAll", City.class);
            return query.getResultList();
        }catch (Exception e){
            LOG.error("Unexpected DB exception", e);
            throw  new DaoException(e);
        }
    }
}
