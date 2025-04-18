package com.shashankpk.BugBridgeApi.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Team {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long teamId;
	String teamName;
	String teamDescription;
}
