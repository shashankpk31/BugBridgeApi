package com.shashankpk.BugBridgeApi.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shashankpk.BugBridgeApi.Repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepository.findByEmail(email)
				.map(user -> org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
						.password(user.getPassword()).authorities(user.getRole()).build())
				.orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
	}

}
