package com.jschool.dao.impl;

import com.jschool.dao.api.UserDao;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.User;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao{

    private static final Logger LOG = Logger.getLogger(UserDaoImpl.class);

    public UserDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }

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
