package com.aaen.OnlineVotingSystem.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * @author Mohammad Enayatullah
 *
 */
@Data
@Document(collection = "vote")
public class Vote {

	@Id
	private String id;
	private String voterName;
	private String voterIdNumber;
	private String party;
	private LocalDateTime voteDateTime;
	private String constituency;
}
