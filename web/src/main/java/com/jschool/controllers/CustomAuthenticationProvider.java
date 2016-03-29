package com.jschool.controllers;

import com.jschool.services.api.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by infinity on 10.03.16.
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private UserService userService;
    private HttpServletRequest request;

    @Autowired
    public CustomAuthenticationProvider(UserService userService, HttpServletRequest request) {
        this.userService = userService;
        this.request = request;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = DigestUtils.md5Hex((String) authentication.getCredentials());
        String verifyCode = request.getParameter("verifyCode");
        String number = request.getParameter("number");
        UserDetails userDetails;
        if (!email.isEmpty() && number.isEmpty()) {
            userDetails = userService.loadUserByUsername(email);
            if (userDetails == null)
                throw new BadCredentialsException("Invalid username or password");
            if (!password.equals(userDetails.getPassword()))
                throw new BadCredentialsException("Invalid username or password");
            return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        } else if (!number.isEmpty() && email.isEmpty()) {
            try {
                userDetails = userService.loadUserByUsername(Integer.parseInt(number));
                if (userDetails == null)
                    throw new BadCredentialsException("Driver not found");
                if (!verifyCode.equals(userDetails.getPassword()))
                    throw new BadCredentialsException("Invalid driver verify code");
                return new UsernamePasswordAuthenticationToken(userDetails, verifyCode, userDetails.getAuthorities());
            } catch (NumberFormatException e) {
                throw new BadCredentialsException("Driver not found");
            }
        } else {
            throw new BadCredentialsException("Undefined login");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
