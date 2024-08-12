package com.scm.scmPJ.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.scmPJ.entities.User;
import com.scm.scmPJ.helper.Helper;
import com.scm.scmPJ.services.UserService;

@ControllerAdvice
public class RootController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addLoggedInUserInformation(Model model,Authentication authentication){
        if(authentication == null){
            return;
        }
        System.out.println("Adding logged in user information to the model\n");

        String name = Helper.getEmailOfLoggedInUser(authentication);
        logger.info("user logged in: "+name);
        User user=userService.getUserByEmail(name);
        System.out.println(user);
        
        System.out.println(user.getEmail());
        System.out.println(user.getName());
        System.out.println(user.getAbout());
        model.addAttribute("loggedInUser", user);

    }
}
