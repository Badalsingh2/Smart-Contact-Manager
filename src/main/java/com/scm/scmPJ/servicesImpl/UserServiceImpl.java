package com.scm.scmPJ.servicesImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.scmPJ.entities.AppConstants;
import com.scm.scmPJ.entities.User;
import com.scm.scmPJ.helper.Helper;
import com.scm.scmPJ.helper.ResourceNotFoundException;
import com.scm.scmPJ.repositories.UserRepo;
import com.scm.scmPJ.services.EmailService;
import com.scm.scmPJ.services.UserService;

//import ch.qos.logback.classic.Logger;
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    private Logger logger=LoggerFactory.getLogger(this.getClass());

    @Override
    public User saveUser(User user) {
        //user id:have to generate
        String userId=UUID.randomUUID().toString();
        user.setUserId(userId);

        //password encode
        //user.setpassword(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRoleList(List.of(AppConstants.ROLE_USER));

        

        String emailToken = UUID.randomUUID().toString();
        user.setEmailToken(emailToken);
        User saveUser= userRepo.save(user);
        String emailLink = Helper.getEmailForEmailVerification(emailToken);
        emailService.sendEmail(saveUser.getEmail(), "Verify Account : Email COntact Manager" , emailLink);
        return saveUser;
    }

    @Override
    public Optional<User> getUserById(String id) {
        
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
        User user1=userRepo.findById(user.getUserId()).orElseThrow(()-> new ResourceNotFoundException("User Not found"));
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        user1.setAbout(user.getAbout());
        user1.setPhoneNumber(user.getPhoneNumber());
        user1.setPassword(user.getPassword());
        user1.setProfilePic(user.getProfilePic());
        user1.setEnabled(user.isEnabled());
        user1.setEmailVerified(user.isEmailVerified());
        user1.setPhoneVerified(user.isPhoneVerified());
        user1.setProvider(user.getProvider());
        user1.setProvideruserId(user.getProvideruserId());

        //save
        User save =userRepo.save(user1);

        return Optional.ofNullable(save);



    }

    @Override
    public void deleteUser(String id) {
        User user1=userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not found"));
        userRepo.delete(user1);
      
    }

    @Override
    public boolean isUserExist(String userId) {
        User user1=userRepo.findById(userId).orElse(null);
        return user1!=null ? true : false;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user1=userRepo.findByEmail(email).orElse(null);
        return user1 != null?true:false;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo. findAll();  
    }

    @Override
    public User getUserByEmail(String email) {
       return userRepo.findByEmail(email).orElse(null);
    }
}
