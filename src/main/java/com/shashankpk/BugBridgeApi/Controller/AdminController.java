package com.shashankpk.BugBridgeApi.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000", 
			 methods = { 
				RequestMethod.GET,
				RequestMethod.POST,
				RequestMethod.OPTIONS 
			}, allowCredentials = "true")
public class AdminController {
	
	@PostMapping("/createUser")
	public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest){
		
	}
	
	
}
