package com.jschool.controllers;

import com.jschool.controllers.exception.ControllerException;
import com.jschool.entities.Driver;
import com.jschool.entities.User;
import com.jschool.services.api.DriverService;
import com.jschool.services.api.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by infinity on 12.02.16.
 */
@Controller
public class DriverController {

    private static final Logger LOG = Logger.getLogger(DriverController.class);

    private final DriverService driverService;
    private final Validator validator;

    @Autowired
    public DriverController(DriverService driverService, Validator validator) {
        this.driverService = driverService;
        this.validator = validator;
    }


    //    /employee/drivers/ GET
    @RequestMapping(value = "/employee/drivers", method = RequestMethod.GET)
    public String showDrivers(@RequestParam(value = "page", defaultValue = "1") int page, Model model) throws ServiceException {
        int limitElements = 7;
        List<Driver> utilElem = driverService.findAllDrivers();
        int pageCount = (int) Math.ceil(utilElem.size() / (float) limitElements);
        List<Driver> drivers = driverService.findAllDriversByOffset((page - 1) * limitElements, limitElements);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("currentPage", page);
        model.addAttribute("drivers", drivers);
        return "driver/driver";
    }

    //   /employee/driver/add GET
    @RequestMapping(value = "/employee/driver/add", method = RequestMethod.GET)
    public String showFormForDriverAdd(Model model) {
        model.addAttribute("driver",new Driver());
        model.addAttribute("user",new User());
        return "driver/driverAdd";
    }

    //   /employee/driver/add POST
    @RequestMapping(value = "/employee/driver/add", method = RequestMethod.POST)
    public String addDriver(@ModelAttribute("driver") Driver driver,
                            BindingResult driverResult, @ModelAttribute("user") User user,
                            BindingResult userResult) throws ServiceException, ControllerException {
        //get fields from form validate them and fill entity, pass it to service
        validator.validateEmail(user.getEmail());
        //validator.validateDriverNumber(driver.getNumber());
        validator.validateFirstAndLastName(driver.getFirstName(), driver.getLastName());
        user.setPassword(user.getEmail());
        user.setRole(true);
        driver.setUser(user);
        driverService.addDriver(driver);
        return "redirect:/employee/drivers";
    }

    //   /employee/driver/delete POST
    @RequestMapping(value = "/employee/driver/delete", method = RequestMethod.POST)
    public String deleteDriver(HttpServletRequest req, @RequestParam(value = "number", required = false) String number) throws ServiceException, ControllerException {
        validator.validateDriverNumber(number);
        driverService.deleteDriver(Integer.parseInt(number));
        return "redirect:/employee/drivers";
    }

    //   /employee/driver/{number}/edit GET
    @RequestMapping(value = "/employee/driver/{number}/edit", method = RequestMethod.GET)
    public String showFormForChangeDriver(@PathVariable("number") String number, Model model) throws ServiceException, ControllerException {
        validator.validateDriverNumber(number);
        Driver driver = driverService.getDriverByPersonalNumber(Integer.parseInt(number));
        model.addAttribute("driver", driver);
        return "driver/driverEdit";
    }

    //   /employee/driver/change POST
    @RequestMapping(value = "/employee/driver/change", method = RequestMethod.POST)
    public String changeDriver(HttpServletRequest req,
                               @RequestParam(value = "number", required = false) String number,
                               @RequestParam(value = "firstName", required = false) String firstName,
                               @RequestParam(value = "lastName", required = false) String lastName) throws ControllerException, ServiceException {
        validator.validateDriverNumber(number);
        validator.validateFirstAndLastName(firstName, lastName);
        Driver driver = new Driver();
        driver.setNumber(Integer.parseInt(number));
        driver.setFirstName(firstName);
        driver.setLastName(lastName);
        driverService.updateDrive(driver);
        return "redirect:/employee/drivers";
    }


}
