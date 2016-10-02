package com.edgardndouna.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edgardndouna.services.UserService;

import domain.User;

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
	
	@RequestMapping(value="/updateUser", method= RequestMethod.POST)
	public String saveOrUpdateUser(User user, RedirectAttributes redirectAttributes){
		userService.saveOrUpdate(user);
		redirectAttributes.addFlashAttribute("success", "Your information has been successfully updated");
		return "redirect:/account";
	}
	
}
