package com.scm.scmPJ.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;

import com.scm.scmPJ.entities.User;
import com.scm.scmPJ.forms.UserForm;
import com.scm.scmPJ.helper.Message;
import com.scm.scmPJ.helper.MessageType;
import com.scm.scmPJ.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(){
        return "redirect:/home";
    }

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

    @GetMapping("/contact")
    public String ContactPage() {
        return new String("contact");
    }
   
    @GetMapping("/login")
    public String login() {
        return new String("login");
    }

    @GetMapping("/register")
    public String register(Model model) {
        UserForm userForm = new UserForm();
        //userForm.setName("Badal");
        model.addAttribute("userForm", userForm);
        return "register";
    }
    

    //processing register 
    @RequestMapping(value = "/do-register",method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult,HttpSession session){
        System.out.println("Processing registration");
        //fetch the form data
        //UserForm
        System.out.println(userForm);

        //validte form data
        //todo
        if(bindingResult.hasErrors()){
            return "register";
        }


        //save to database
        // User user=User.builder()
        // .name(userForm.getName())
        // .email(userForm.getEmail())
        // .password(userForm.getPassword())
        // .about(userForm.getAbout())
        // .phoneNumber(userForm.getPhoneNumber())
        // .profilePic("https://www.kindpng.com/picc/m/451-4517876_default-profile-hd-png-download.png")
        // .build();

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setEnabled(false);
        user.setProfilePic("https://www.kindpng.com/picc/m/451-4517876_default-profile-hd-png-download.png");
        User savedUser=userService.saveUser(user);
        System.out.println("user saved: "+savedUser);


        //message = "Registration successfull"

        Message message=Message.builder().content("Registration SuccessFull").type(MessageType.green).build();
        session.setAttribute("message",message);
        //redirect to login page

        return "redirect:/register";
    }
    
}
