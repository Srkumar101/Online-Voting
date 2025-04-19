package com.aaen.OnlineVotingSystem.util;

/**
 * @author Mohammad Enayatullah
 *
 */
public class Constant {

	public static final String CREATED = "Created";
	public static final String NOT_CREATED = "Not created";
	public static final String DELETED = "Deleted";
	public static final String UPDATED = "Updated";
	public static final String RECORD_NOT_FOUND = "Record not found";
	public static final String MESSAGE = "message";
	public static final String TOKEN = "token";

	/*
	 * Authentication
	 */
	public static final String SUCCESS = "Success";
	public static final String INVALID_EMAIL_OR_PASSWORD = "Invalid email or password";
	public static final String _ID = "_id";
	public static final String PERMISSIONS = "permissions";
	public static final String DATABASE_CONNECTED = "Database connected";
	public static final String DATABASE_NOT_CONNECTED = "Database not connected";
	public static final String USER_NOT_FOUND = "User not found";

	/*
	 * User
	 */
	public static final String ADMIN = "admin";
	public static final String CANDIDATE = "candidate";
	public static final String VOTER = "voter";
	public static final String ROLE_ID = "roleId";
	public static final String PASSWORD = "password";
	public static final String USER_CREATED = "User saved successfully";
	public static final String USER_UPDATED = "User updated successfully";
	public static final String USER_NOT_UPDATED = "Update user failed";
	public static final String USER_UNAVAILABLE = "User not exist";
	public static final String EMAIL_ALREADY_EXISTS = "Email id already exist";
	public static final String ALL_USER_DELETED = "All users are deleted";
	public static final String USER_DELETED = "User deleted";
	public static final String USER_NOT_UPDATED_PROVIDE_ROLE = "Not updated, please provide valid role name";
	public static final String USER_NOT_CREATED_PROVIDE_ROLE = "Not created, please provide valid role name";
	public static final String INVALID_EMAIL = "Please provide valid email id";

	/*
	 * Resource
	 */
	public static final String NAME = "name";

	/*
	 * Candidate
	 */
	public static final String PENDING = "pending";
	public static final String APPROVED = "approved";
	public static final String DENIED = "denied";
	public static final String STATE = "state";

	/*
	 * Vote
	 */
	public static final String CONSTITUENCY = "constituency";
	public static final String PARTY = "party";
}
