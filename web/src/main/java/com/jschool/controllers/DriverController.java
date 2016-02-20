package com.jschool.controllers;

import com.jschool.AppContext;
import com.jschool.Validator;
import com.jschool.controllers.exception.ControllerException;
import com.jschool.entities.Driver;
import com.jschool.entities.User;
import com.jschool.services.api.DriverService;
import com.jschool.services.api.exception.ServiceException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by infinity on 12.02.16.
 */
public class DriverController implements Command{

    private AppContext appContext = AppContext.getInstance();
    private DriverService driverService = appContext.getDriverService();
    private Validator validator = appContext.getValidator();


    public void execute(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] uri = request.getRequestURI().split("/");
        if (request.getMethod().equals("GET")){
            if (uri.length == 4 && uri[3].equals("drivers"))
                showDrivers(request,response);
            else if (uri.length == 5 && uri[4].equals("add"))
                showFormForDriverAdd(request,response);
            else if (uri.length == 6 && uri[5].equals("edit"))
                showFormForChangeDriver(request,response,uri[4]);
        }
        if (request.getMethod().equals("POST")){
            if (uri.length == 5 && uri[4].equals("add"))
                addDriver(request,response);
            else if (uri.length == 5 && uri[4].equals("delete"))
                deleteDriver(request,response);
            else if (uri.length == 5 && uri[4].equals("change"))
                changeDriver(request,response);
        }

    }

//    /employee/drivers/ GET
    public void showDrivers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Driver> drivers = driverService.findAllDrivers();
            req.setAttribute("drivers",drivers);
            req.getRequestDispatcher("/WEB-INF/pages/driver/driver.jsp").forward(req, resp);
        }catch (ServiceException e){
            req.setAttribute("error",e);
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
        }
    }
 //   /employee/driver/add GET
    public void showFormForDriverAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/driver/driverAdd.jsp").forward(req, resp);
    }
 //   /employee/driver/add POST
    public void addDriver(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String number = req.getParameter("number");
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");
            String email = req.getParameter("email");
            validator.validateEmail(email);
            validator.validateDriverNumber(number);
            User user = new User();
            user.setEmail(email);
            user.setPassword(email);
            user.setRole(true);
            Driver driver = new Driver();
            driver.setNumber(Integer.parseInt(number));
            driver.setFirstName(firstName);
            driver.setLastName(lastName);
            driver.setUser(user);
            driverService.addDriver(driver);
            resp.sendRedirect("/employee/drivers");
        }catch (ServiceException | ControllerException e){
            req.setAttribute("error",e);
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
        }
    }

 //   /employee/driver/delete POST
    public void deleteDriver(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String number = req.getParameter("number");
            validator.validateDriverNumber(number);
            driverService.deleteDriver(Integer.parseInt(number));
            resp.sendRedirect("/employee/drivers");
        }catch (ServiceException | ControllerException e){
            req.setAttribute("error",e);
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
        }
    }

 //   /employee/driver/{number}/edit GET
    public void showFormForChangeDriver(HttpServletRequest req, HttpServletResponse resp, String number) throws ServletException, IOException {
        try {
            validator.validateDriverNumber(number);
            Driver driver = driverService.getDriverByPersonalNumber(Integer.parseInt(number));
            req.setAttribute("driver",driver);
            req.getRequestDispatcher("/WEB-INF/pages/driver/driverEdit.jsp").forward(req, resp);
        }catch (ServiceException | ControllerException e){
            req.setAttribute("error",e);
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
        }
    }

 //   /employee/driver/change POST
    public void changeDriver(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String number = req.getParameter("number");
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");
            validator.validateDriverNumber(number);
            Driver driver = new Driver();
            driver.setNumber(Integer.parseInt(number));
            driver.setFirstName(firstName);
            driver.setLastName(lastName);
            driverService.updateDrive(driver);
            resp.sendRedirect("/employee/drivers");
        }catch (ServiceException | ControllerException e){
            req.setAttribute("error",e);
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
        }
    }
}
