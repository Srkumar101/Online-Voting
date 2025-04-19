package com.aaen.OnlineVotingSystem.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aaen.OnlineVotingSystem.model.Role;
import com.aaen.OnlineVotingSystem.service.RoleService;

/**
 * @author Mohammad Enayatullah
 *
 */
@RestController
@RequestMapping(path = { "/api/v1/roles" })
public class RoleController {

	@Autowired
	private RoleService service;

	@PostMapping
	@PreAuthorize("hasAuthority('roles.create')")
	public ResponseEntity<?> create(@RequestBody Role role) {

		if (Objects.isNull(role))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

		if (Objects.isNull(role.getName()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

		return ResponseEntity.status(service.create(role)).build();

	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('roles.get.by.id')")
	public ResponseEntity<?> getById(@PathVariable String id) {

		if (Objects.isNull(id))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

		var role = service.getById(id);

		if (Objects.nonNull(role))

			return ResponseEntity.ok(role);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

	}

	@GetMapping("/name/{name}")
	@PreAuthorize("hasAuthority('roles.get.by.name')")
	public ResponseEntity<?> getByName(@PathVariable String name) {

		if (Objects.isNull(name))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

		var role = service.getByName(name);

		if (Objects.nonNull(role))

			return ResponseEntity.ok(role);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

	}

	@GetMapping
	@PreAuthorize("hasAuthority('roles.get.all')")
	public ResponseEntity<?> gets() {

		var userList = service.getRoles();

		if (Objects.nonNull(userList)) {

			return ResponseEntity.status(HttpStatus.OK).body(userList);

		} else {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('roles.delete.by.id')")
	public ResponseEntity<?> deleteById(@PathVariable String id) {

		if (Objects.isNull(id))
			ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

		return ResponseEntity.status(service.deleteById(id)).build();

	}

	@DeleteMapping
	@PreAuthorize("hasAuthority('roles.delete.all')")
	public ResponseEntity<?> deleteAll() {

		return ResponseEntity.status(service.deleteRoles()).build();
	}

	@PutMapping
	@PreAuthorize("hasAuthority('roles.update')")
	public ResponseEntity<?> update(@RequestBody Role role) {

		if (Objects.isNull(role))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

		if (Objects.isNull(role.getId()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

		if (Objects.isNull(role.getName()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

		return ResponseEntity.status(service.update(role)).build();

	}

}
