package com.jschool.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * Created by infinity on 08.03.16.
 */
@Controller
public class FrontController {

    @RequestMapping(value = "/sign", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/sign?sign_out=true";
    }

    @RequestMapping("/")
    public String commonFrontPage() {
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder
                .getContext().getAuthentication().getAuthorities();

        GrantedAuthority employeeRole = new SimpleGrantedAuthority("ROLE_EMPLOYEE");
        GrantedAuthority driverRole = new SimpleGrantedAuthority("ROLE_DRIVER");
        GrantedAuthority userRole = new SimpleGrantedAuthority("ROLE_ADMIN");

        if (authorities.contains(employeeRole)) {
            return "redirect:/employee/orders";
        }else if (authorities.contains(driverRole)) {
            return "redirect:/driver";
        }else if (authorities.contains(userRole)) {
            return "redirect:/admin/users";
        }else
            return "redirect:/sign";
    }

}
