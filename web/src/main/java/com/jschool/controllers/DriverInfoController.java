package com.jschool.controllers;

import com.jschool.controllers.exception.ControllerException;
import com.jschool.controllers.exception.ControllerStatusCode;
import com.jschool.entities.Cargo;
import com.jschool.entities.Driver;
import com.jschool.entities.Order;
import com.jschool.services.api.DriverService;
import com.jschool.services.api.OrderAndCargoService;
import com.jschool.services.api.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by infinity on 17.02.16.
 */
@Controller
public class DriverInfoController{

    private static final Logger LOG = Logger.getLogger(DriverInfoController.class);

    @Resource(name="errorProperties")
    private Properties errorProperties;
    private final OrderAndCargoService orderAndCargoService;
    private final DriverService driverService;

    @Autowired
    public DriverInfoController(DriverService driverService, OrderAndCargoService orderAndCargoService) {
        this.driverService = driverService;
        this.orderAndCargoService = orderAndCargoService;
    }

    @RequestMapping(value = "/driver", method = RequestMethod.GET)
    public String showDriverInfo(Model model){
        try {
            //get full information about driver and pass it to page
            //getting driver from session scope
            String number = SecurityContextHolder.getContext().getAuthentication().getName();
            Driver driver = driverService.getDriverByPersonalNumber(Integer.parseInt(number));
            Order order = driver.getOrder();
            if (order != null){
                model.addAttribute("assign", true);
                List<Cargo> cargos = orderAndCargoService.findAllCargosByOrderNumber(order.getNumber());
                List<Driver> drivers = order.getDrivers();
                List<String> cities = new ArrayList<>();
                for (Cargo cargo : cargos) {
                    cities.add(cargo.getPickup().getCity().getName());
                    cities.add(cargo.getUnload().getCity().getName());
                }
                model.addAttribute("cities", cities);
                model.addAttribute("driver", driver);
                model.addAttribute("cargos", cargos);
                model.addAttribute("drivers", drivers);
                model.addAttribute("order", order);
            }else{
                model.addAttribute("assign", false);
                model.addAttribute("driver", driver);
            }
            return "driverInfo";
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            model.addAttribute("error", errorProperties.getProperty(e.getStatusCode().name()));
            return "driverInfo";
        }

    }
}
