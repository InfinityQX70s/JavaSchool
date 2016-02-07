package com.jschool.dao.impl;

import com.jschool.dao.api.UserDao;
import com.jschool.entities.UserEntity;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public class UserDaoImpl extends GenericDaoImpl<UserEntity> implements UserDao{

    public UserEntity findByEmail(String email) {
        TypedQuery<UserEntity> query =
                entityManager.createNamedQuery("User.findByEmail", UserEntity.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    public List<UserEntity> findAll() {
        TypedQuery<UserEntity> query =
                entityManager.createNamedQuery("User.findAll", UserEntity.class);
        return query.getResultList();
    }
}
