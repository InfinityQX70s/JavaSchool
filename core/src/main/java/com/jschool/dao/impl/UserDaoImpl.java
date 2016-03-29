package com.jschool.dao.impl;

import com.jschool.dao.api.UserDao;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
@Repository
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao{

    private static final Logger LOG = Logger.getLogger(UserDaoImpl.class);

    @Override
    public User findUniqueByEmail(String email) throws DaoException {
        try {
            TypedQuery<User> query =
                    entityManager.createNamedQuery("User.findByEmail", User.class);
            query.setParameter("email", email);
            List<User> users = query.getResultList();
            User user = null;
            if (!users.isEmpty())
                user = users.get(0);
            return user;
        }catch (Exception e){
            LOG.error("Unexpected DB exception", e);
            throw  new DaoException(e);
        }
    }


    @Override
    public List<User> findAllByRole(boolean isDriver) throws DaoException {
        try {
            TypedQuery<User> query =
                    entityManager.createNamedQuery("User.findByRole", User.class);
            query.setParameter("role", isDriver);
            return query.getResultList();
        }catch (Exception e){
            LOG.error("Unexpected DB exception", e);
            throw  new DaoException(e);
        }
    }

    @Override
    public List<User> findAll() throws DaoException {
        try {
            TypedQuery<User> query =
                    entityManager.createNamedQuery("User.findAll", User.class);
            return query.getResultList();
        }catch (Exception e){
            LOG.error("Unexpected DB exception", e);
            throw  new DaoException(e);
        }
    }
}
