package com.edgardndouna.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.edgardndouna.services.QueryConversionService;

@Controller
public class HomeController {

	private QueryConversionService queryConversionService;
	
	@Autowired
	public void setQueryConversionService(QueryConversionService queryConversionService) {
		this.queryConversionService = queryConversionService;
	}
	
	@RequestMapping("/home")
	public String home(Model model){
		model.addAttribute("lastTenQueries",queryConversionService.loadLastTenQueriesPerformedByUser(1));
		return "home";
	}
	
}
