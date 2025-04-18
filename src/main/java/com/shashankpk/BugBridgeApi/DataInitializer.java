package com.shashankpk.BugBridgeApi;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.shashankpk.BugBridgeApi.Model.User;
import com.shashankpk.BugBridgeApi.Repositories.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static final Logger logger=LoggerFactory.getLogger(DataInitializer.class);

	@Override
	public void run(String... args) throws Exception {
		try {
			User admin=new User();
			admin.setEmail("admin@bugbridge.com");
			admin.setPassword(passwordEncoder.encode("adminpassword"));
			admin.setUsername("admin");
			admin.setRole("ADMIN");
			admin.setFirstLogin(false);
			userRepository.save(admin);
			logger.info("admin created with the email as "+ admin.getEmail());
		} catch (Exception e) {
			logger.warn("Admin user already exists");
		}
		
		
	}
	
}
