package com.edgardndouna.services;

import java.time.LocalDate;
import java.util.List;

import com.edgardndouna.domain.User;

public interface UserService {
	
	public List<String> getListOfCountries();
	
	public User authenticateUser(String email, String password);

	public User saveOrUpdate(User user);
	
	public User getUserById(int id);
	
	public boolean isEmailAlreadyRegistered(String email);
	
	public boolean isValidEmailAddress(String email);
	
	public boolean isReasonableDateOfBirth(LocalDate dateOrBirth);

}
