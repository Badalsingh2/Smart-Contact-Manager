package com.scm.scmPJ.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm.scmPJ.entities.Contact;
import com.scm.scmPJ.services.ContactService;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    ContactService contactService;

    //get contact of user
    @RequestMapping("/contacts/{contactID}")
    public Contact getContacts(@PathVariable String contactID){
        return contactService.getById(contactID);
    }
}
