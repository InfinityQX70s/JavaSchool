package com.jschool.controllers;

import com.jschool.controllers.exception.ControllerException;
import com.jschool.controllers.exception.ControllerStatusCode;
import com.jschool.entities.Driver;
import com.jschool.entities.User;
import com.jschool.services.api.DriverService;
import com.jschool.services.api.UserService;
import com.jschool.services.api.exception.ServiceException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by infinity on 12.02.16.
 */
public class LoginController extends BaseController {

    private static final Logger LOG = Logger.getLogger(LoginController.class);

    private DriverService driverService;
    private UserService userService;
    private Validator validator;

    public LoginController(Properties errorProperties,  DriverService driverService, UserService userService, Validator validator) {
        super(errorProperties);
        this.driverService = driverService;
        this.userService = userService;
        this.validator = validator;
    }

    @Override
    public void execute(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //split uri by "/" and check url on correct and pass it to needed method
            // by uri and "get" or "post" method
            String[] uri = request.getRequestURI().split("/");
            if ("GET".equals(request.getMethod())) {
                if (uri.length == 3 && "login".equals(uri[2]))
                    showUserLoginForm(request, response);
                else if (uri.length == 3 && "logout".equals(uri[2]))
                    logoutUser(request, response);
                else
                    throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
            }
            if ("POST".equals(request.getMethod())) {
                if (uri.length == 3 && "login".equals(uri[2]))
                    loginUser(request, response);
                else
                    throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
            }
        } catch (ControllerException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }
    }

    // /login GET
    private void showUserLoginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
    }

    // /login POST
    private void loginUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            // get params from form and check who want to login
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String number = req.getParameter("number");
            // employer login, find user by email and equals passwords in md5 hash
            if (!email.isEmpty() && number.isEmpty()) {
                validator.validateEmail(email);
                User user = userService.findUserByEmail(email);
                if (user != null && !user.isRole() && DigestUtils.md5Hex(password).equals(user.getPassword())) {
                    //set attribute in session scope
                    req.getSession().setAttribute("role", "employee");
                    req.getSession().setAttribute("entity", user);
                    resp.sendRedirect("/employee/orders");
                } else {
                    throw new ControllerException("Wrong email or pass", ControllerStatusCode.WRONG_EMAIL_OR_PASS);
                }
            } else if (!number.isEmpty() && email.isEmpty()) {
                //find driver and set attribute in session scope
                validator.validateDriverNumber(number);
                Driver driver = driverService.getDriverByPersonalNumber(Integer.parseInt(number));
                if (driver != null) {
                    req.getSession().setAttribute("role", "driver");
                    req.getSession().setAttribute("entity", driver);
                    resp.sendRedirect("/driver");
                } else {
                    throw new ControllerException("Wrong driver number", ControllerStatusCode.WRONG_DRIVER);
                }
            } else {
                throw new ControllerException("Undefined login", ControllerStatusCode.UNDEFINED_LOGIN);
            }
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,req,resp);
        }catch (ControllerException e){
            LOG.warn(e.getMessage());
            showError(e,req,resp);
        }
    }

    // /logout GET
    private void logoutUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().invalidate();
        resp.sendRedirect("/login");
    }
}
