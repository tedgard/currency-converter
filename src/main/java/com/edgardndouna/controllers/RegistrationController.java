package com.edgardndouna.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.edgardndouna.services.UserService;

import domain.User;

@Controller
public class RegistrationController {

	private static final String REGISTER_PAGE = "registration";
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping("/register")
	public String register(Model model){
		model.addAttribute("user", new User());
		return REGISTER_PAGE;
	}
	
	@RequestMapping(value="/registerUser", method= RequestMethod.POST)
	public String saveOrUpdateUser(User user, Model model){
		
		logger.info("Registering new user : "+user);
		
		//Checking if all the inputs have been provided 
		if(user.getFullName().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty() ||
				user.getDateOfBirth().isEmpty() || user.getAddress().isEmpty() || user.getCity().isEmpty() ||
				user.getZipCode().isEmpty() || user.getCountry().isEmpty()){
			
			model.addAttribute("failed", "Please fill all the fields");
			model.addAttribute("user", user);
			logger.warn("One or more inputs are missing | "+user);
			return REGISTER_PAGE;
		}
		
		//Checking the date format provided
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date dateOfBirth = null;
        try {
            dateOfBirth = formatter.parse(user.getDateOfBirth());
        } catch (Exception e) {
        	model.addAttribute("failed", "Invalid date format provided for date of birth");
        	model.addAttribute("user", user);
        	logger.warn("Invalid date format provided for date of birth | "+user);
			return REGISTER_PAGE;
        }
        
        if(!userService.isReasonableDateOfBirth(dateOfBirth)){
			model.addAttribute("failed", "The date of birth should be in the past");
			model.addAttribute("user", user);
			logger.warn("Not reasonable date of birth provided | "+user);
			return REGISTER_PAGE;
		}
		
		if(!userService.isValidEmailAddress(user.getEmail())){
			model.addAttribute("failed", "The email address "+user.getEmail()+" is not valid");
			model.addAttribute("user", user);
			logger.warn("Invalid email address provided | "+user);
			return REGISTER_PAGE;
		}
		
		if(userService.isEmailAlreadyRegistered(user.getEmail())){
			model.addAttribute("failed", "The email address "+user.getEmail()+" already exists");
			model.addAttribute("user", user);
			logger.warn("Already registered email address provided | "+user);
			return REGISTER_PAGE;
		}
		
		userService.saveOrUpdate(user);
		return "redirect:/home";
	}
}
