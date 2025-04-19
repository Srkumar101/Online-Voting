package com.aaen.OnlineVotingSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.aaen.OnlineVotingSystem.model.Role;

/**
 * @author Mohammad Enayatullah
 *
 */
@Component
@Qualifier(value = "RoleService")
public interface RoleService {

	public HttpStatus create(Role role);

	public Role getById(String id);

	public Role getByName(String name);

	public List<Role> getRoles();

	public HttpStatus deleteById(String id);

	public HttpStatus deleteRoles();

	public HttpStatus update(Role role);
}
