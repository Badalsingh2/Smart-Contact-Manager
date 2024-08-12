package com.scm.scmPJ.controllers;

import java.security.Principal;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.scmPJ.entities.User;
import com.scm.scmPJ.helper.Helper;
import com.scm.scmPJ.services.UserService;


@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    

    //user dashboard page
    @RequestMapping(value="/dashboard")
    public String userDashboard() {
        return "user/dashboard";
    }

    //user profile page
    @RequestMapping(value="/profile")
    public String userProfile(Model model,Authentication authentication) {
       
        return "user/profile";
    }
    

    //user add contacts page

    //user view contacts


}
