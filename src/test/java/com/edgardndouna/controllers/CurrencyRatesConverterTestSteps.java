package com.edgardndouna.controllers;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CurrencyRatesConverterTestSteps {
	
	private String baseCurrency;
	private String targetCurrency;
	private double amount;
	private String historicalDate;
	private double result;
	
	@Given("^mmmthe data \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")
	public void the_data_and(String baseCurrency, String targetCurrency, 
			String amount, String historicalDate) throws Throwable {
	    this.baseCurrency = baseCurrency;
	    this.targetCurrency = targetCurrency;
	    this.amount = Double.parseDouble(amount);
	    this.historicalDate = historicalDate;
	    this.result = 0.0;
	}
}
