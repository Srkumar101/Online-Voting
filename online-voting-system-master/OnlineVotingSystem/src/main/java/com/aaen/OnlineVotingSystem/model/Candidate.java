package com.aaen.OnlineVotingSystem.model;

import org.bson.types.Binary;
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
@Document(collection = "candidate")
public class Candidate {

	@Id
	private String id;
	private String email;
	private Long phoneNo;
	private String password;
	private String name;
	private String role;
	private String[] permissions;
	private String party;
	private String constituency;
	private String background;
	private String voterIdNumber;
	private Integer age;
	private String gender;
	private String state;
	private Binary symbol;
	private Binary imageOfCandidate;
}
