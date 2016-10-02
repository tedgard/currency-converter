package com.edgardndouna.services;

import domain.User;

public interface UserService {

	public User authenticateUser(String login, String password);

	public User saveOrUpdate(User user);
	
	public User getUserById(int id);
}
