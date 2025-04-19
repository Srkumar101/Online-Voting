package com.aaen.OnlineVotingSystem.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aaen.OnlineVotingSystem.exception.RecordNotFoundException;
import com.aaen.OnlineVotingSystem.model.Authentication;
import com.aaen.OnlineVotingSystem.model.BlacklistedJwtToken;
import com.aaen.OnlineVotingSystem.model.User;
import com.aaen.OnlineVotingSystem.repository.UserRepository;
import com.aaen.OnlineVotingSystem.security.JwtUtil;
import com.aaen.OnlineVotingSystem.service.AuthenticationService;
import com.aaen.OnlineVotingSystem.service.BlacklistedJwtTokenService;
import com.aaen.OnlineVotingSystem.service.RoleService;
import com.aaen.OnlineVotingSystem.util.Constant;
import com.aaen.OnlineVotingSystem.util.JsonResponseUtil;
import com.mongodb.client.MongoDatabase;

/**
 * @author Mohammad Enayatullah
 *
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService, UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class.getName());

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private RoleService roleService;

	@Autowired
	private BlacklistedJwtTokenService blacklistedJwtTokenService;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public ResponseEntity<?> validateUser(Authentication authentication)
			throws UsernameNotFoundException, BadCredentialsException, RecordNotFoundException {

		try {

			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authentication.getEmail(), authentication.getPassword()));

		} catch (BadCredentialsException bd) {

			throw new BadCredentialsException(Constant.INVALID_EMAIL_OR_PASSWORD);

		} catch (RecordNotFoundException rn) {

			throw new RecordNotFoundException(Constant.RECORD_NOT_FOUND);
		}

		final User user = loadUserByEmail(authentication.getEmail());

		final String jwt = jwtTokenUtil.generateToken(user);

		Query query = new Query().addCriteria(Criteria.where(Constant._ID).is(user.getId()));
		query.fields().include(Constant.PERMISSIONS);

		final List<User> permissions = mongoTemplate.find(query, User.class);

		return ResponseEntity.ok(JsonResponseUtil.getResponse(Constant.SUCCESS, jwt, user.getName(), permissions));

	}

	public String ping() {

		try {

			MongoDatabase answer = mongoTemplate.getDb();

			return answer.getName();

		} catch (Exception e) {

			return e.getMessage();
		}
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		if (ping().equals(null)) {

			LOGGER.info(Constant.DATABASE_NOT_CONNECTED);
			throw new RecordNotFoundException(Constant.DATABASE_NOT_CONNECTED);
		}

		Optional<User> optional = userRepository.findByEmail(email);

		if (optional.isPresent()) {

			User user = optional.get();

			Set<GrantedAuthority> authorities = new HashSet<>();

			authorities.add(new SimpleGrantedAuthority(roleService.getById(user.getRole()).getName()));

			return new org.springframework.security.core.userdetails.User(user.getId(), user.getPassword(),
					authorities);
		} else
			throw new UsernameNotFoundException(Constant.USER_NOT_FOUND);
	}

	public User loadUserByEmail(String email) {

		Optional<User> optional = userRepository.findByEmail(email);

		if (optional.isPresent())
			return optional.get();

		return null;
	}

	@Override
	public String invalidateToken(String token) {

		BlacklistedJwtToken blacklistedJwtToken = new BlacklistedJwtToken();

		blacklistedJwtToken.setId(UUID.randomUUID().toString());
		blacklistedJwtToken.setToken(token.substring(7));

		return blacklistedJwtTokenService.createBlacklistedJwtToken(blacklistedJwtToken);
	}

}
