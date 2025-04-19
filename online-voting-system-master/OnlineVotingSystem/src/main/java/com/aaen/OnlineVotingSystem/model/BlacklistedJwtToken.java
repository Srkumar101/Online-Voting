package com.aaen.OnlineVotingSystem.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * @author Mohammad Enayatullah
 *
 */
@Data
@Document(collection = "blacklistedJwtToken")
public class BlacklistedJwtToken {

	@Id
	private String id;
	private String token;
}
