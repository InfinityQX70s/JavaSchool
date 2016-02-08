package com.jschool.dao.api;

import com.jschool.entities.UserEntity;

import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public interface UserDao extends GenericDao<UserEntity>{

    UserEntity findByEmail(String email);
    List<UserEntity> findByRole(boolean isDriver);
    List<UserEntity> findAll();
}
