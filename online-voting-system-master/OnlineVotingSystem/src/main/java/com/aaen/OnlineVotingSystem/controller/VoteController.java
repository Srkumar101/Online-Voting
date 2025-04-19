package com.aaen.OnlineVotingSystem.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aaen.OnlineVotingSystem.model.Vote;
import com.aaen.OnlineVotingSystem.security.JwtUtil;
import com.aaen.OnlineVotingSystem.service.VoteService;

/**
 * @author Mohammad Enayatullah
 *
 */
@RestController
@RequestMapping(path = { "/api/v1/votes" })
public class VoteController {

	@Autowired
	private VoteService service;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@PostMapping
	@PreAuthorize("hasAuthority('votes.create')")
	public ResponseEntity<?> create(@RequestBody Vote vote, @RequestHeader(name = "Authorization") String token) {

		if (Objects.isNull(vote))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

		return ResponseEntity.status(service.create(vote, jwtTokenUtil.extractId(token.substring(7)))).build();
	}

	@GetMapping
	@PreAuthorize("hasAuthority('votes.get.all')")
	public ResponseEntity<?> getVotes() {

		return ResponseEntity.ok(service.getVotes());
	}

	@GetMapping("/result")
	@PreAuthorize("hasAuthority('votes.get.result')")
	public ResponseEntity<?> getResult() {

		return ResponseEntity.ok(service.getResult());
	}
}
