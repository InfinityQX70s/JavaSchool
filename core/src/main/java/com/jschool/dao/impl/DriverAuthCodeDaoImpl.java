package com.jschool.dao.impl;

import com.jschool.dao.api.DriverAuthCodeDao;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.Driver;
import com.jschool.entities.DriverAuthCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by infinity on 19.03.16.
 */
@Repository
public class DriverAuthCodeDaoImpl extends GenericDaoImpl<DriverAuthCode> implements DriverAuthCodeDao {

    private static final Logger LOG = Logger.getLogger(DriverAuthCodeDaoImpl.class);

    @Override
    public List<DriverAuthCode> findLastCode(Driver driver) throws DaoException {
        try {
            TypedQuery<DriverAuthCode> query =
                    entityManager.createNamedQuery("DriverAuthCode.findLastCode", DriverAuthCode.class);
            query.setParameter("driver", driver);
            return query.getResultList();
        }catch (Exception e){
            LOG.error("Unexpected DB exception", e);
            throw  new DaoException(e);
        }
    }
}
