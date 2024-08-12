package com.scm.scmPJ.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scm.scmPJ.entities.User;

public interface UserRepo extends JpaRepository<User, String>{

    //extra methods db relationsh
    Optional<User> findByEmail(String email);
    //custom query methods

    Optional<User> findByEmailAndPassword(String email,String password);

    //custom finder method
    
    Optional<User> findByEmailToken(String token);
}