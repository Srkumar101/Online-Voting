package com.aaen.OnlineVotingSystem.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aaen.OnlineVotingSystem.model.Candidate;
import com.aaen.OnlineVotingSystem.model.Role;
import com.aaen.OnlineVotingSystem.model.User;
import com.aaen.OnlineVotingSystem.repository.CandidateRepository;
import com.aaen.OnlineVotingSystem.repository.UserRepository;
import com.aaen.OnlineVotingSystem.service.CandidateService;
import com.aaen.OnlineVotingSystem.service.RoleService;
import com.aaen.OnlineVotingSystem.util.Constant;

/**
 * @author Mohammad Enayatullah
 *
 */
@Service
public class CandidateServiceImpl implements CandidateService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleService roleService;

	@Autowired
	private CandidateRepository repository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	String[] voterPermission = { "votes.create", "votes.get.all", "votes.get.result" };

	String[] candidatePermission = { "votes.get.all", "votes.get.result" };

	@Override
	public HttpStatus registerCandidate(String email, Long phoneNumber, String password, String name, String role,
			String party, String background, String constituency, String voterIdNumber, Integer age, String gender,
			MultipartFile symbol, MultipartFile imageOfCandidate) {

		try {

			Candidate candidate = null;

			if (role.equals(Constant.CANDIDATE))

				candidate = Candidate.builder().id(UUID.randomUUID().toString()).email(email.trim().toLowerCase())
						.phoneNo(phoneNumber).password(passwordEncoder.encode(password)).name(name.trim())
						.role(roleService.getByName(role).getId()).party(party.trim().toLowerCase())
						.background(background.trim().toLowerCase()).constituency(constituency.trim().toLowerCase())
						.age(age).gender(gender.trim().toLowerCase())
						.symbol(new Binary(BsonBinarySubType.BINARY, symbol.getBytes()))
						.imageOfCandidate(new Binary(BsonBinarySubType.BINARY, imageOfCandidate.getBytes()))
						.state(Constant.PENDING).build();

			else if (role.equals(Constant.VOTER))

				candidate = Candidate.builder().id(UUID.randomUUID().toString()).email(email.trim().toLowerCase())
						.phoneNo(phoneNumber).password(passwordEncoder.encode(password)).name(name.trim())
						.role(roleService.getByName(role).getId()).constituency(constituency.trim().toLowerCase())
						.voterIdNumber(voterIdNumber.trim().toUpperCase()).age(age).gender(gender.trim().toLowerCase())
						.imageOfCandidate(new Binary(BsonBinarySubType.BINARY, imageOfCandidate.getBytes()))
						.state(Constant.PENDING).build();

			mongoTemplate.save(candidate);

			return HttpStatus.CREATED;

		} catch (IOException e) {

			return HttpStatus.BAD_REQUEST;
		}
	}

	@Override
	public Candidate getById(String id) {

		Optional<Candidate> optional = repository.findById(id);

		return optional.isPresent() ? optional.get() : null;
	}

	@Override
	public List<Candidate> getCandidates() {

		return repository.findAll();
	}

	@Override
	public List<Candidate> getApprovedCandidates() {

		return mongoTemplate.find(new Query().addCriteria(Criteria.where(Constant.STATE).is(Constant.APPROVED)),
				Candidate.class);
	}

	@Override
	public List<Candidate> getPendingCandidates() {

		return mongoTemplate.find(new Query().addCriteria(Criteria.where(Constant.STATE).is(Constant.PENDING)),
				Candidate.class);
	}

	@Override
	public List<Candidate> getDeniedCandidates() {

		return mongoTemplate.find(new Query().addCriteria(Criteria.where(Constant.STATE).is(Constant.DENIED)),
				Candidate.class);
	}

	@Override
	public HttpStatus deleteById(String id) {

		repository.deleteById(id);

		return HttpStatus.NO_CONTENT;
	}

	@Override
	public HttpStatus deleteCandidates() {

		repository.deleteAll();

		return HttpStatus.NO_CONTENT;
	}

	@Override
	public HttpStatus updateCandidates(String id, String email, Long phoneNumber, String password, String name,
			String role, String party, String background, String constituency, String voterIdNumber, Integer age,
			String gender, MultipartFile symbol, MultipartFile imageOfCandidate) {

		try {

			if (Objects.nonNull(id)) {

				Candidate candidate = getById(id);

				if (Objects.isNull(candidate))
					return HttpStatus.NOT_FOUND;

				if (Objects.nonNull(email))
					candidate.setEmail(email);

				if (Objects.nonNull(phoneNumber))
					candidate.setPhoneNo(phoneNumber);

				if (Objects.nonNull(password))
					candidate.setPassword(passwordEncoder.encode(password));

				if (Objects.nonNull(name))
					candidate.setName(name);

				if (Objects.nonNull(role))
					candidate.setRole(roleService.getByName(role).getId());

				if (Objects.nonNull(party))
					candidate.setParty(party);

				if (Objects.nonNull(background))
					candidate.setBackground(background);

				if (Objects.nonNull(constituency))
					candidate.setConstituency(constituency);

				if (Objects.nonNull(voterIdNumber))
					candidate.setVoterIdNumber(voterIdNumber);

				if (Objects.nonNull(age))
					candidate.setAge(age);

				if (Objects.nonNull(gender))
					candidate.setGender(gender);

				if (Objects.nonNull(symbol))
					candidate.setSymbol(new Binary(BsonBinarySubType.BINARY, symbol.getBytes()));

				if (Objects.nonNull(imageOfCandidate))
					candidate.setImageOfCandidate(new Binary(BsonBinarySubType.BINARY, imageOfCandidate.getBytes()));

				repository.save(candidate);

				return HttpStatus.NO_CONTENT;
			}

			return HttpStatus.NOT_FOUND;

		} catch (Exception e) {

			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}

	@Override
	public HttpStatus approvedCandidateById(String id) {

		if (Objects.isNull(id))
			return HttpStatus.BAD_REQUEST;

		Candidate candidate = getById(id);

		candidate.setState(Constant.APPROVED);

		Candidate response = repository.save(candidate);

		if (Objects.nonNull(response)) {

			User user = User.builder().id(UUID.randomUUID().toString()).email(candidate.getEmail())
					.password(candidate.getPassword()).name(candidate.getName()).role(candidate.getRole()).build();

			Role role = roleService.getById(candidate.getRole());

			if (role.getName().equals(Constant.CANDIDATE))
				user.setPermissions(candidatePermission);

			else if (role.getName().equals(Constant.VOTER))
				user.setPermissions(voterPermission);

			userRepository.save(user);

			return HttpStatus.NO_CONTENT;
		}

		return HttpStatus.BAD_REQUEST;
	}

	@Override
	public HttpStatus deniedCandidateById(String id) {

		if (Objects.isNull(id))
			return HttpStatus.BAD_REQUEST;

		Candidate candidate = getById(id);

		candidate.setState(Constant.DENIED);

		repository.save(candidate);

		return HttpStatus.NO_CONTENT;
	}

}
