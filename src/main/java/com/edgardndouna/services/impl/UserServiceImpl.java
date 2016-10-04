package com.edgardndouna.services.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.edgardndouna.domain.User;
import com.edgardndouna.domain.util.ToolBox;
import com.edgardndouna.services.UserService;

@Service
@Profile("dev")
public class UserServiceImpl implements UserService {

	private List<User> users;
	
	public UserServiceImpl() {
		loadUsers();
	}
	
	public List<String> getListOfCountries(){
		return ToolBox.generateListOfCountries();
	}
	
	@Override
	public User authenticateUser(String email, String password) {
		User user = null;
		
		try {
			user = users.stream().filter(u -> {
						if(u.getEmail().equals(email) && u.getPassword().equals(password))
							return true;
						return false;
					}).findAny().get();
		} catch (Exception e) { }
		
		return user; 
	}
	
	@Override
	public User getUserById(int id){
		User user = null;
		try {
			user = users.stream().filter(item -> item.getId() == id).findFirst().get();
		} catch (Exception e) { }
		
		return user;
	}
	
	@Override
	public boolean isEmailAlreadyRegistered(String email) {
		boolean result = true;
		
		try {
			User user = (users.stream().filter(item -> item.getEmail().equals(email)).findFirst().get());
			if(user != null) result = true;
		} catch (Exception e) { 
			result = false;
		}
		
		return result; 
	}
	
	@Override
	public boolean isValidEmailAddress(String email) {
		return ToolBox.checkIfValidEmail(email);
	}
	
	@Override
	public boolean isReasonableDateOfBirth(LocalDate dateOrBirth){
		return ToolBox.checkIfDateIsInTheFuture(dateOrBirth);
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
		
		//TODO:To remove
		System.out.println("--- New User Elements : "+users);
		
		return user;
	}
	
	
	public void loadUsers(){
		users = new ArrayList<>();
		users.add(new User(1, "John Doe", "john.doe@example.com", "test1234", "1987-01-09", "45 Dora St", "80000", "Amiens", "France"));
		users.add(new User(2, "Mary Smith", "mary.smith@example.com", "test4321", "1988-09-01", "98 Jefferson St", "80000", "Amiens", "France"));
	}

}
