package com.jschool.controllers;

import com.jschool.controllers.exception.ControllerException;
import com.jschool.controllers.exception.ControllerStatusCode;
import com.jschool.entities.*;
import com.jschool.services.api.DriverService;
import com.jschool.services.api.OrderAndCargoService;
import com.jschool.services.api.TruckService;
import com.jschool.services.api.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by infinity on 12.02.16.
 */
public class OrderController extends BaseController {

    private static final Logger LOG = Logger.getLogger(OrderController.class);

    private OrderAndCargoService orderAndCargoService;
    private TruckService truckService;
    private DriverService driverService;
    private Validator validator;

    public OrderController(Properties errorProperties, OrderAndCargoService orderAndCargoService, TruckService truckService, DriverService driverService, Validator validator) {
        super(errorProperties);
        this.orderAndCargoService = orderAndCargoService;
        this.truckService = truckService;
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
                if (uri.length == 4 && "orders".equals(uri[3]))
                    showOrders(request, response);
                else if (uri.length == 5 && "add".equals(uri[4]))
                    showFormForOrderAdd(request, response);
                else
                    throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
            }
            if ("POST".equals(request.getMethod())) {
                if (uri.length == 5 && "add".equals(uri[4]))
                    addOrder(request, response);
                else if (uri.length == 5 && "submit".equals(uri[4]))
                    submitOrder(request, response);
                else
                    throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
            }
        } catch (ControllerException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }
    }

    // /employee/orders/
    public void showOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Order> orders = orderAndCargoService.findAllOrders();
            Map<Order, List<Cargo>> orderListMap = new HashMap<>();
            for (Order order : orders) {
                List<Cargo> cargos = orderAndCargoService.findAllCargosByOrderNumber(order.getNumber());
                orderListMap.put(order, cargos);
            }
            req.setAttribute("orderListMap", orderListMap);
            req.getRequestDispatcher("/WEB-INF/pages/order/order.jsp").forward(req, resp);
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,req,resp);
        }
    }

    // /employee/order/add GET
    public void showFormForOrderAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/order/orderAllAdd.jsp").forward(req, resp);
    }

    //   /employee/order/add POST
    public void addOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //return needed page equals step on wizard and passed params from wizard form
            String stepNumber = req.getParameter("step_number");
            switch (stepNumber) {
                case "1":
                    req.getRequestDispatcher("/WEB-INF/pages/order/orderCargo.jsp").forward(req, resp);
                    break;
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
                    req.setAttribute("trucks", trucks);
                    req.setAttribute("max", max);
                    req.getRequestDispatcher("/WEB-INF/pages/order/orderTruck.jsp").forward(req, resp);
                    break;
                case "3":
                    String pickupCity[] = req.getParameterValues("pickup");
                    String unloadCity[] = req.getParameterValues("unload");
                    validator.validateCargoCities(pickupCity, unloadCity);
                    List<String> cities = new ArrayList<>();
                    for (int i = 0; i < pickupCity.length; i++) {
                        cities.add(pickupCity[i]);
                        cities.add(unloadCity[i]);
                    }
                    req.setAttribute("cities", cities);
                    req.getRequestDispatcher("/WEB-INF/pages/order/orderMap.jsp").forward(req, resp);
                    break;
                case "4":
                    String duration = req.getParameter("duration").split(" ")[0];
                    String truckNumber = req.getParameter("truckNumber");
                    validator.validateTruckNumber(truckNumber);
                    Truck truck = truckService.getTruckByNumber(truckNumber);
                    Map<Driver, Integer> driverHoursList = driverService.findAllAvailableDrivers(Integer.parseInt(duration));
                    req.setAttribute("drivers", driverHoursList);
                    req.setAttribute("duration", duration);
                    req.setAttribute("shiftSize", truck.getShiftSize());
                    req.getRequestDispatcher("/WEB-INF/pages/order/orderDrivers.jsp").forward(req, resp);
                    break;
            }
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,req,resp);
        }catch (ControllerException e){
            LOG.warn(e.getMessage());
            showError(e,req,resp);
        }
    }

    // /employee/order/submit POST
    public void submitOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
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
            showError(e,req,resp);
        }catch (ControllerException e){
            LOG.warn(e.getMessage());
            showError(e,req,resp);
        }
    }

}
