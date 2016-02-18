package com.jschool.dao.impl;

import com.jschool.dao.api.CargoDao;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.Cargo;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public class CargoDaoImpl extends GenericDaoImpl<Cargo> implements CargoDao {

    public CargoDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public Cargo findUniqueByNumber(int number) throws DaoException {
        try {
            TypedQuery<Cargo> query =
                    entityManager.createNamedQuery("Cargo.findByNumber", Cargo.class);
            query.setParameter("number", number);
            List<Cargo> cargos = query.getResultList();
            Cargo cargo = null;
            if (!cargos.isEmpty())
                cargo = cargos.get(0);
            return cargo;
        }catch (Exception e){
            throw new DaoException(e);
        }
    }

    public List<Cargo> findAllByName(String name) throws DaoException {
        try {
            TypedQuery<Cargo> query =
                    entityManager.createNamedQuery("Cargo.findByName", Cargo.class);
            query.setParameter("name", name);
            return query.getResultList();
        }catch (Exception e){
            throw new DaoException(e);
        }
    }

    public List<Cargo> findAll() throws DaoException {
        try {
            TypedQuery<Cargo> query =
                    entityManager.createNamedQuery("Cargo.findAll", Cargo.class);
            return query.getResultList();
        }catch (Exception e){
            throw  new DaoException(e);
        }
    }
}
