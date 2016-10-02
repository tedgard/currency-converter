package com.edgardndouna.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.edgardndouna.services.UserService;

import domain.User;

@Controller
public class RegistrationController {

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping("/register")
	public String register(Model model){
		model.addAttribute("user", new User());
		return "registration";
	}
	
	@RequestMapping(value="/saveUser", method= RequestMethod.POST)
	public String saveOrUpdateUser(User user){
		//TODO:Connection and session creation
		userService.saveOrUpdate(user);
		return "redirect:/home";
	}
}
