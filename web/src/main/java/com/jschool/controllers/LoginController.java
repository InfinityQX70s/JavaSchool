package com.jschool.controllers;

import com.jschool.AppContext;
import com.jschool.Validator;
import com.jschool.controllers.exception.ControllerException;
import com.jschool.controllers.exception.ControllerStatusCode;
import com.jschool.entities.Driver;
import com.jschool.entities.User;
import com.jschool.services.api.DriverService;
import com.jschool.services.api.UserService;
import com.jschool.services.api.exception.ServiceException;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by infinity on 12.02.16.
 */
public class LoginController implements Command {

    private AppContext appContext = AppContext.getInstance();
    private DriverService driverService = appContext.getDriverService();
    private UserService userService = appContext.getUserService();
    private Validator validator = appContext.getValidator();

    public void execute(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] uri = request.getRequestURI().split("/");
        if (request.getMethod().equals("GET")){
            if (uri.length == 3 && uri[2].equals("login"))
                showUserLoginForm(request,response);
            else if (uri.length == 3 && uri[2].equals("logout"))
                logoutUser(request,response);
        }
        if (request.getMethod().equals("POST")){
            if (uri.length == 3 && uri[2].equals("login"))
                loginUser(request,response);

        }
    }

   // /login GET
    private void showUserLoginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
    }

    // /login POST
    private void loginUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String number = req.getParameter("number");
            if (!email.isEmpty() && number.isEmpty()){
                validator.validateEmail(email);
                User user = userService.findUserByEmail(email);
                if (user != null && !user.isRole() && DigestUtils.md5Hex(password).equals(user.getPassword())){
                    req.getSession().setAttribute("role","employee");
                    resp.sendRedirect("/employee/orders");
                }else {
                    throw new ControllerException("Wrong email or pass", ControllerStatusCode.WRONG_EMAIL_OR_PASS);
                }
            }else if (!number.isEmpty() && email.isEmpty()){
                validator.validateDriverNumber(number);
                Driver driver = driverService.getDriverByPersonalNumber(Integer.parseInt(number));
                if (driver != null) {
                    req.getSession().setAttribute("role", "driver");
                    resp.sendRedirect("/driver");
                }else {
                    throw new ControllerException("Wrong driver number", ControllerStatusCode.WRONG_DRIVER);
                }
            }else {
                throw new ControllerException("Undefined login", ControllerStatusCode.UNDEFINED_LOGIN);
            }
        }catch (ServiceException | ControllerException e){
            req.setAttribute("error",e);
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
        }
    }

    // /logout GET
    private void logoutUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().invalidate();
        resp.sendRedirect("/login");
    }
}
