package com.aaen.OnlineVotingSystem.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * @author Mohammad Enayatullah
 *
 */
@Data
@Document(collection = "role")
public class Role {

	@Id
	private String id;
	private String name;
}
