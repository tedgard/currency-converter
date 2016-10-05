package com.edgardndouna.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.edgardndouna.domain.User;
import com.edgardndouna.services.UserService;
import com.edgardndouna.util.ToolBox;

@Controller
public class LoginController {

	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping("/login")
	public String login(Authentication auth, Model model){
		
		//If user already authenticated then redirect to home page
		if(auth != null)
			return "redirect:/home";
			
		model.addAttribute("email", "");
		model.addAttribute("password", "");
		return "log-in";
	}
	
	@RequestMapping(value="/authenticateUser", method= RequestMethod.POST)
	public String authenticateUser(@RequestParam String email, @RequestParam String password, Model model, HttpServletRequest request){
		
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
		
        //Using spring security for handling user context authentication
        ToolBox.authenticatedUser(user);
        
		return "redirect:/home";
	}

	@RequestMapping(value="/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
	
}
