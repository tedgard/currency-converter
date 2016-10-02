package com.edgardndouna.services;

import java.util.Date;

import domain.User;

public interface UserService {

	public User authenticateUser(String login, String password);

	public User saveOrUpdate(User user);
	
	public User getUserById(int id);
	
	public boolean isEmailAlreadyRegistered(String email);
	
	public boolean isValidEmailAddress(String email);
	
	public boolean isReasonableDateOfBirth(Date dateOrBirth);

}
