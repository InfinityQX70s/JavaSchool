package com.jschool.services.api;

import com.jschool.entities.User;
import com.jschool.services.api.exception.ServiceExeption;

import javax.jws.soap.SOAPBinding;

/**
 * Created by infinity on 17.02.16.
 */
public interface UserService {

    User findUserByEmail(String email) throws ServiceExeption;
}
