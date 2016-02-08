package com.jschool.dao.api;

import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public interface GenericDao<T>{
    void create(T entity);
    void update(T entity);
    void delete(T entity);
}
