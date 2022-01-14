package com.payMyBuddy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PayMyBuddyApplication extends SpringBootServletInitializer {

	private static final Logger LOGGER = LogManager.getLogger(PayMyBuddyApplication.class.getName());

	public static void main(String[] args) {
		LOGGER.debug("Pay My Buddy started");
		SpringApplication.run(PayMyBuddyApplication.class, args);
	}

}
