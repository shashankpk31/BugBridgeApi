package com.shashankpk.BugBridgeApi.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shashankpk.BugBridgeApi.Constants.AuthConstants;
import com.shashankpk.BugBridgeApi.Dto.CreateTeamRequest;
import com.shashankpk.BugBridgeApi.Dto.CreateTeamResponse;
import com.shashankpk.BugBridgeApi.Dto.CreateUserRequest;
import com.shashankpk.BugBridgeApi.Dto.CreateUserResponse;
import com.shashankpk.BugBridgeApi.Model.Team;
import com.shashankpk.BugBridgeApi.Model.User;
import com.shashankpk.BugBridgeApi.Repositories.TeamRepository;
import com.shashankpk.BugBridgeApi.Repositories.UserRepository;
import com.shashankpk.BugBridgeApi.Services.EmailService;


@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000", 
			 methods = { 
				RequestMethod.GET,
				RequestMethod.POST,
				RequestMethod.OPTIONS 
			}, allowCredentials = "true")
public class AdminController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired 
	private EmailService emailService;
	
	@Autowired
	private TeamRepository teamRepository;
	
	@GetMapping("/getUsers")
	public ResponseEntity<List<User>> getUsers() {
		try {
			List<User> users=userRepository.findAll();
			return ResponseEntity.ok(users);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/createUser")
	public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest createUserRequest){
		try {
			User user=new User();
			if (userRepository.findByEmail(createUserRequest.getEmail()).isPresent() ||
				userRepository.findByUsername(createUserRequest.getUsername()).isPresent()) {
				ResponseEntity.badRequest().build();
			}
			user.setEmail(createUserRequest.getEmail());
			user.setUsername(createUserRequest.getUsername());
			user.setRole(AuthConstants.ROLE_USER.toString());
			String tempPassword = generateTemporaryPassword();
			user.setPassword(passwordEncoder.encode(tempPassword));
			userRepository.save(user);
			emailService.sendWelcomeEmail(createUserRequest.getEmail(), tempPassword);
			return  ResponseEntity.ok(new CreateUserResponse(true,user.getEmail()));
		}catch (Exception e) {
			ResponseEntity.badRequest().build();
		}
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/getTeams")
	public ResponseEntity<List<Team>> getTeams() {
		try {
			List<Team> teams=teamRepository.findAll();
			return ResponseEntity.ok(teams);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/createTeam")
	public ResponseEntity<CreateTeamResponse> createTeam(@RequestBody CreateTeamRequest createTeamRequest){
		try {
			Team team=new Team();
			team.setTeamName(createTeamRequest.getTeamName());
			team.setTeamDescription(createTeamRequest.getTeamDescription());
			teamRepository.save(team);
			return  ResponseEntity.ok(new CreateTeamResponse(true,team.getTeamName()+" team created Successfully."));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	
	private String generateTemporaryPassword() {
	    int length = 10;
	    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < length; i++) {
	        int randomIndex = (int) (Math.random() * characters.length());
	        sb.append(characters.charAt(randomIndex));
	    }
	    return sb.toString();
	}
}
