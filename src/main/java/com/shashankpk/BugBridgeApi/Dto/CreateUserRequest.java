package com.shashankpk.BugBridgeApi.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserRequest {
	String username;
	String email;
	String password;
}
