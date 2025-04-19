package com.aaen.OnlineVotingSystem.model;

import lombok.Data;

/**
 * @author Mohammad Enayatullah
 *
 */
@Data
public class PasswordReset {

	private String currentPassword;
	private String newPassword;
}
