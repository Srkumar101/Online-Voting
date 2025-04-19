package com.aaen.OnlineVotingSystem.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.aaen.OnlineVotingSystem.model.Vote;

/**
 * @author Mohammad Enayatullah
 *
 */
@Repository
public interface VoteRepository extends MongoRepository<Vote, String> {

	public Boolean existsByVoterIdNumber(String voterIdNumber);
}