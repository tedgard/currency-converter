package com.edgardndouna.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.edgardndouna.domain.User;
import com.edgardndouna.services.UserService;

@Controller
public class LoginController {

	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping("/login")
	public String login(Model model){
		model.addAttribute("email", "");
		model.addAttribute("password", "");
		return "log-in";
	}
	
	@RequestMapping(value="/authenticateUser", method= RequestMethod.POST)
	public String authenticateUser(@RequestParam String email, @RequestParam String password, Model model){
		
		logger.info("Authenticating user | email: "+email+ ", password: "+password);
		
		//Checking if all the inputs have been provided 
		if(email.isEmpty() || password.isEmpty()){
			model.addAttribute("failed", "Please fill all the fields");
			model.addAttribute("email", email);
			model.addAttribute("password", password);
			logger.warn("Email or password not provided | "+email+ ", password: "+password);
			return "log-in";
		}
        
		User user = userService.authenticateUser(email, password);
		
        if(user == null){
			model.addAttribute("failed", "Incorrect email or password");
			model.addAttribute("email", email);
			model.addAttribute("password", password);
			logger.warn("Incorrect email or password for signing-in | "+email+ ", password: "+password);
			return "log-in";
		}
		
		return "redirect:/home";
	}
}
