package com.aaen.OnlineVotingSystem.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.aaen.OnlineVotingSystem.model.VoteCount;

/**
 * @author Mohammad Enayatullah
 *
 */
@Repository
public interface VoteCountRepository extends MongoRepository<VoteCount, String> {

}