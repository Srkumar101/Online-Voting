package com.aaen.OnlineVotingSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.aaen.OnlineVotingSystem.model.Vote;
import com.aaen.OnlineVotingSystem.model.VoteCount;

/**
 * @author Mohammad Enayatullah
 *
 */
@Component
@Qualifier(value = "VoteService")
public interface VoteService {

	public HttpStatus create(Vote vote, String userId);

	public List<Vote> getVotes();

	public List<VoteCount> getResult();
}
