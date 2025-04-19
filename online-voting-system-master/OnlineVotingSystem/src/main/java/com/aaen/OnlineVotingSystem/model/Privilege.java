package com.aaen.OnlineVotingSystem.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

/**
 * @author Mohammad Enayatullah
 *
 */
@SuppressWarnings("serial")
@Data
@Document(collection = "privilege")
public class Privilege implements GrantedAuthority {

	private String id;
	private String privilege;
	private String description;
	private String parent;
	private Boolean active;
	private Boolean accessibleForUi;
	private Boolean accessibleForUser;

	public Privilege(String privilege) {
		this.privilege = privilege;
	}

	@Override
	public String getAuthority() {
		return privilege;
	}

}
