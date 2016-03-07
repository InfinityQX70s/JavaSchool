package com.jschool.dao.impl;

import com.jschool.dao.api.CargoDao;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.Cargo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
@Repository
public class CargoDaoImpl extends GenericDaoImpl<Cargo> implements CargoDao {

    private static final Logger LOG = Logger.getLogger(CargoDaoImpl.class);

    /**Return Cargo finded by unique in DB
     * @param number
     * @return
     * @throws DaoException if something goes wrong
     */
    @Override
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
            LOG.error("Unexpected DB exception", e);
            throw new DaoException(e);
        }
    }

    /** Return all Cargoes named like param.
     * @param name
     * @return
     * @throws DaoException if something goes wrong
     */
    @Override
    public List<Cargo> findAllByName(String name) throws DaoException {
        try {
            TypedQuery<Cargo> query =
                    entityManager.createNamedQuery("Cargo.findByName", Cargo.class);
            query.setParameter("name", name);
            return query.getResultList();
        }catch (Exception e){
            LOG.error("Unexpected DB exception", e);
            throw new DaoException(e);
        }
    }

    /**Return all cargoes finded in DB
     * @return
     * @throws DaoException if something goes wrong
     */
    @Override
    public List<Cargo> findAll() throws DaoException {
        try {
            TypedQuery<Cargo> query =
                    entityManager.createNamedQuery("Cargo.findAll", Cargo.class);
            return query.getResultList();
        }catch (Exception e){
            LOG.error("Unexpected DB exception", e);
            throw  new DaoException(e);
        }
    }
}
