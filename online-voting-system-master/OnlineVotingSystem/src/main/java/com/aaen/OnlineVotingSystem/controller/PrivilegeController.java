package com.aaen.OnlineVotingSystem.controller;

import java.util.List;
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

import com.aaen.OnlineVotingSystem.exception.RecordNotFoundException;
import com.aaen.OnlineVotingSystem.model.PrivilegeRequestResponse;
import com.aaen.OnlineVotingSystem.service.PrivilegeService;

/**
 * @author Mohammad Enayatullah
 *
 */
@RestController
@RequestMapping(path = { "/api/v1/privilege" })
public class PrivilegeController {

	@Autowired(required = true)
	private PrivilegeService privilegeService;

	@PostMapping
	@PreAuthorize("hasAuthority('privilege.create')")
	public ResponseEntity<?> createPrivilege(@RequestBody PrivilegeRequestResponse privilege) {

		String response = privilegeService.createPrivilege(privilege);

		if (Objects.nonNull(response))
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		else
			return new ResponseEntity<>("Privilege creation failed", HttpStatus.NO_CONTENT);

	}

	@GetMapping(path = "/{id}")
	@PreAuthorize("hasAuthority('privilege.get.by.id')")
	public ResponseEntity<?> getPrivilegeById(@PathVariable String id) {

		PrivilegeRequestResponse response = privilegeService.getPrivilegeById(id);

		if (Objects.nonNull(response))
			return ResponseEntity.status(HttpStatus.OK).body(response);
		else
			throw new RecordNotFoundException("Invalid privilege id : " + id);
	}

	@GetMapping
	@PreAuthorize("hasAuthority('privilege.get.all')")
	public ResponseEntity<?> getPrivileges() {

		List<PrivilegeRequestResponse> response = privilegeService.getPrivileges();

		if (Objects.nonNull(response))
			return ResponseEntity.status(HttpStatus.OK).body(response);
		else
			throw new RecordNotFoundException("No records found");

	}

	@DeleteMapping(path = "/{id}")
	@PreAuthorize("hasAuthority('admin') and hasAuthority('privilege.delete.by.id')")
	public ResponseEntity<?> deletePrivilege(@PathVariable String id) {

		return ResponseEntity.status(privilegeService.deletePrivilege(id)).build();
	}

	@DeleteMapping
	@PreAuthorize("hasAuthority('admin') and hasAuthority('privilege.delete.all')")
	public ResponseEntity<?> deletePrivileges() {

		return ResponseEntity.status(privilegeService.deletePrivileges()).build();
	}

	@PutMapping
	@PreAuthorize("hasAuthority('privilege.update')")
	public ResponseEntity<?> updatePrivilege(@RequestBody PrivilegeRequestResponse privilege) {

		String response = privilegeService.updatePrivilege(privilege);

		if (Objects.nonNull(response))
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
		else
			throw new RecordNotFoundException("privilege update failed");

	}
}
