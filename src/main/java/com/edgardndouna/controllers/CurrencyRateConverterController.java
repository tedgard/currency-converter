package com.edgardndouna.controllers;

import java.time.LocalDate;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.edgardndouna.config.ApiRateConverterException;
import com.edgardndouna.domain.QueryConversion;
import com.edgardndouna.domain.User;
import com.edgardndouna.services.ApiRateService;
import com.edgardndouna.services.QueryConversionService;

@Controller
public class CurrencyRateConverterController {

	private Logger logger = Logger.getLogger(this.getClass().getName()); 
	
	private QueryConversionService queryConversionService;	
	private ApiRateService apiRateService;
	
	@Autowired
	public void setApiRateService(ApiRateService apiRateService) {
		this.apiRateService = apiRateService;
	}
	
	@Autowired
	public void setQueryConversionService(QueryConversionService queryConversionService) {
		this.queryConversionService = queryConversionService;
	}
	
	@RequestMapping("/home")
	public String home(Authentication auth, Model model){
		User user = (User) auth.getPrincipal();
		
		logger.info("Landing on homepage | "+user);
		
		populateModelWithData("", "", "", LocalDate.now().toString(), model, user);
		
		return "homepage";
	}
	
	@RequestMapping("/convertRate")
	public String convertRate(@RequestParam String baseCurrency, @RequestParam String targetCurrency,
			@RequestParam String amount, @RequestParam String dateRate, Model model, Authentication auth){
		
		User user = (User) auth.getPrincipal();
		
		logger.info("Converting rate by user <"+user.getEmail()+"> | baseCurrency="+baseCurrency+", targetCurrency="+targetCurrency+", amount="+amount+", dateRate="+dateRate);
		
		//Sending the request to the Api Service
		double result = 0;
		try {

			//Effective call
			result = apiRateService.convertRateFromTo(baseCurrency, targetCurrency, dateRate, amount);
			
			//Saving query for history
			queryConversionService.saveQuery(new QueryConversion(baseCurrency, targetCurrency, Double.parseDouble(amount), dateRate, result, user));
			
			model.addAttribute("success", "On "+dateRate+", "+amount+" "+baseCurrency+" = "+result+" "+targetCurrency);
			
		} catch (ApiRateConverterException e) {
			model.addAttribute("failed", e.getMessage());
		}
		
		populateModelWithData(baseCurrency, targetCurrency, amount, dateRate, model, user);

		return "homepage";
	}

	/**
	 * Populating data before returning to the view 
	 * @param baseCurrency
	 * @param targetCurrency
	 * @param amount
	 * @param dateRate
	 * @param model
	 * @param user
	 */
	private void populateModelWithData(String baseCurrency, String targetCurrency, String amount, String dateRate,
			Model model, User user) {
	
		model.addAttribute("user", user);
		model.addAttribute("date", LocalDate.now());
		model.addAttribute("dateRate", dateRate);
		model.addAttribute("baseCurrency", baseCurrency);
		model.addAttribute("targetCurrency", targetCurrency);
		model.addAttribute("amount", amount);
		model.addAttribute("listOfCurrencies", apiRateService.getSupportedCurrencies());
		model.addAttribute("lastTenQueries",queryConversionService.loadLastTenQueriesPerformedByUser(user.getId()));
	}
}
