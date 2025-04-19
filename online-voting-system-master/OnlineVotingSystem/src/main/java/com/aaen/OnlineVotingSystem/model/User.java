package com.aaen.OnlineVotingSystem.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Mohammad Enayatullah
 *
 */
@Builder
@Getter
@Setter
@Document(collection = "user")
public class User {

	@Id
	private String id;
	private String email;
	private String password;
	private String name;
	private String role;
	private String[] permissions;

}
