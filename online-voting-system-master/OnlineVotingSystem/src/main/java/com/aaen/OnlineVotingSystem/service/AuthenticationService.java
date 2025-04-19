package com.aaen.OnlineVotingSystem.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import com.aaen.OnlineVotingSystem.exception.RecordNotFoundException;
import com.aaen.OnlineVotingSystem.model.Authentication;

/**
 * @author Mohammad Enayatullah
 *
 */
@Component
@Qualifier("AuthenticationService")
public interface AuthenticationService {

	public ResponseEntity<?> validateUser(Authentication authentication)
			throws BadCredentialsException, RecordNotFoundException;

	public String invalidateToken(String token);
}
