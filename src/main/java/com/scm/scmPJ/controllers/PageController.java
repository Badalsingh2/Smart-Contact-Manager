package com.scm.scmPJ.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/home")
    public String home(Model model){

        System.out.println("Home page handler");

        //sending data to view
        model.addAttribute("name", "Badal Singh");
        model.addAttribute("Google","Come to Google.com");
        model.addAttribute("FaceBook", "http://www.facebook.com");
        return "home";
    }

    //about
    @RequestMapping("/about")
    public String aboutPage(Model model){
        model.addAttribute("isLogin", false);
        return "about";
    }


    //services
    @RequestMapping("/services")
    public String servicesPage(){

        return "service";
    }

}
