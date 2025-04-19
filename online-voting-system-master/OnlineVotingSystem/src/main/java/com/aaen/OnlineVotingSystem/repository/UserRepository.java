package com.aaen.OnlineVotingSystem.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.aaen.OnlineVotingSystem.model.User;

/**
 * @author Mohammad Enayatullah
 *
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

	public Optional<User> findByEmail(String email);
}