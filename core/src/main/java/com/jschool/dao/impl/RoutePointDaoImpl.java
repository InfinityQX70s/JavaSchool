package com.jschool.dao.impl;

import com.jschool.dao.api.RoutePointDao;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.Order;
import com.jschool.entities.RoutePoint;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
@Repository
public class RoutePointDaoImpl extends GenericDaoImpl<RoutePoint> implements RoutePointDao{

    private static final Logger LOG = Logger.getLogger(RoutePointDaoImpl.class);

    @Override
    public List<RoutePoint> findByOrder(Order order) throws DaoException {
        try {
            TypedQuery<RoutePoint> query =
                    entityManager.createNamedQuery("RoutePoint.findByOrder", RoutePoint.class);
            query.setParameter("orders", order);
            return query.getResultList();
        }catch (Exception e){
            LOG.error("Unexpected DB exception", e);
            throw  new DaoException(e);
        }
    }
}
