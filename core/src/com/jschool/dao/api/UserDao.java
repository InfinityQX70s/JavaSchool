package com.jschool.dao.api;

import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.User;

import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public interface UserDao extends GenericDao<User>{

    User findUniqueByEmail(String email) throws DaoException;
    List<User> findAllByRole(boolean isDriver) throws DaoException;
    List<User> findAll() throws DaoException;
}
