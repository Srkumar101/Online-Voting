package com.aaen.OnlineVotingSystem.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.aaen.OnlineVotingSystem.model.Candidate;

/**
 * @author Mohammad Enayatullah
 *
 */
@Repository
public interface CandidateRepository extends MongoRepository<Candidate, String> {

	public Candidate findByEmail(String email);
}