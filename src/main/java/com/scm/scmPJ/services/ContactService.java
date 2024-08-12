package com.scm.scmPJ.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.scm.scmPJ.entities.Contact;
import com.scm.scmPJ.entities.User;

@Service
public interface ContactService {
    //save contact
    Contact save(Contact contact);

    //update contact
    Contact update(Contact contact);

    //get contacts
    List<Contact> getAll();

    //get contact by id
    Contact getById(String id);

    //delete contact
    void delete(String id);

    //search contact
    Page<Contact> searchByName(String name,int size,int page,String sortBy,String order,User user);
    Page<Contact> searchByEmail(String email,int size,int page,String sortBy,String order,User user);
    Page<Contact> searchByPhoneNumber(String phoneNumberKeyword,int size,int page,String sortBy,String order,User user);

    //get contact  by userId
    List<Contact> getByUserId(String userId);

    Page<Contact> getbyUser(User user,int page,int size,String sort,String direction);
}
