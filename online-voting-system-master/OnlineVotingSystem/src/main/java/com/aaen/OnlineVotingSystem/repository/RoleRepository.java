package com.aaen.OnlineVotingSystem.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.aaen.OnlineVotingSystem.model.Role;

/**
 * @author Mohammad Enayatullah
 *
 */
@Repository
public interface RoleRepository extends MongoRepository<Role, String> {

	public Optional<Role> findByName(String roleName);
}
