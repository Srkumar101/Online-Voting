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
@Document(collection = "voteCount")
public class VoteCount {

	@Id
	private String id;
	private String constituency;
	private String party;
	private Integer voteCount;
}
