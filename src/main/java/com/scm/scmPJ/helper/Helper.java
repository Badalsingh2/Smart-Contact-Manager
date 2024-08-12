package com.scm.scmPJ.helper;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication){
       
        //if login throug email and password
        if(authentication instanceof OAuth2AuthenticationToken){

            var oAuth2AuthenticationToken=(OAuth2AuthenticationToken)authentication;
            var clientId=oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            var oauth2User = (OAuth2User)authentication.getPrincipal();
            String username="";
            System.out.println(clientId);
            //sign in google
            if(clientId.equalsIgnoreCase("google")){
                username=oauth2User.getAttribute("email").toString();
            }

            //sign in github
            else if(clientId.equalsIgnoreCase("github")){
                System.out.println("Getting email from github");
                username=oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString()
                :oauth2User.getAttribute("login").toString() + "@gmail.com";
            }
            else if(clientId.equalsIgnoreCase("facebook")){
                System.out.println("getting email from facebook");
            }

            //sign in facebook
            return username;
        }
        
        else{
            System.out.println("came here");
            return authentication.getName();
        }
       
        
    }

    public static String getEmailForEmailVerification(String emailToken){
        String link = "http://localhost:8081/auth/verify-email?token="+emailToken;

        return link;
    }
}
