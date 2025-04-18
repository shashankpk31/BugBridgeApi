package com.shashankpk.BugBridgeApi.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shashankpk.BugBridgeApi.DataInitializer;
import com.shashankpk.BugBridgeApi.Dto.LoginRequest;
import com.shashankpk.BugBridgeApi.Dto.LoginResponse;
import com.shashankpk.BugBridgeApi.Dto.ValidateTokenRequest;
import com.shashankpk.BugBridgeApi.Dto.ValidateTokenResponse;
import com.shashankpk.BugBridgeApi.Model.User;
import com.shashankpk.BugBridgeApi.Repositories.UserRepository;
import com.shashankpk.BugBridgeApi.Services.JwtUtil;



@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", 
			 methods = { 
				RequestMethod.GET,
				RequestMethod.POST,
				RequestMethod.OPTIONS 
			}, allowCredentials = "true")
public class AuthController {
	 @Autowired
	 private AuthenticationManager authenticationManager;
	 
	 @Autowired
	 private UserRepository userRepository;
	 
	 @Autowired
	 private JwtUtil jwtUtil;
	 
	 private static final Logger logger=LoggerFactory.getLogger(DataInitializer.class);

	 
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
		logger.info("login request expected with info");
		Authentication authentication=authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()) );
		String email=authentication.getName();
		String role=authentication.getAuthorities()
					.iterator().next().getAuthority();
		User user = userRepository.findByEmail(email)
		.orElseThrow(() -> new RuntimeException("User not found"));
		String token = jwtUtil.generateToken(email, role);
        return ResponseEntity.ok(new LoginResponse(token, user.isFirstLogin(),role));
	}
	
	@PostMapping("/validateToken")
	public ResponseEntity<ValidateTokenResponse> validateToken(@RequestBody ValidateTokenRequest validateTokenRequest){
		if(jwtUtil.validateToken(validateTokenRequest.getToken())){
			return ResponseEntity.ok(new ValidateTokenResponse(true));
		}else{
			return ResponseEntity.ok(new ValidateTokenResponse(false));
		}
	}

}
