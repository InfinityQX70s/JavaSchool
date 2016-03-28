package com.jschool.controllers;

import com.jschool.entities.Truck;
import com.jschool.entities.TruckStatistic;
import com.jschool.services.api.TruckService;
import com.jschool.services.api.exception.ServiceException;
import com.jschool.validator.TruckFormValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by infinity on 12.02.16.
 */
@Controller
public class TruckController{

    private static final Logger LOG = Logger.getLogger(TruckController.class);

    @Resource(name="errorProperties")
    private Properties errorProperties;
    private final TruckService truckService;
    private final TruckFormValidator truckFormValidator;

    @Autowired
    public TruckController(TruckService truckService, TruckFormValidator truckFormValidator) {
        this.truckService = truckService;
        this.truckFormValidator = truckFormValidator;
    }

    @RequestMapping(value = "/employee/trucks", method = RequestMethod.GET)
    public String showTrucks(@RequestParam(value = "page", defaultValue = "1") int page, Model model, RedirectAttributes redirectAttributes) {
        try {
            int limitElements = 7;
            List<Truck> utilElem = truckService.findAllTrucks();
            int pageCount = (int) Math.ceil(utilElem.size()/(float)limitElements);
            List<Truck> trucks = truckService.findAllTrucksByOffset((page-1)*limitElements,limitElements);
            Map<Truck, List<TruckStatistic>> truckListMap = new HashMap<>();
            for (Truck truck : trucks){
                List<TruckStatistic> truckStatistics = truckService.findTruckStatisticByOneMonth(truck);
                truckListMap.put(truck,truckStatistics);
            }
            model.addAttribute("pageCount", pageCount);
            model.addAttribute("currentPage", page);
            model.addAttribute("trucks", trucks);
            model.addAttribute("truckListMap", truckListMap);
            return "truck/truck";
        } catch (ServiceException e) {
            LOG.warn(e);
            redirectAttributes.addFlashAttribute("message", errorProperties.getProperty(e.getStatusCode().name()));
            return "redirect:/employee/trucks";
        }
    }

    @RequestMapping(value = "/employee/truck/add", method = RequestMethod.GET)
    public String showFormForTruckAdd(Model model){
        model.addAttribute("truck", new Truck());
        return "truck/truckAdd";
    }

    //  /employee/truck/add POST
    @RequestMapping(value = "/employee/truck/add", method = RequestMethod.POST)
    public String addTruck(@ModelAttribute("truck") Truck truck,
                         BindingResult truckResult, Model model, RedirectAttributes redirectAttributes){
        truckFormValidator.validate(truck,truckResult);
        if (truckResult.hasErrors())
            return "truck/truckAdd";
        else{
            try {
                truckService.addTruck(truck);
            } catch (ServiceException e) {
                LOG.warn(e);
                model.addAttribute("error", errorProperties.getProperty(e.getStatusCode().name()));
                return "truck/truckAdd";
            }
            redirectAttributes.addFlashAttribute("message", "Truck added successfully!");
            return "redirect:/employee/trucks";
        }
    }

    @RequestMapping(value = "/employee/truck/delete", method = RequestMethod.POST)
    public String deleteTruck(@RequestParam(value = "number", required = false) String number, Model model, RedirectAttributes redirectAttributes){
        try {
            truckService.deleteTruck(number);
            redirectAttributes.addFlashAttribute("message", "Truck deleted successfully!");
            return "redirect:/employee/trucks";
        } catch (ServiceException e) {
            LOG.warn(e);
            redirectAttributes.addFlashAttribute("message", errorProperties.getProperty(e.getStatusCode().name()));
            return "redirect:/employee/trucks";
        }
    }

    @RequestMapping(value = "/employee/truck/{number}/edit", method = RequestMethod.GET)
    public String showFormForChangeTruck(@PathVariable("number") String number, Model model, RedirectAttributes redirectAttributes){
        try {
            Truck truck = truckService.getTruckByNumber(number);
            model.addAttribute("truck", truck);
            return "truck/truckEdit";
        } catch (ServiceException e) {
            LOG.warn(e);
            redirectAttributes.addFlashAttribute("message", errorProperties.getProperty(e.getStatusCode().name()));
            return "redirect:/employee/trucks";
        }
    }

    //   /employee/truck/change POST
    @RequestMapping(value = "/employee/truck/change", method = RequestMethod.POST)
    public String changeTruck(@ModelAttribute("truck") Truck truck,
                            BindingResult truckResult,
                            Model model, RedirectAttributes redirectAttributes){
        truckFormValidator.validate(truck,truckResult);
        if (truckResult.hasErrors()) {
            return "truck/truckEdit";
        }else {
            try {
                truckService.updateTruck(truck);
                redirectAttributes.addFlashAttribute("message", "Truck edited successfully!");
                return "redirect:/employee/trucks";
            } catch (ServiceException e) {
                LOG.warn(e);
                model.addAttribute("error", errorProperties.getProperty(e.getStatusCode().name()));
                return "truck/truckEdit";
            }
        }
    }

}
