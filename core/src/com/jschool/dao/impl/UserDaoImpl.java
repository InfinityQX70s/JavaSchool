package com.jschool.dao.impl;

import com.jschool.dao.api.UserDao;
import com.jschool.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao{


    public UserDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public User findUniqueByEmail(String email) {
        TypedQuery<User> query =
                entityManager.createNamedQuery("User.findByEmail", User.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    public List<User> findAllByRole(boolean isDriver) {
        TypedQuery<User> query =
                entityManager.createNamedQuery("User.findByRole", User.class);
        query.setParameter("role", isDriver);
        return query.getResultList();
    }

    public List<User> findAll() {
        TypedQuery<User> query =
                entityManager.createNamedQuery("User.findAll", User.class);
        return query.getResultList();
    }
}
