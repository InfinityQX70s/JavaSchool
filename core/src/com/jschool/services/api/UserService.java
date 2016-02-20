package com.jschool.services.api;

import com.jschool.entities.User;
import com.jschool.services.api.exception.ServiceException;

/**
 * Created by infinity on 17.02.16.
 */
public interface UserService {

    User findUserByEmail(String email) throws ServiceException;
}
