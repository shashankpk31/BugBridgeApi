package com.shashankpk.BugBridgeApi.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
	String token;
	boolean firstLogin;
	String role;
}
