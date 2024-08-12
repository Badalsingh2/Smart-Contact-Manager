package com.scm.scmPJ;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.scm.scmPJ.services.EmailService;

@SpringBootTest
class ScmPjApplicationTests {

	@Test
	void contextLoads() {
	}

    @Autowired
    private EmailService service;

    @Test
    void sendEmailTest(){
        service.sendEmail("bs.421521@gmail.com", "Just testing email servid=ce", 
        "this is scm project testing email service");
    }

}
