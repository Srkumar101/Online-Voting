package com.aaen.OnlineVotingSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.aaen.OnlineVotingSystem.model.Candidate;

/**
 * @author Mohammad Enayatullah
 *
 */
@Component
@Qualifier(value = "CandidateService")
public interface CandidateService {

	public HttpStatus registerCandidate(String email, Long phoneNumber, String password, String name, String role,
			String party, String background, String constituency, String voterIdNumber, Integer age, String gender,
			MultipartFile symbol, MultipartFile imageOfCandidate);

	public Candidate getById(String id);

	public List<Candidate> getCandidates();

	public List<Candidate> getApprovedCandidates();

	public List<Candidate> getPendingCandidates();

	public List<Candidate> getDeniedCandidates();

	public HttpStatus deleteById(String id);

	public HttpStatus deleteCandidates();

	public HttpStatus updateCandidates(String id, String email, Long phoneNumber, String password, String name,
			String role, String party, String background, String constituency, String voterIdNumber, Integer age,
			String gender, MultipartFile symbol, MultipartFile imageOfCandidate);

	public HttpStatus approvedCandidateById(String id);

	public HttpStatus deniedCandidateById(String id);
}
