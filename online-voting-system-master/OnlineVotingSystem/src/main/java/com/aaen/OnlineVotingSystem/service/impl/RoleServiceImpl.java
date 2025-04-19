package com.aaen.OnlineVotingSystem.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.aaen.OnlineVotingSystem.model.Role;
import com.aaen.OnlineVotingSystem.repository.RoleRepository;
import com.aaen.OnlineVotingSystem.service.RoleService;

/**
 * @author Mohammad Enayatullah
 *
 */
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository repository;

	@Override
	public HttpStatus create(Role role) {

		role.setId(UUID.randomUUID().toString());

		repository.save(role);

		return HttpStatus.CREATED;
	}

	@Override
	public Role getById(String id) {

		Optional<Role> optional = repository.findById(id);

		return optional.get();
	}

	@Override
	public Role getByName(String name) {

		Optional<Role> optional = repository.findByName(name);

		return optional.get();
	}

	@Override
	public List<Role> getRoles() {

		return repository.findAll();
	}

	@Override
	public HttpStatus deleteById(String id) {

		repository.deleteById(id);

		return HttpStatus.NO_CONTENT;
	}

	@Override
	public HttpStatus deleteRoles() {

		repository.deleteAll();

		return HttpStatus.NO_CONTENT;
	}

	@Override
	public HttpStatus update(Role role) {

		Role dbRole = getById(role.getId());

		if (Objects.nonNull(dbRole)) {

			if (dbRole.getName() != null)
				dbRole.setName(role.getName());

			repository.save(dbRole);

			return HttpStatus.NO_CONTENT;
		}

		return HttpStatus.NOT_FOUND;
	}

}
