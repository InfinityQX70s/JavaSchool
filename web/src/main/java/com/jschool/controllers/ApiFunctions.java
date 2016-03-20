package com.jschool.controllers;

import com.jschool.entities.City;
import com.jschool.model.AutoComplete;
import com.jschool.model.JsonResponse;
import com.jschool.services.api.ApiService;
import com.jschool.services.api.DriverService;
import com.jschool.services.api.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by infinity on 10.03.16.
 */
@Controller
public class ApiFunctions {

    private static final Logger LOG = Logger.getLogger(ApiFunctions.class);
    private ApiService apiService;
    private DriverService driverService;

    @Autowired
    public ApiFunctions(ApiService apiService, DriverService driverService) {
        this.apiService = apiService;
        this.driverService = driverService;
    }

    @RequestMapping(value = "/api/cities")
    public
    @ResponseBody
    AutoComplete findCities(@RequestParam(value = "query") String query) {
        try {
            List<City> cities = apiService.findAllCitiesByName(query);
            List<String> names = new ArrayList<>();
            for (City city : cities)
                names.add(city.getName());
            AutoComplete autoComplete = new AutoComplete();
            autoComplete.setQuery(query);
            autoComplete.setSuggestions(names);
            return autoComplete;
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            return null;
        }
    }

    @RequestMapping(value = "/api/verify")
    public @ResponseBody JsonResponse sendVerifyCode(@RequestParam(value = "driver") Integer number) {
        try {
            driverService.sendDriverVerifyCode(number);
            JsonResponse jsonResponse = new JsonResponse();
            jsonResponse.setStatus("ok");
            jsonResponse.setResult("Verification code send successfully");
            return jsonResponse;
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            JsonResponse jsonResponse = new JsonResponse();
            jsonResponse.setStatus("error");
            jsonResponse.setResult(e.getMessage());
            return jsonResponse;
        }
    }
}
