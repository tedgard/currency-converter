package com.edgardndouna.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.edgardndouna.services.QueryHistoryService;

@Controller
public class HomeController {

	private QueryHistoryService queryHistoryService;
	
	@Autowired
	public void setQueryHistoryService(QueryHistoryService queryHistoryService) {
		this.queryHistoryService = queryHistoryService;
	}
	
	@RequestMapping("/home")
	public String home(Model model){
		model.addAttribute("lastTenQueries",queryHistoryService.loadLastTenQueryHistory(1));
		return "home";
	}
	
}
