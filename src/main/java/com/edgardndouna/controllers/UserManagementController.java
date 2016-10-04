package com.edgardndouna.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.edgardndouna.services.UserService;

@Controller
public class UserManagementController {

	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping("/account")
	public String userAccount(Model model){
		model.addAttribute("user", userService.getUserById(1));
		return "account";
	}

}
