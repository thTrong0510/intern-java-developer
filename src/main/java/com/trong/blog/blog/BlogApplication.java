package com.trong.blog.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication
//disable security
@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
})
public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

}
