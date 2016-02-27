package com.jschool.controllers;

import com.jschool.AppContext;
import com.jschool.controllers.exception.ControllerException;
import com.jschool.controllers.exception.ControllerStatusCode;
import com.jschool.entities.Cargo;
import com.jschool.entities.Driver;
import com.jschool.entities.Order;
import com.jschool.services.api.OrderAndCargoService;
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
 * Created by infinity on 17.02.16.
 */
public class DriverInfoController extends BaseController {

    private static final Logger LOG = Logger.getLogger(DriverInfoController.class);

    private OrderAndCargoService orderAndCargoService;

    public DriverInfoController(Properties errorProperties, OrderAndCargoService orderAndCargoService) {
        super(errorProperties);
        this.orderAndCargoService = orderAndCargoService;
    }

    @Override
    public void execute(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String[] uri = request.getRequestURI().split("/");
            if ("GET".equals(request.getMethod())) {
                if (uri.length == 3 && "driver".equals(uri[2]))
                    showDriverInfo(request, response);
                else
                    throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
            }
        } catch (ControllerException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }
    }

    public void showDriverInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Driver driver = (Driver) req.getSession().getAttribute("entity");
            Order order = driver.getOrder();
            if (order != null){
                req.setAttribute("assign", true);
                List<Cargo> cargos = orderAndCargoService.findAllCargosByOrderNumber(order.getNumber());
                List<Driver> drivers = order.getDrivers();
                req.setAttribute("driver", driver);
                req.setAttribute("cargos", cargos);
                req.setAttribute("drivers", drivers);
                req.setAttribute("order", order);
            }else{
                req.setAttribute("assign", false);
                req.setAttribute("driver", driver);
            }
            req.getRequestDispatcher("/WEB-INF/pages/driverInfo.jsp").forward(req, resp);
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,req,resp);
        }

    }
}
