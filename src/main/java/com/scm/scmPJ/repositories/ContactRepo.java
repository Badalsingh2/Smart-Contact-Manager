package com.scm.scmPJ.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scm.scmPJ.entities.Contact;
import com.scm.scmPJ.entities.User;

import java.util.List;


@Repository
public interface ContactRepo extends JpaRepository<Contact,String>{

    Page<Contact> findByUser(User user,Pageable pageable);
    //find the contact by users

    
    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
    List<Contact> findByUserId(String user);

    Page<Contact> findByUserAndNameContaining(User user,String name,Pageable pageable);
    Page<Contact> findByUserAndEmailContaining(User user,String email,Pageable pageable);
    Page<Contact> findByUserAndPhoneNumberContaining(User user,String phone,Pageable pageable);
}