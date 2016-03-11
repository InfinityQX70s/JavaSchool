package com.jschool.controllers;

import com.jschool.controllers.exception.ControllerException;
import com.jschool.controllers.exception.ControllerStatusCode;
import com.jschool.entities.*;
import com.jschool.services.api.DriverService;
import com.jschool.services.api.OrderAndCargoService;
import com.jschool.services.api.TruckService;
import com.jschool.services.api.exception.ServiceException;
import com.jschool.validator.Validator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by infinity on 12.02.16.
 */
@Controller
public class OrderController{

    private static final Logger LOG = Logger.getLogger(OrderController.class);

    @Resource(name="errorProperties")
    private Properties errorProperties;
    private OrderAndCargoService orderAndCargoService;
    private TruckService truckService;
    private DriverService driverService;
    private Validator validator;

    @Autowired
    public OrderController(OrderAndCargoService orderAndCargoService, TruckService truckService, DriverService driverService, Validator validator) {
        this.orderAndCargoService = orderAndCargoService;
        this.truckService = truckService;
        this.driverService = driverService;
        this.validator = validator;
    }

    @RequestMapping(value = "/employee/orders", method = RequestMethod.GET)
    public String showOrders(Model model, RedirectAttributes redirectAttributes){
        try {
            List<Order> orders = orderAndCargoService.findAllOrders();
            Map<Order, List<Cargo>> orderListMap = new HashMap<>();
            for (Order order : orders) {
                List<Cargo> cargos = orderAndCargoService.findAllCargosByOrderNumber(order.getNumber());
                orderListMap.put(order, cargos);
            }
            model.addAttribute("orderListMap", orderListMap);
            return "order/order";
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            redirectAttributes.addFlashAttribute("message", errorProperties.getProperty(e.getStatusCode().name()));
            return "redirect:/employee/orders";
        }
    }

    @RequestMapping(value = "/employee/order/add", method = RequestMethod.GET)
    public String showFormForOrderAdd(){
        return "order/orderAllAdd";
    }

    //   /employee/order/add POST
    @RequestMapping(value = "/employee/order/add", method = RequestMethod.POST)
    public String addOrder(HttpServletRequest req, Model model){
        try {
            //return needed page equals step on wizard and passed params from wizard form
            String stepNumber = req.getParameter("step_number");
            switch (stepNumber) {
                case "1":
                    return "order/orderCargo";
                case "2":
                    String orderNumber = req.getParameter("orderNumber");
                    String[] cargoNumber = req.getParameterValues("cargoNumber");
                    String[] cargoName = req.getParameterValues("cargoName");
                    String[] cargoWeight = req.getParameterValues("cargoWeight");
                    String[] pickup = req.getParameterValues("pickup");
                    String[] unload = req.getParameterValues("unload");
                    validator.validateOrderAndCargo(orderNumber, cargoNumber, cargoName, cargoWeight, pickup, unload);
                    int max = 0;
                    for (String weight : cargoWeight) {
                        if (Integer.parseInt(weight) > max)
                            max = Integer.parseInt(weight);
                    }
                    List<Truck> trucks = truckService.findAllAvailableTrucksByMinCapacity(max);
                    model.addAttribute("trucks", trucks);
                    model.addAttribute("max", max);
                    return "order/orderTruck";
                case "3":
                    String pickupCity[] = req.getParameterValues("pickup");
                    String unloadCity[] = req.getParameterValues("unload");
                    validator.validateCargoCities(pickupCity, unloadCity);
                    List<String> cities = new ArrayList<>();
                    for (int i = 0; i < pickupCity.length; i++) {
                        cities.add(pickupCity[i]);
                        cities.add(unloadCity[i]);
                    }
                    model.addAttribute("cities", cities);
                    return "order/orderMap";
                case "4":
                    String duration = req.getParameter("duration").split(" ")[0];
                    String truckNumber = req.getParameter("truckNumber");
                    validator.validateTruckNumber(truckNumber);
                    Truck truck = truckService.getTruckByNumber(truckNumber);
                    Map<Driver, Integer> driverHoursList = driverService.findAllAvailableDrivers(Integer.parseInt(duration));
                    model.addAttribute("drivers", driverHoursList);
                    model.addAttribute("duration", duration);
                    model.addAttribute("shiftSize", truck.getShiftSize());
                    return "order/orderDrivers";
            }
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
        }catch (ControllerException e){
            LOG.warn(e.getMessage());
        }
        return "redirect:/employee/orders";
    }

    // /employee/order/submit POST
    //@RequestMapping(value = "/employee/order/submit", method = RequestMethod.POST)
    public void submitOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            //get params from form, validate them, fill entities and pass them to
            //service for full order add
            String orderNumber = req.getParameter("orderNumber");
            String[] cargoNumber = req.getParameterValues("cargoNumber");
            String[] cargoName = req.getParameterValues("cargoName");
            String[] cargoWeight = req.getParameterValues("cargoWeight");
            String[] pickup = req.getParameterValues("pickup");
            String[] unload = req.getParameterValues("unload");
            String truckNumber = req.getParameter("truckNumber");
            String[] driverNumbers = req.getParameterValues("driverNumber");
            String duration = req.getParameter("duration").split(" ")[0];
            validator.validateOrderAndCargo(orderNumber,cargoNumber,cargoName,cargoWeight,pickup,unload);
            validator.validateTruckDriversAndDuration(truckNumber,driverNumbers,duration);

            Order order = new Order();
            order.setNumber(Integer.parseInt(orderNumber));
            order.setDoneState(false);
            List<Cargo> cargos = new ArrayList<>();
            for (int i = 0; i < cargoNumber.length; i++) {
                Cargo cargo = new Cargo();
                cargo.setNumber(Integer.parseInt(cargoNumber[i]));
                cargo.setName(cargoName[i]);
                cargo.setWeight(Integer.parseInt(cargoWeight[i]));

                City pickCity = new City();
                pickCity.setName(pickup[i]);

                City unloadCity = new City();
                unloadCity.setName(unload[i]);

                RoutePoint pickRoute = new RoutePoint();
                pickRoute.setPoint(i);
                pickRoute.setCity(pickCity);

                RoutePoint unloadRoute = new RoutePoint();
                unloadRoute.setPoint(i);
                unloadRoute.setCity(unloadCity);

                cargo.setPickup(pickRoute);
                cargo.setUnload(unloadRoute);
                cargos.add(cargo);
            }
            Truck truck = truckService.getTruckByNumber(truckNumber);
            List<Driver> drivers = new ArrayList<>();
            for (String driver : driverNumbers)
                drivers.add(driverService.getDriverByPersonalNumber(Integer.parseInt(driver)));
            order.setTruck(truck);
            order.setDrivers(drivers);

            orderAndCargoService.addOrder(order, cargos, Integer.parseInt(duration));
            resp.sendRedirect("/employee/orders");
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
        }catch (ControllerException e){
            LOG.warn(e.getMessage());
        }
    }

}
