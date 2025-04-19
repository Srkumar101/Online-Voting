package com.aaen.OnlineVotingSystem.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * @author Mohammad Enayatullah
 *
 */
@Data
@Document(collection = "privilege")
public class PrivilegeRequestResponse {

	@Id
	private String id;
	private String privilege;
	private String description;
	private String parent;
	private Boolean active;
	private Boolean accessibleForUi;
	private Boolean accessibleForUser;

}
