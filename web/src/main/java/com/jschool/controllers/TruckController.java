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

/**
 * Created by infinity on 12.02.16.
 */
public class TruckController implements BaseController {

    private static final Logger LOG = Logger.getLogger(TruckController.class);

    private AppContext appContext = AppContext.getInstance();
    private TruckService truckService = appContext.getTruckService();
    private Validator validator = appContext.getValidator();

    public void execute(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String[] uri = request.getRequestURI().split("/");
            if (request.getMethod().equals("GET")) {
                if (uri.length == 4 && uri[3].equals("trucks"))
                    showTrucks(request, response);
                else if (uri.length == 5 && uri[4].equals("add"))
                    showFormForTruckAdd(request, response);
                else if (uri.length == 6 && uri[5].equals("edit"))
                    showFormForChangeTruck(request, response, uri[4]);
                else
                    throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
            }
            if (request.getMethod().equals("POST")) {
                if (uri.length == 5 && uri[4].equals("add"))
                    addTruck(request, response);
                else if (uri.length == 5 && uri[4].equals("delete"))
                    deleteTruck(request, response);
                else if (uri.length == 5 && uri[4].equals("change"))
                    changeTruck(request, response);
                else
                    throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
            }
        } catch (ControllerException e) {
            LOG.warn(e.getMessage());
            request.setAttribute("error", e);
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }


    // /employee/trucks/
    public void showTrucks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Truck> trucks = truckService.findAllTrucks();
            req.setAttribute("trucks", trucks);
            req.getRequestDispatcher("/WEB-INF/pages/truck/truck.jsp").forward(req, resp);
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            req.setAttribute("error", e);
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
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
        } catch (ServiceException | ControllerException e) {
            LOG.warn(e.getMessage());
            req.setAttribute("error", e);
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
        }
    }

    //   /employee/truck/delete POST
    public void deleteTruck(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String number = req.getParameter("number");
            validator.validateTruckNumber(number);
            truckService.deleteTruck(number);
            resp.sendRedirect("/employee/trucks");
        } catch (ServiceException | ControllerException e) {
            LOG.warn(e.getMessage());
            req.setAttribute("error", e);
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
        }
    }

    //   /employee/truck/{number}/edit GET
    public void showFormForChangeTruck(HttpServletRequest req, HttpServletResponse resp, String number) throws ServletException, IOException {
        try {
            validator.validateTruckNumber(number);
            Truck truck = truckService.getTruckByNumber(number);
            req.setAttribute("truck", truck);
            req.getRequestDispatcher("/WEB-INF/pages/truck/truckEdit.jsp").forward(req, resp);
        } catch (ServiceException | ControllerException e) {
            LOG.warn(e.getMessage());
            req.setAttribute("error", e);
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
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
        } catch (ServiceException | ControllerException e) {
            LOG.warn(e.getMessage());
            req.setAttribute("error", e);
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
        }
    }

}
