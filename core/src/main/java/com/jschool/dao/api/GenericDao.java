package com.jschool.dao.api;

import com.jschool.dao.api.exception.DaoException;

/**
 * Created by infinity on 07.02.16.
 */
public interface GenericDao<T>{
    void create(T entity) throws DaoException;
    void update(T entity) throws DaoException;
    void delete(T entity) throws DaoException;
}
