package com.scm.scmPJ.servicesImpl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm.scmPJ.entities.Contact;
import com.scm.scmPJ.entities.User;
import com.scm.scmPJ.helper.ResourceNotFoundException;
import com.scm.scmPJ.repositories.ContactRepo;
import com.scm.scmPJ.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService{

    @Autowired
    ContactRepo contactRepo;

    @Override
    public Contact save(Contact contact) {
        String contactId = UUID.randomUUID().toString();
        contact.setId(contactId);
        return contactRepo.save(contact);

    }

    @Override
    public Contact update(Contact contact) {
        Contact contactOld=contactRepo.findById(contact.getId()).orElseThrow(()->new ResourceNotFoundException("ContactNot found"));
        contactOld.setName(contact.getName());
        contactOld.setEmail(contact.getEmail());
        contactOld.setAddress(contact.getAddress());
        contactOld.setPhoneNumber(contact.getPhoneNumber());
        contactOld.setDescription(contact.getDescription());
        contactOld.setPictures(contact.getPictures());
        contactOld.setFavorite(contact.isFavorite());
        contactOld.setLinkedInLink(contact.getLinkedInLink());
        contactOld.setWebsiteLink(contact.getWebsiteLink());
        contactOld.setCloudinaryImage(contact.getCloudinaryImage());
       // contactOld.setLinks(contact.getLinks());

        return contactRepo.save(contactOld);

    }

    @Override
    public List<Contact> getAll() {
        return contactRepo.findAll();
    }

    @Override
    public Contact getById(String id) {
        return contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Contact not found with id"+id));
    }

    @Override
    public void delete(String id) {
        var contact=contactRepo.findById(id)
            .orElseThrow(()-> new ResourceNotFoundException("Contact not found with id"+id));
        contactRepo.delete(contact);
    }

   

    @Override
    public List<Contact> getByUserId(String userId) {
        return contactRepo.findByUserId(userId);
    }

    @Override
    public Page<Contact> getbyUser(User user,int page,int size,String sortBy,String direction) {

        Sort sort=direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();

        
        var pageable = PageRequest.of(page,size,sort);

        
       return contactRepo.findByUser(user,pageable);
    }

    @Override
    public Page<Contact> searchByName(String name, int size, int page, String sortBy, String order,User user) {
        Sort sort = order.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size,sort);
        return contactRepo.findByUserAndNameContaining(user,name,pageable);
    }

    @Override
    public Page<Contact> searchByEmail(String email, int size, int page, String sortBy, String order,User user) {
        
        Sort sort = order.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size,sort);
        return contactRepo.findByUserAndEmailContaining(user,email,pageable);
    }

    @Override
    public Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy,
            String order ,User user) {
                Sort sort = order.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
                var pageable = PageRequest.of(page, size,sort);
                return contactRepo.findByUserAndPhoneNumberContaining(user,phoneNumberKeyword,pageable);
    }



}
