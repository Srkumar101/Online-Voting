package com.aaen.OnlineVotingSystem.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.aaen.OnlineVotingSystem.model.Candidate;
import com.aaen.OnlineVotingSystem.model.User;
import com.aaen.OnlineVotingSystem.model.Vote;
import com.aaen.OnlineVotingSystem.model.VoteCount;
import com.aaen.OnlineVotingSystem.repository.CandidateRepository;
import com.aaen.OnlineVotingSystem.repository.UserRepository;
import com.aaen.OnlineVotingSystem.repository.VoteCountRepository;
import com.aaen.OnlineVotingSystem.repository.VoteRepository;
import com.aaen.OnlineVotingSystem.service.VoteService;
import com.aaen.OnlineVotingSystem.util.Constant;

/**
 * @author Mohammad Enayatullah
 *
 */
@Service
public class VoteServiceImpl implements VoteService {

	@Autowired
	private VoteRepository repository;

	@Autowired
	private VoteCountRepository voteCountRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CandidateRepository candidateRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public HttpStatus create(Vote vote, String userId) {

		Optional<User> optional = userRepository.findById(userId);

		if (!optional.isPresent())
			return HttpStatus.BAD_REQUEST;

		Candidate candidate = candidateRepository.findByEmail(optional.get().getEmail());

		if (repository.existsByVoterIdNumber(candidate.getVoterIdNumber()))
			return HttpStatus.CONFLICT;

		if (Objects
				.isNull(mongoTemplate
						.findOne(
								new Query().addCriteria(Criteria.where(Constant.CONSTITUENCY)
										.is(candidate.getConstituency()).and(Constant.PARTY).is(vote.getParty())),
								Candidate.class)))
			return HttpStatus.BAD_REQUEST;

		vote.setId(UUID.randomUUID().toString());
		vote.setVoteDateTime(LocalDateTime.now());
		vote.setVoterName(candidate.getName());
		vote.setVoterIdNumber(candidate.getVoterIdNumber());
		vote.setConstituency(candidate.getConstituency());

		Vote response = repository.save(vote);

		if (Objects.nonNull(response)) {

			VoteCount voteCountResponse = mongoTemplate.findOne(new Query().addCriteria(Criteria
					.where(Constant.CONSTITUENCY).is(vote.getConstituency()).and(Constant.PARTY).is(vote.getParty())),
					VoteCount.class);

			VoteCount voteCount = VoteCount.builder().constituency(vote.getConstituency()).party(vote.getParty())
					.build();

			if (Objects.nonNull(voteCountResponse) && Objects.nonNull(voteCountResponse.getVoteCount())) {

				voteCount.setId(voteCountResponse.getId());
				voteCount.setVoteCount(voteCountResponse.getVoteCount() + 1);

			} else {

				voteCount.setId(UUID.randomUUID().toString());
				voteCount.setVoteCount(1);
			}

			voteCountRepository.save(voteCount);

			return HttpStatus.CREATED;
		}

		return HttpStatus.BAD_REQUEST;
	}

	@Override
	public List<Vote> getVotes() {

		return repository.findAll();
	}

	@Override
	public List<VoteCount> getResult() {

		return voteCountRepository.findAll();
	}

}
