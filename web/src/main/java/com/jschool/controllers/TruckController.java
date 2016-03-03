package com.jschool.controllers;

import com.jschool.AppContext;
import com.jschool.Validator;
import com.jschool.controllers.exception.ControllerException;
import com.jschool.controllers.exception.ControllerStatusCode;
import com.jschool.entities.Truck;
import com.jschool.services.api.TruckService;
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
public class TruckController extends BaseController {

    private static final Logger LOG = Logger.getLogger(TruckController.class);

    private TruckService truckService;
    private Validator validator;

    public TruckController(Properties errorProperties, TruckService truckService, Validator validator) {
        super(errorProperties);
        this.truckService = truckService;
        this.validator = validator;
    }

    @Override
    public void execute(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //split uri by "/" and check url on correct and pass it to needed method
            // by uri and "get" or "post" method
            String[] uri = request.getRequestURI().split("/");
            if ("GET".equals(request.getMethod())) {
                if (uri.length == 4 && "trucks".equals(uri[3]))
                    showTrucks(request, response);
                else if (uri.length == 5 && "add".equals(uri[4]))
                    showFormForTruckAdd(request, response);
                else if (uri.length == 6 && "edit".equals(uri[5]))
                    showFormForChangeTruck(request, response, uri[4]);
                else
                    throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
            }
            if ("POST".equals(request.getMethod())) {
                if (uri.length == 5 && "add".equals(uri[4]))
                    addTruck(request, response);
                else if (uri.length == 5 && "delete".equals(uri[4]))
                    deleteTruck(request, response);
                else if (uri.length == 5 && "change".equals(uri[4]))
                    changeTruck(request, response);
                else
                    throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
            }
        } catch (ControllerException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }
    }


    // /employee/trucks/
    public void showTrucks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int limitElements = 7;
            List<Truck> utilElem = truckService.findAllTrucks();
            int pageCount = (int) Math.ceil(utilElem.size()/(float)limitElements);
            String page = req.getParameter("page");
            List<Truck> trucks;
            if (page == null){
                page = "1";
                trucks = truckService.findAllTrucksByOffset(0,limitElements);
            }else{
                trucks = truckService.findAllTrucksByOffset((Integer.parseInt(page)-1)*limitElements,limitElements);
            }
            req.setAttribute("pageCount", pageCount);
            req.setAttribute("currentPage", page);
            req.setAttribute("trucks", trucks);
            req.getRequestDispatcher("/WEB-INF/pages/truck/truck.jsp").forward(req, resp);
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,req,resp);
        }
    }

    // /employee/truck/add GET
    public void showFormForTruckAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/truck/truckAdd.jsp").forward(req, resp);
    }

    //  /employee/truck/add POST
    public void addTruck(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String number = req.getParameter("number");
            String capacity = req.getParameter("capacity");
            String shiftSize = req.getParameter("shiftSize");
            String status = req.getParameter("status");
            validator.validateTruck(number, capacity, shiftSize, status);
            Truck truck = new Truck();
            truck.setNumber(number);
            truck.setCapacity(Integer.parseInt(capacity));
            truck.setShiftSize(Integer.parseInt(shiftSize));
            if (status.equals("ok")) {
                truck.setRepairState(true);
            } else {
                truck.setRepairState(false);
            }
            truckService.addTruck(truck);
            resp.sendRedirect("/employee/trucks");
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,req,resp);
        }catch (ControllerException e){
            LOG.warn(e.getMessage());
            showError(e,req,resp);
        }
    }

    //   /employee/truck/delete POST
    public void deleteTruck(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String number = req.getParameter("number");
            validator.validateTruckNumber(number);
            truckService.deleteTruck(number);
            resp.sendRedirect("/employee/trucks");
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,req,resp);
        }catch (ControllerException e){
            LOG.warn(e.getMessage());
            showError(e,req,resp);
        }
    }

    //   /employee/truck/{number}/edit GET
    public void showFormForChangeTruck(HttpServletRequest req, HttpServletResponse resp, String number) throws ServletException, IOException {
        try {
            validator.validateTruckNumber(number);
            Truck truck = truckService.getTruckByNumber(number);
            req.setAttribute("truck", truck);
            req.getRequestDispatcher("/WEB-INF/pages/truck/truckEdit.jsp").forward(req, resp);
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,req,resp);
        }catch (ControllerException e){
            LOG.warn(e.getMessage());
            showError(e,req,resp);
        }
    }

    //   /employee/truck/change POST
    public void changeTruck(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String number = req.getParameter("number");
            String capacity = req.getParameter("capacity");
            String shiftSize = req.getParameter("shiftSize");
            String status = req.getParameter("status");
            validator.validateTruck(number, capacity, shiftSize, status);
            Truck truck = new Truck();
            truck.setNumber(number);
            truck.setCapacity(Integer.parseInt(capacity));
            truck.setShiftSize(Integer.parseInt(shiftSize));
            if (status.equals("ok")) {
                truck.setRepairState(true);
            } else {
                truck.setRepairState(false);
            }
            truckService.updateTruck(truck);
            resp.sendRedirect("/employee/trucks");
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,req,resp);
        }catch (ControllerException e){
            LOG.warn(e.getMessage());
            showError(e,req,resp);
        }
    }

}
