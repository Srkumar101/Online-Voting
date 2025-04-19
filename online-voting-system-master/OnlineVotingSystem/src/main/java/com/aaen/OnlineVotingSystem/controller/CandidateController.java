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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aaen.OnlineVotingSystem.model.Candidate;
import com.aaen.OnlineVotingSystem.service.CandidateService;
import com.mongodb.lang.Nullable;

/**
 * @author Mohammad Enayatullah
 *
 */
@RestController
@RequestMapping(path = { "/api/v1" })
public class CandidateController {

	@Autowired
	private CandidateService service;

	@PostMapping("/registration")
	public ResponseEntity<?> registerCandidate(@RequestParam("email") String email,
			@RequestParam("phoneNumber") Long phoneNumber, @RequestParam("password") String password,
			@RequestParam("name") String name, @RequestParam("role") String role,
			@Nullable @RequestParam("party") String party, @Nullable @RequestParam("background") String background,
			@RequestParam("constituency") String constituency,
			@Nullable @RequestParam("voterIdNumber") String voterIdNumber, @RequestParam("age") Integer age,
			@RequestParam("gender") String gender, @Nullable @RequestParam("symbol") MultipartFile symbol,
			@Nullable @RequestParam("imageOfCandidate") MultipartFile imageOfCandidate) {

		return ResponseEntity.status(service.registerCandidate(email, phoneNumber, password, name, role, party,
				background, constituency, voterIdNumber, age, gender, symbol, imageOfCandidate)).build();
	}

	@GetMapping("/candidate/{id}")
	@PreAuthorize("hasAuthority('candidates.get.by.id')")
	public ResponseEntity<?> getById(@PathVariable String id) {

		Candidate response = service.getById(id);

		return Objects.nonNull(response) ? ResponseEntity.ok(response)
				: ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@GetMapping("/candidate")
	@PreAuthorize("hasAuthority('candidates.get.all')")
	public ResponseEntity<?> getCandidates() {

		return ResponseEntity.ok(service.getCandidates());
	}

	@GetMapping("/candidate/approved")
	@PreAuthorize("hasAuthority('candidates.get.approved')")
	public ResponseEntity<?> getApprovedCandidates() {

		return ResponseEntity.ok(service.getApprovedCandidates());
	}

	@GetMapping("/candidate/pending")
	@PreAuthorize("hasAuthority('candidates.get.pending')")
	public ResponseEntity<?> getPendingCandidates() {

		return ResponseEntity.ok(service.getPendingCandidates());
	}

	@GetMapping("/candidate/denied")
	@PreAuthorize("hasAuthority('candidates.get.denied')")
	public ResponseEntity<?> getDeniedCandidates() {

		return ResponseEntity.ok(service.getDeniedCandidates());
	}

	@DeleteMapping("/candidate/{id}")
	@PreAuthorize("hasAuthority('candidates.delete.by.id')")
	public ResponseEntity<?> deleteById(@PathVariable String id) {

		return ResponseEntity.status(service.deleteById(id)).build();
	}

	@DeleteMapping("/candidate")
	@PreAuthorize("hasAuthority('candidates.delete.all')")
	public ResponseEntity<?> deleteCandidates() {

		return ResponseEntity.status(service.deleteCandidates()).build();
	}

	@PutMapping("/candidate")
	@PreAuthorize("hasAuthority('candidates.update')")
	public ResponseEntity<?> updateCandidates(@RequestParam("id") String id,
			@Nullable @RequestParam("email") String email, @Nullable @RequestParam("phoneNumber") Long phoneNumber,
			@Nullable @RequestParam("password") String password, @Nullable @RequestParam("name") String name,
			@Nullable @RequestParam("role") String role, @Nullable @RequestParam("party") String party,
			@Nullable @RequestParam("background") String background,
			@Nullable @RequestParam("constituency") String constituency,
			@Nullable @RequestParam("voterIdNumber") String voterIdNumber, @Nullable @RequestParam("age") Integer age,
			@Nullable @RequestParam("gender") String gender, @Nullable @RequestParam("symbol") MultipartFile symbol,
			@Nullable @RequestParam("imageOfCandidate") MultipartFile imageOfCandidate) {

		return ResponseEntity.status(service.updateCandidates(id, email, phoneNumber, password, name, role, party,
				background, constituency, voterIdNumber, age, gender, symbol, imageOfCandidate)).build();
	}

	@PutMapping("/candidate/approved/{id}")
	@PreAuthorize("hasAuthority('candidates.update.approved')")
	public ResponseEntity<?> approvedCandidateById(@PathVariable String id) {

		return ResponseEntity.status(service.approvedCandidateById(id)).build();
	}

	@PutMapping("/candidate/denied/{id}")
	@PreAuthorize("hasAuthority('candidates.update.denied')")
	public ResponseEntity<?> deniedCandidateById(@PathVariable String id) {

		return ResponseEntity.status(service.deniedCandidateById(id)).build();
	}

}
