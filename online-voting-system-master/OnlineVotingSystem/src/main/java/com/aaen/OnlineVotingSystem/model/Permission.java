package com.aaen.OnlineVotingSystem.model;

import java.util.ArrayList;

import org.springframework.lang.Nullable;

import lombok.Data;

/**
 * @author Mohammad Enayatullah
 *
 */
@Data
public class Permission {

	@Nullable
	private ArrayList<String> userIds;

	@Nullable
	private String roleName;

	private ArrayList<String> permissions;
}
