package com.jschool.services.impl;

import com.jschool.dao.api.DriverAuthCodeDao;
import com.jschool.dao.api.DriverDao;
import com.jschool.dao.api.UserDao;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.Driver;
import com.jschool.entities.DriverAuthCode;
import com.jschool.entities.User;
import com.jschool.services.api.UserService;
import com.jschool.services.api.exception.ServiceException;
import com.jschool.services.api.exception.ServiceStatusCode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infinity on 17.02.16.
 */
@Service
public class UserServiceImpl implements UserService{

    private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);

    private UserDao userDao;
    private DriverDao driverDao;
    private DriverAuthCodeDao driverAuthCodeDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, DriverDao driverDao, DriverAuthCodeDao driverAuthCodeDao) {
        this.userDao = userDao;
        this.driverDao = driverDao;
        this.driverAuthCodeDao = driverAuthCodeDao;
    }

    /**
     * Return unique user by email address or throw ServiceException with status code
     * USER_NOT_FOUND
     *
     * @param email
     * @return
     * @throws ServiceException
     */
    @Override
    public User findUserByEmail(String email) throws ServiceException {
        try {
            User user = userDao.findUniqueByEmail(email);
            if (user == null) {
                throw new ServiceException("User not found", ServiceStatusCode.USER_NOT_FOUND);
            }
            return user;
        } catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            User user = userDao.findUniqueByEmail(email);
            if (user == null || user.isRole()) {
                throw new UsernameNotFoundException(email);
            } else {
                GrantedAuthority userRole = new SimpleGrantedAuthority("ROLE_EMPLOYEE");
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(userRole);
                return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
            }
        } catch (DaoException e) {
            LOG.warn("User Not Found Exception", e);
            throw new UsernameNotFoundException(email);
        }
    }

    public UserDetails loadUserByUsername(int number) throws UsernameNotFoundException {
        try {
            Driver driver = driverDao.findUniqueByNumber(number);
            if (driver == null) {
                throw new UsernameNotFoundException(String.valueOf(number));
            } else {
                DriverAuthCode driverAuthCode = driverAuthCodeDao.findLastCode(driver);
                if (driverAuthCode == null){
                    throw new UsernameNotFoundException("Driver verify code not found");
                }
                String verifyCode = String.valueOf(driverAuthCode.getCode());
                GrantedAuthority userRole = new SimpleGrantedAuthority("ROLE_DRIVER");
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(userRole);
                return new org.springframework.security.core.userdetails.User(String.valueOf(number), verifyCode, authorities);
            }
        } catch (DaoException | NumberFormatException e) {
            LOG.warn("User Not Found Exception", e);
            throw new UsernameNotFoundException(String.valueOf(number));
        }
    }
}
