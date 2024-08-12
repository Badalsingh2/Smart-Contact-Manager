package com.scm.scmPJ.controllers;

import java.util.List;
import java.util.UUID;

//import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.scmPJ.entities.AppConstants;
import com.scm.scmPJ.entities.Contact;
import com.scm.scmPJ.entities.User;
import com.scm.scmPJ.forms.ContactForm;
import com.scm.scmPJ.forms.ContactSearchForm;
import com.scm.scmPJ.helper.Helper;
import com.scm.scmPJ.helper.Message;
import com.scm.scmPJ.helper.MessageType;
import com.scm.scmPJ.services.ContactService;
import com.scm.scmPJ.services.ImageService;
import com.scm.scmPJ.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    private Logger logger=LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ImageService imageService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    //add contact page: handler
    @RequestMapping("/add")
    public String addContactView(Model model){
        ContactForm contactForm= new ContactForm();
        model.addAttribute("contactform", contactForm);
        //contactForm.setEmail("badal@gmail.com");
        //contactForm.setFavorite(true);
        //System.out.println(contactForm);
        return "user/add_contact";
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute("contactform") ContactForm contactForm,BindingResult result,Authentication authentication,HttpSession session){

       
        //process the form

        //validate the form
        if(result.hasErrors()){
            session.setAttribute("message", Message.builder().content("Please correct the following errors").type(MessageType.red).build());
            return "user/add_contact";
        }

        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user=userService.getUserByEmail(username);

        //process the contact picture
        
        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setFavorite(contactForm.isFavorite());
        contact.setEmail(contactForm.getEmail());
        contact.setDescription(contactForm.getDescription());
        contact.setAddress(contactForm.getAddress());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setUser(user);

        if(contactForm.getContactImage()!= null && !contactForm.getContactImage().isEmpty()){

            String fileName = UUID.randomUUID().toString();
            String imageURL=imageService.uploadImage(contactForm.getContactImage(),fileName);
            contact.setPictures(imageURL);
            contact.setCloudinaryImage(fileName);
        } 

        contactService.save(contact);
        System.out.println(contactForm);

        //set the contact picture url

        //set message to be displayed on view
        session.setAttribute("message", Message.builder().content("New Contact SuccessFully Added").type(MessageType.green).build());

        return "redirect:/user/contacts/add";
    }

    @RequestMapping
    public String viewContacts(
        @RequestParam(value="page",defaultValue = "0") int page,
        @RequestParam(value="size",defaultValue = AppConstants.PAGE_SIZE+ "") int size,
        @RequestParam(value="sortBy",defaultValue = "name") String sortBy,
        @RequestParam(value="direction",defaultValue = "asc") String direction,
        Model model,Authentication authentication){
        //load all the contacts
        String username=Helper.getEmailOfLoggedInUser(authentication);

        User user = userService.getUserByEmail(username);
        Page<Contact> pageContacts=contactService.getbyUser(user,page,size,sortBy,direction);

        model.addAttribute("pageContacts",pageContacts);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        model.addAttribute("contactSearchForm", new ContactSearchForm());

        return "user/contacts";
    }

    //search handleer
    @RequestMapping("/search")
    public String searchhandler(
    @ModelAttribute ContactSearchForm contactSearchForm,
   
    @RequestParam(value = "size",defaultValue = AppConstants.PAGE_SIZE + "")int size,
    @RequestParam(value = "page",defaultValue = "0")int page, 
    @RequestParam(value = "sortBy",defaultValue = "name")String sortBy, 
    @RequestParam(value = "direction",defaultValue = "asc")String direction,
    Model model,
    Authentication authentication){
        logger.info("field {} keyword {}",contactSearchForm.getField(),contactSearchForm.getValue());

        var user=userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));
       
        Page<Contact> pageContact = null;
        String field = contactSearchForm.getField();
        if (field != null) {
            if (field.equalsIgnoreCase("name")) {
                pageContact = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction, user);
            } else if (field.equalsIgnoreCase("email")) {
                pageContact = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction, user);
            } else if (field.equalsIgnoreCase("phone")) {
                pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy, direction, user);
            }
        }
          
        logger.info("PageContact {}:",pageContact);

        model.addAttribute("pageContacts", pageContact);

        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        return "user/search";
    }

    @RequestMapping("/delete/{id}")
    public String deleteContact(@PathVariable("id") String id, HttpSession session){
        contactService.delete(id);
        logger.info("contactId {} deleted",id);

        session.setAttribute("message",
                Message.builder()
                .content("Contact is Deleted SuccessFully")
                .type(MessageType.green)
                .build());

        return "redirect:/user/contacts";
    }

    //update contact form view
    @GetMapping("/view/{contactId}")
    public String updateContactFormView(@PathVariable("contactId") String contactId,Model model){
        var contact=contactService.getById(contactId);
        ContactForm contactForm=new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getAddress());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setFavorite(contact.isFavorite());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setLinkedInLink(contact.getLinkedInLink());
        contactForm.setPicture(contact.getPictures());

        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contactId", contactId);
        return "user/update_contact_view";
    }


    @RequestMapping(value="/update/{contactId}",method = RequestMethod.POST)
    public String updateContact(@PathVariable("contactId") String contactId,@Valid @ModelAttribute("contactForm") ContactForm contactForm,Model model,HttpSession session,BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "user/update_contact_view";
        }
        var con = new Contact();
        con.setId(contactId);
        con.setName(contactForm.getName());
        con.setEmail(contactForm.getEmail());
        con.setAddress(contactForm.getAddress());
        con.setPhoneNumber(contactForm.getPhoneNumber());
        con.setDescription(contactForm.getDescription());
        con.setPictures(contactForm.getPicture());
        con.setFavorite(contactForm.isFavorite());
        con.setLinkedInLink(contactForm.getLinkedInLink());
        con.setWebsiteLink(contactForm.getWebsiteLink());
        //con.setCloudinaryImage(contactForm.getCloudinaryImage());
        //con.setLinks(contactForm.getLinks());

        //process Image
        if(contactForm.getContactImage()!=null && !contactForm.getContactImage().isEmpty()){
            String fileName = UUID.randomUUID().toString();
            String imagePublicId = imageService.uploadImage(contactForm.getContactImage(),fileName); 
            con.setCloudinaryImage(fileName);
            con.setPictures(imagePublicId);
            contactForm.setPicture(imagePublicId);

        }

        var updateCon=contactService.update(con);
        logger.info("updated contact {}",updateCon);
        session.setAttribute("message", Message.builder().content("Contact Updated").type(MessageType.green).build());
        return "redirect:/user/contacts/view/"+contactId;
    }

}
