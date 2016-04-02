package com.jschool.controllers;

import com.jschool.entities.User;
import com.jschool.services.api.UserService;
import com.jschool.services.api.exception.ServiceException;
import com.jschool.validator.UserFormValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;
import java.util.Properties;

/**
 * Created by infinity on 24.03.16.
 */
@Controller
public class AdminController {

    private static final Logger LOG = Logger.getLogger(AdminController.class);

    @Resource(name="errorProperties")
    private Properties errorProperties;
    private UserService userService;
    private final UserFormValidator userFormValidator;

    @Autowired
    public AdminController(UserService userService, UserFormValidator userFormValidator) {
        this.userService = userService;
        this.userFormValidator = userFormValidator;
    }

    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public String showDrivers(Model model, RedirectAttributes redirectAttributes){
        try {
            List<User> users = userService.findAllManagers();
            model.addAttribute("users", users);
            return "user";
        } catch (ServiceException e) {
            LOG.warn(e);
            redirectAttributes.addFlashAttribute("message", errorProperties.getProperty(e.getStatusCode().name()));
            return "redirect:/admin/users";
        }

    }

    @RequestMapping(value = "/admin/user/add", method = RequestMethod.GET)
    public String showFormForDriverAdd(Model model) {
        model.addAttribute("user",new User());
        return "userAdd";
    }

    @RequestMapping(value = "/admin/user/add", method = RequestMethod.POST)
    public String addDriver(@ModelAttribute("user") User user,
                            BindingResult userResult, Model model, RedirectAttributes redirectAttributes){
        userFormValidator.validate(user,userResult);
        if (userResult.hasErrors()) {
            return "userAdd";
        } else {
            try {
                userService.addUser(user);
            } catch (ServiceException e) {
                LOG.warn(e);
                model.addAttribute("error", errorProperties.getProperty(e.getStatusCode().name()));
                return "userAdd";
            }
            redirectAttributes.addFlashAttribute("message", "User added successfully!");
            return "redirect:/admin/users";
        }
    }

    @RequestMapping(value = "/admin/user/delete", method = RequestMethod.POST)
    public String deleteDriver(@RequestParam(value = "email", required = false) String email, RedirectAttributes redirectAttributes){
        try {
            userService.deleteManagerByEmail(email);
            redirectAttributes.addFlashAttribute("message", "User deleted successfully!");
            return "redirect:/admin/users";
        } catch (ServiceException e) {
            LOG.warn(e);
            redirectAttributes.addFlashAttribute("message", errorProperties.getProperty(e.getStatusCode().name()));
            return "redirect:/admin/users";
        }
    }

}
