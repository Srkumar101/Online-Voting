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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aaen.OnlineVotingSystem.exception.RecordNotFoundException;
import com.aaen.OnlineVotingSystem.model.PasswordReset;
import com.aaen.OnlineVotingSystem.model.User;
import com.aaen.OnlineVotingSystem.service.UserService;

/**
 * @author Mohammad Enayatullah
 *
 */
@RestController
@RequestMapping(path = { "/api/v1/users" })
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	@PreAuthorize("hasAuthority('users.create')")
	public ResponseEntity<?> createUser(@RequestBody User user) {

		String response = userService.createUser(user);

		if (Objects.nonNull(response))
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		else
			return new ResponseEntity<>("user creation failed", HttpStatus.NO_CONTENT);

	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('users.get.by.id')")
	public ResponseEntity<?> getUserById(@PathVariable String id, @RequestHeader(name = "Authorization") String token) {

		User user = userService.getUser(id, token);

		if (Objects.nonNull(user))
			return ResponseEntity.status(HttpStatus.OK).body(user);
		else
			throw new RecordNotFoundException("Invalid User id : " + id);

	}

	@GetMapping
	@PreAuthorize("hasAuthority('users.get.all')")
	public ResponseEntity<?> getUsers(@RequestHeader(name = "Authorization") String token) {

		return ResponseEntity.ok(userService.getUsers(token));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('admin') and hasAuthority('users.delete.by.id')")
	public ResponseEntity<?> deleteUserById(@PathVariable String id) {

		String response = userService.deleteUser(id);

		if (Objects.nonNull(response))
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("{\"success\":\"User deleted sucessfully\"}");
		else
			throw new RecordNotFoundException("Invalid User id : " + id);

	}

	@DeleteMapping
	@PreAuthorize("hasAuthority('admin') and hasAuthority('users.delete.all')")
	public ResponseEntity<?> deleteAllUser() {

		String response = userService.deleteAllUsers();

		if (Objects.nonNull(response))
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
		else
			throw new RecordNotFoundException("No Record Found");

	}

	@PutMapping
	public ResponseEntity<?> updateUser(@RequestBody User user) {

		String response = userService.updateUser(user);

		if (Objects.nonNull(response))
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
		else
			throw new RecordNotFoundException("User update failed");

	}

	@PutMapping("/change-password")
	@PreAuthorize("hasAuthority('admin') or hasAuthority('users.update.changePassword')")
	public ResponseEntity<?> changePassword(@RequestBody PasswordReset passwordReset,
			@RequestHeader(name = "Authorization") String token) {

		switch (userService.changePassword(passwordReset, token)) {

		case NO_CONTENT:
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

		case BAD_REQUEST:
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

		case UNAUTHORIZED:
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		default:
			throw new RecordNotFoundException("Password update failed");
		}
	}
}
