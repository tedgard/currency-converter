package com.edgardndouna.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.validator.EmailValidator;
import org.springframework.stereotype.Service;

import domain.User;

@Service
public class UserServiceImpl implements UserService {

	private List<User> users;
	
	public UserServiceImpl() {
		loadUsers();
	}
	
	@Override
	public User authenticateUser(String login, String password) {
		return users.get(0);
	}
	
	@Override
	public User getUserById(int id){
		return users.stream().filter(item -> item.getId() == id).findFirst().get();
	}
	
	@Override
	public boolean isEmailAlreadyRegistered(String email) {
		return (users.stream().filter(item -> item.getEmail().equals(email)).findFirst().get() == null);
	}
	
	@Override
	public boolean isValidEmailAddress(String email) {
		boolean result = true;
		EmailValidator validator = EmailValidator.getInstance();
		try {
			if (!validator.isValid(email))
				result = false;
		} catch (Exception ex) {
			result = false;
		}
	   return result;
	}
	
	@Override
	public boolean isReasonableDateOfBirth(Date dateOrBirth){
		Date now = new Date();
		if(now.after(dateOrBirth)) return true;		
		return false;
	}
	

	@Override
	public User saveOrUpdate(User user) {
		
		if(user == null)
			throw new RuntimeException("User can't be null !");
			
		User userStored = null;
		if(user.getId() != null){
			userStored = users.stream().filter(item -> item.getId() == user.getId()).findFirst().get();
			users.remove(userStored);
		}
		else{
			user.setId(users.size()+1);
		}
		
		users.add(user);
		System.out.println("--- New user lists : "+users);
		
		return user;
	}
	
	
	public void loadUsers(){
		users = new ArrayList<>();
	}

}
