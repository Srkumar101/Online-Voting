package com.aaen.OnlineVotingSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.aaen.OnlineVotingSystem.util.Constant;

/**
 * @author Mohammad Enayatullah
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException() {

		super(Constant.RECORD_NOT_FOUND);
	}
}