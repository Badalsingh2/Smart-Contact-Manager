package com.scm.scmPJ.services;

public interface EmailService {

    void sendEmail(String to,String subject,String body);

    void sendEmailWithHtml();

    void sendEmailWithAttachment();
    
}
