package com.jschool.controllers;

import com.jschool.entities.Driver;
import com.jschool.services.api.DriverService;
import com.jschool.services.api.exception.ServiceException;
import com.jschool.validator.DriverFormValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;
import java.util.Properties;

/**
 * Created by infinity on 12.02.16.
 */
@Controller
public class DriverController {

    private static final Logger LOG = Logger.getLogger(DriverController.class);

    @Resource(name="errorProperties")
    private Properties errorProperties;
    private final DriverService driverService;
    private final DriverFormValidator driverFormValidator;


    @Autowired
    public DriverController(DriverService driverService, DriverFormValidator driverFormValidator) {
        this.driverService = driverService;
        this.driverFormValidator = driverFormValidator;
    }

    @RequestMapping(value = "/employee/drivers", method = RequestMethod.GET)
    public String showDrivers(@RequestParam(value = "page", defaultValue = "1") int page, Model model, RedirectAttributes redirectAttributes){
        try {
            int limitElements = 7;
            List<Driver> utilElem = driverService.findAllDrivers();
            int pageCount = (int) Math.ceil(utilElem.size() / (float) limitElements);
            List<Driver> drivers = driverService.findAllDriversByOffset((page - 1) * limitElements, limitElements);
            model.addAttribute("pageCount", pageCount);
            model.addAttribute("currentPage", page);
            model.addAttribute("drivers", drivers);
            return "driver/driver";
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            redirectAttributes.addFlashAttribute("message", errorProperties.getProperty(e.getStatusCode().name()));
            return "redirect:/employee/drivers";
        }
    }

    @RequestMapping(value = "/employee/driver/add", method = RequestMethod.GET)
    public String showFormForDriverAdd(Model model) {
        model.addAttribute("driver",new Driver());
        return "driver/driverAdd";
    }

    @RequestMapping(value = "/employee/driver/add", method = RequestMethod.POST)
    public String addDriver(@ModelAttribute("driver") Driver driver,
                            BindingResult driverResult, Model model, RedirectAttributes redirectAttributes){
        driverFormValidator.validate(driver,driverResult);
        if (driverResult.hasErrors()) {
            return "driver/driverAdd";
        } else {
            driver.getUser().setPassword(driver.getUser().getEmail());
            driver.getUser().setRole(true);
            try {
                driverService.addDriver(driver);
            } catch (ServiceException e) {
                LOG.warn(e.getMessage());
                model.addAttribute("error", errorProperties.getProperty(e.getStatusCode().name()));
                return "driver/driverAdd";
            }
            redirectAttributes.addFlashAttribute("message", "Driver added successfully!");
            return "redirect:/employee/drivers";
        }
    }

    @RequestMapping(value = "/employee/driver/delete", method = RequestMethod.POST)
    public String deleteDriver(@RequestParam(value = "number", required = false) int number, Model model, RedirectAttributes redirectAttributes){
        try {
            driverService.deleteDriver(number);
            redirectAttributes.addFlashAttribute("message", "Driver deleted successfully!");
            return "redirect:/employee/drivers";
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            redirectAttributes.addFlashAttribute("message", errorProperties.getProperty(e.getStatusCode().name()));
            return "redirect:/employee/drivers";
        }
    }

    @RequestMapping(value = "/employee/driver/{number}/edit", method = RequestMethod.GET)
    public String showFormForChangeDriver(@PathVariable("number") int number, Model model, RedirectAttributes redirectAttributes){
        try {
            Driver driver = driverService.getDriverByPersonalNumber(number);
            model.addAttribute("driver", driver);
            return "driver/driverEdit";
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            redirectAttributes.addFlashAttribute("message", errorProperties.getProperty(e.getStatusCode().name()));
            return "redirect:/employee/drivers";
        }
    }

    @RequestMapping(value = "/employee/driver/change", method = RequestMethod.POST)
    public String changeDriver(@ModelAttribute("driver") Driver driver,
                               BindingResult driverResult,
                               Model model, RedirectAttributes redirectAttributes){
        driverFormValidator.validate(driver,driverResult);
        if (driverResult.hasErrors()) {
            return "driver/driverEdit";
        }else {
            try {
                driverService.updateDrive(driver);
                redirectAttributes.addFlashAttribute("message", "Driver edited successfully!");
                return "redirect:/employee/drivers";
            } catch (ServiceException e) {
                LOG.warn(e.getMessage());
                model.addAttribute("error", errorProperties.getProperty(e.getStatusCode().name()));
                return "driver/driverEdit";
            }
        }
    }


}
