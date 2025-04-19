package com.aaen.OnlineVotingSystem.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aaen.OnlineVotingSystem.exception.RecordNotFoundException;
import com.aaen.OnlineVotingSystem.model.PasswordReset;
import com.aaen.OnlineVotingSystem.model.Role;
import com.aaen.OnlineVotingSystem.model.User;
import com.aaen.OnlineVotingSystem.repository.UserRepository;
import com.aaen.OnlineVotingSystem.security.JwtUtil;
import com.aaen.OnlineVotingSystem.service.RoleService;
import com.aaen.OnlineVotingSystem.service.UserService;
import com.aaen.OnlineVotingSystem.util.Constant;
import com.aaen.OnlineVotingSystem.util.Validation;

/**
 * @author Mohammad Enayatullah
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private RoleService roleService;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public String createUser(User user) {

		user.setId(UUID.randomUUID().toString());

		if (Objects.nonNull(user.getEmail())) {

			Optional<User> optional = userRepository.findByEmail(user.getEmail());

			if (!optional.isPresent()) {

				user.setPassword(passwordEncoder.encode(user.getPassword().trim()));

				user.setEmail(user.getEmail().trim().toLowerCase());

				if (user.getRole().equals(Constant.ADMIN)) {

					Role role = roleService.getByName(user.getRole().trim());

					user.setRole(role.getId());

					userRepository.save(user);

				} else
					throw new RecordNotFoundException(Constant.NOT_CREATED);

				return Constant.CREATED;

			} else
				throw new RecordNotFoundException(Constant.EMAIL_ALREADY_EXISTS);

		} else
			throw new RecordNotFoundException(Constant.INVALID_EMAIL);
	}

	@Override
	public User getUser(String id, String token) {
		Map<String, String> role = jwtTokenUtil.extractRole(token.substring(7));

		if (role.containsKey(Constant.CANDIDATE) && role.get(Constant.CANDIDATE).equals(Constant.CANDIDATE)
				&& id.equals(jwtTokenUtil.extractId(token.substring(7)))) {

			Optional<User> optionalUser = userRepository.findById(id);

			if (optionalUser.isPresent()) {

				User user = optionalUser.get();

				if (user != null) {

					user.setPassword(null);

					return user;
				}
			}

		} else if (role.containsKey(Constant.ADMIN) && role.get(Constant.ADMIN).equals(Constant.ADMIN)) {

			Query query = new Query();
			query.addCriteria(Criteria.where(Constant._ID).is(id));
			query.fields().exclude(Constant.PASSWORD);
			query.limit(1);

			return mongoTemplate.findOne(query, User.class);
		}
		return null;
	}

	@Override
	public List<User> getUsers(String token) {

		Map<String, String> role = jwtTokenUtil.extractRole(token.substring(7));

		if (role.containsKey(Constant.CANDIDATE) && role.get(Constant.CANDIDATE).equals(Constant.CANDIDATE)) {

			Query query = new Query();
			query.addCriteria(Criteria.where(Constant._ID).is(jwtTokenUtil.extractId(token.substring(7))));
			query.fields().exclude(Constant.PASSWORD);
			query.limit(5000);

			return mongoTemplate.find(query, User.class);

		} else if (role.containsKey(Constant.ADMIN) && role.get(Constant.ADMIN).equals(Constant.ADMIN)) {

			Query query = new Query();
			query.fields().exclude(Constant.PASSWORD);
			query.limit(500);
			return mongoTemplate.find(query, User.class);
		}

		return null;
	}

	@Override
	public String deleteUser(String id) {

		boolean isFound = userRepository.existsById(id);

		if (isFound == true) {
			userRepository.deleteById(id);
			return Constant.DELETED;
		}
		return null;
	}

	@Override
	public String deleteAllUsers() {

		userRepository.deleteAll();

		return Constant.DELETED;
	}

	@Override
	public String updateUser(User user) {

		Optional<User> optionalUser = userRepository.findById(user.getId());

		if (optionalUser.isPresent()) {

			try {

				User updateUsers = optionalUser.get();

				if (user.getEmail() != null) {

					updateUsers.setEmail(user.getEmail().toLowerCase());
				}

				if (user.getName() != null) {

					updateUsers.setName(user.getName());
				}

				if (user.getPassword() != null) {

					updateUsers.setPassword(passwordEncoder.encode(user.getPassword()));
				}

				if (user.getRole() != null) {

					if (user.getRole().equals(Constant.ADMIN)) {

						Role role = roleService.getByName(user.getRole());

						updateUsers.setRole(role.getId());

					} else if (user.getRole().equals(Constant.CANDIDATE)) {

						Role role = roleService.getByName(user.getRole());

						updateUsers.setRole(role.getId());

					} else
						throw new RecordNotFoundException(Constant.USER_NOT_UPDATED_PROVIDE_ROLE);
				}

				if (user.getPermissions() != null) {

					updateUsers.setPermissions(user.getPermissions());
				}

				userRepository.save(updateUsers);

				return Constant.UPDATED;

			} catch (Exception e) {

				throw new RecordNotFoundException(Constant.USER_NOT_UPDATED);
			}
		} else
			throw new RecordNotFoundException(Constant.USER_UNAVAILABLE);
	}

	@Override
	public HttpStatus changePassword(PasswordReset passwordReset, String token) {

		String userId = jwtTokenUtil.extractId(token.substring(7));

		Optional<User> optionalUser = userRepository.findById(userId);
		User user = optionalUser.get();

		if (Validation.isValidPassword(passwordReset.getCurrentPassword())
				&& Validation.isValidPassword(passwordReset.getNewPassword())) {

			if (passwordEncoder.matches(passwordReset.getCurrentPassword(), user.getPassword())) {

				user.setPassword(passwordEncoder.encode(passwordReset.getNewPassword()));
				userRepository.save(user);

				return HttpStatus.NO_CONTENT;

			} else
				return HttpStatus.UNAUTHORIZED;
		}
		return HttpStatus.BAD_REQUEST;
	}

}
