package com.scm.scmPJ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.scm.scmPJ")
public class ScmPjApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScmPjApplication.class, args);
	}

}
