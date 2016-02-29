package com.jschool.controllers;

import com.jschool.AppContext;
import com.jschool.Validator;
import com.jschool.controllers.exception.ControllerException;
import com.jschool.controllers.exception.ControllerStatusCode;
import com.jschool.entities.Driver;
import com.jschool.entities.User;
import com.jschool.services.api.DriverService;
import com.jschool.services.api.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Created by infinity on 12.02.16.
 */
public class DriverController extends BaseController {

    private static final Logger LOG = Logger.getLogger(DriverController.class);

    private DriverService driverService;

    private Validator validator;

    public DriverController(DriverService driverService,Properties errorProperties, Validator validator) {
        super(errorProperties);
        this.driverService = driverService;
        this.validator = validator;
    }

    @Override
    public void execute(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //split uri by "/" and check url on correct and pass it to needed method
            // by uri and "get" or "post" method
            String[] uri = request.getRequestURI().split("/");
            if ("GET".equals(request.getMethod())) {
                if (uri.length == 4 && "drivers".equals(uri[3]))
                    showDrivers(request, response);
                else if (uri.length == 5 && "add".equals(uri[4]))
                    showFormForDriverAdd(request, response);
                else if (uri.length == 6 && "edit".equals(uri[5]))
                    showFormForChangeDriver(request, response, uri[4]);
                else
                    throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
            }
            if ("POST".equals(request.getMethod())) {
                if (uri.length == 5 && "add".equals(uri[4]))
                    addDriver(request, response);
                else if (uri.length == 5 && "delete".equals(uri[4]))
                    deleteDriver(request, response);
                else if (uri.length == 5 && "change".equals(uri[4]))
                    changeDriver(request, response);
                else
                    throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
            }
        } catch (ControllerException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }
    }

    //    /employee/drivers/ GET
    public void showDrivers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Driver> drivers = driverService.findAllDrivers();
            req.setAttribute("drivers", drivers);
            req.getRequestDispatcher("/WEB-INF/pages/driver/driver.jsp").forward(req, resp);
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,req,resp);
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
            //get fields from form validate them and fill entity, pass it to service
            validator.validateEmail(email);
            validator.validateDriverNumber(number);
            validator.validateFirstAndLastName(firstName, lastName);
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
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,req,resp);
        }catch (ControllerException e){
            LOG.warn(e.getMessage());
            showError(e,req,resp);
        }
    }

    //   /employee/driver/delete POST
    public void deleteDriver(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String number = req.getParameter("number");
            validator.validateDriverNumber(number);
            driverService.deleteDriver(Integer.parseInt(number));
            resp.sendRedirect("/employee/drivers");
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,req,resp);
        }catch (ControllerException e){
            LOG.warn(e.getMessage());
            showError(e,req,resp);
        }
    }

    //   /employee/driver/{number}/edit GET
    public void showFormForChangeDriver(HttpServletRequest req, HttpServletResponse resp, String number) throws ServletException, IOException {
        try {
            validator.validateDriverNumber(number);
            Driver driver = driverService.getDriverByPersonalNumber(Integer.parseInt(number));
            req.setAttribute("driver", driver);
            req.getRequestDispatcher("/WEB-INF/pages/driver/driverEdit.jsp").forward(req, resp);
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,req,resp);
        }catch (ControllerException e){
            LOG.warn(e.getMessage());
            showError(e,req,resp);
        }
    }

    //   /employee/driver/change POST
    public void changeDriver(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String number = req.getParameter("number");
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");
            validator.validateDriverNumber(number);
            validator.validateFirstAndLastName(firstName, lastName);
            Driver driver = new Driver();
            driver.setNumber(Integer.parseInt(number));
            driver.setFirstName(firstName);
            driver.setLastName(lastName);
            driverService.updateDrive(driver);
            resp.sendRedirect("/employee/drivers");
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,req,resp);
        }catch (ControllerException e){
            LOG.warn(e.getMessage());
            showError(e,req,resp);
        }
    }


}
