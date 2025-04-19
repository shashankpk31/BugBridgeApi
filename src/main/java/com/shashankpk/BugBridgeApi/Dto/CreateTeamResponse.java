package com.shashankpk.BugBridgeApi.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateTeamResponse {
	boolean creationStatus;
	String creationMessage;
}
