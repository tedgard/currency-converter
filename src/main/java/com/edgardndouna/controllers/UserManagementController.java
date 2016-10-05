package com.edgardndouna.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserManagementController {
	
	@RequestMapping("/account")
	public String userAccount(Authentication auth, Model model){
		model.addAttribute("user", auth.getPrincipal());
		return "account";
	}
	
}
