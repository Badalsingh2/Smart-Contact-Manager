package com.scm.scmPJ.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.scmPJ.entities.User;
import com.scm.scmPJ.helper.Message;
import com.scm.scmPJ.helper.MessageType;
import com.scm.scmPJ.repositories.UserRepo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    //vreify email


    @Autowired
    private UserRepo userRepo;

    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token,HttpSession session){
        System.out.println("\n Email verified");
        User user=userRepo.findByEmailToken(token).orElse(null);

        if(user != null){
            //then user is fetched
            if(user.getEmailToken().equals(token)){
                user.setEmailVerified(true);
                user.setEnabled(true);
                userRepo.save(user);
                session.setAttribute("message", Message.builder()
                    .type(MessageType.green)
                    .content("Your email is verified.Now you can login")
                    .build());
                return "success";
            }
            return "error_page";
        }
        session.setAttribute("message", Message.builder()
            .type(MessageType.red)
            .content("Email Not verified! Token not associated")
            .build());
        return "error_page";
    }

}
