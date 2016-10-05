package com.edgardndouna.controllers;

import java.time.LocalDate;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.edgardndouna.config.ApiRateConverterException;
import com.edgardndouna.domain.QueryConversion;
import com.edgardndouna.services.ApiRateService;
import com.edgardndouna.services.QueryConversionService;
import com.edgardndouna.services.UserService;

@Controller
public class CurrencyRateConverterController {

	private Logger logger = Logger.getLogger(this.getClass().getName()); 
	
	private QueryConversionService queryConversionService;	
	private UserService userService;
	private ApiRateService apiRateService;
	
	@Autowired
	public void setApiRateService(ApiRateService apiRateService) {
		this.apiRateService = apiRateService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setQueryConversionService(QueryConversionService queryConversionService) {
		this.queryConversionService = queryConversionService;
	}
	
	@RequestMapping("/home")
	public String home(Model model){
		logger.info("Landing on homepage | user");
		model.addAttribute("user", userService.getUserById(1));
		
		model.addAttribute("date", LocalDate.now());
		model.addAttribute("dateRate", LocalDate.now());
		model.addAttribute("baseCurrency", "");
		model.addAttribute("targetCurrency", "");
		model.addAttribute("amount", "");
		model.addAttribute("lastTenQueries",queryConversionService.loadLastTenQueriesPerformedByUser(1));
		model.addAttribute("listOfCurrencies", apiRateService.getSupportedCurrencies());
		return "homepage";
	}
	
	@RequestMapping("/convertRate")
	public String convertRate(@RequestParam String baseCurrency, @RequestParam String targetCurrency,
			@RequestParam String amount, @RequestParam String dateRate, Model model){
		
		logger.info("Converting rate | baseCurrency="+baseCurrency+", targetCurrency="+targetCurrency+", amount="+amount+", dateRate="+dateRate);;
		
		model.addAttribute("user", userService.getUserById(1));
		
		model.addAttribute("date", LocalDate.now());
		model.addAttribute("dateRate", dateRate);
		model.addAttribute("baseCurrency", baseCurrency);
		model.addAttribute("targetCurrency", targetCurrency);
		model.addAttribute("amount", amount);
		model.addAttribute("listOfCurrencies", apiRateService.getSupportedCurrencies());
		
		//Sending the request to the Api Service
		double result = 0;
		try {

			//Effective call
			result = apiRateService.convertRateFromTo(baseCurrency, targetCurrency, dateRate, amount);
			
			//Saving query for history
			queryConversionService.saveQuery(new QueryConversion(baseCurrency, targetCurrency, Double.parseDouble(amount), dateRate, result, userService.getUserById(1)));
			
			model.addAttribute("success", "On "+dateRate+", "+amount+" "+baseCurrency+" = "+result+" "+targetCurrency);
			
		} catch (ApiRateConverterException e) {
			model.addAttribute("failed", e.getMessage());
		}

		model.addAttribute("lastTenQueries",queryConversionService.loadLastTenQueriesPerformedByUser(1));
		
		return "homepage";
	}
	
	
}
