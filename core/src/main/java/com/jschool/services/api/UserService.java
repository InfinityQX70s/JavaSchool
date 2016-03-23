package com.jschool.services.api;

import com.jschool.entities.User;
import com.jschool.services.api.exception.ServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by infinity on 17.02.16.
 */
public interface UserService {

    User findUserByEmail(String email) throws ServiceException;
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
    UserDetails loadUserByUsername(int number) throws UsernameNotFoundException;
}
