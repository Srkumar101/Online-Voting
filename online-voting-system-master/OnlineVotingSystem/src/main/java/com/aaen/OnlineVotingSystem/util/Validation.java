package com.aaen.OnlineVotingSystem.util;

import java.util.regex.Pattern;

/**
 * @author Mohammad Enayatullah
 *
 */
public class Validation {

	public static boolean isValidPassword(String password) {

		if (password == null)
			return false;

		return Pattern.compile(Regex.PASSWORD_VALIDATION).matcher(password).matches();
	}
}
