package com.edgardndouna.testcontrollers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.mockito.InjectMocks;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.edgardndouna.config.ApiRateConfig;
import com.edgardndouna.controllers.CurrencyRateConverterController;
import com.edgardndouna.services.ApiRateService;
import com.edgardndouna.services.QueryConversionService;
import com.edgardndouna.services.UserService;
import com.edgardndouna.services.impl.ApiRateServiceImpl;
import com.edgardndouna.services.impl.QueryConversionServiceImpl;
import com.edgardndouna.services.impl.UserServiceImpl;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CurrencyRateConverterTestSteps {
	
	private static final String HOME_PAGE = "homepage";
	private String baseCurrency;
	private String targetCurrency;
	private String amount;
	private String historicalDate;
	
	private MockMvc mockMvc;

	private ApiRateService apiRateService; 
	private ApiRateConfig apiRateConfig;
	
	private UserService userService;
	private QueryConversionService queryConversionService;
	
	@InjectMocks //sets up controller and injects mock objects into it
	private CurrencyRateConverterController currencyRateConverterController;
	
	//For testing the result of the controller in all "Then" methods
	private ResultActions resultSubmission;
	
	@Before
	public void setup(){
		currencyRateConverterController = new CurrencyRateConverterController();
		
		apiRateConfig = new ApiRateConfig();
	 		apiRateConfig.setName("openexchangerates.org");
		 	apiRateConfig.setAppId("ad0462c83c7f444d8ed9128cc0538bd8");
		 	apiRateConfig.setAppIdField("app_id");
		 	apiRateConfig.setUrlLatestRate("https://openexchangerates.org/api/latest.json");
		 	apiRateConfig.setUrlHistoricalRate("https://openexchangerates.org/api/historical/");
		 	apiRateConfig.setUrlHistoricalRateExt(".json");
		 	apiRateConfig.setSupportedCurrencies("EUR,EUR,USD,GBP,NZD,AUD,JPY,HUF,ANG,CNY,PHP,SEK");
	 	
		apiRateService  = new ApiRateServiceImpl(apiRateConfig);
		userService 	= new UserServiceImpl();
		queryConversionService = new QueryConversionServiceImpl();
		
		currencyRateConverterController.setApiRateService(apiRateService);
		currencyRateConverterController.setQueryConversionService(queryConversionService);
		currencyRateConverterController.setUserService(userService);
		
		mockMvc = MockMvcBuilders.standaloneSetup(currencyRateConverterController).build();
	}
	
	
	@Given("^I have entered the following inputs : \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_have_entered_the_following_inputs_and(String base, String target, String a, String hDate) throws Throwable {
		
		this.baseCurrency = base;
	    this.targetCurrency = target;
	    this.amount = a;
	    this.historicalDate = hDate;
	    
	    mockMvc.perform(get("/home"))
			.andExpect(status().isOk())
			.andExpect(view().name(HOME_PAGE));
	}

	@When("^the conversion function is run$")
	public void the_conversion_function_is_run() throws Throwable {
	    
		resultSubmission = mockMvc.perform(post("/convertRate")
				.param("baseCurrency", baseCurrency)
				.param("targetCurrency", targetCurrency)
				.param("amount", amount)
				.param("dateRate", historicalDate));
		
	}

	@Then("^the ouput should be \"([^\"]*)\"$")
	public void the_ouput_should_be(String result) throws Throwable {
		resultSubmission
			.andExpect(status().isOk())
			.andExpect(view().name(HOME_PAGE))
			.andExpect(model().attribute("success", containsString(result)));
	}

	@Given("^I have entered the same base and target currencies$")
	public void i_have_entered_the_same_base_and_target_currencies(List<Map<String, String>> inputs) throws Throwable {
		
		extractDataFromParameters(inputs);
		
	}

	@Then("^I should be told that the base and target currencies are the same$")
	public void i_should_be_told_that_the_base_and_target_currencies_are_the_same() throws Throwable {
	    
		resultSubmission
			.andExpect(status().isOk())
			.andExpect(view().name(HOME_PAGE))
			.andExpect(model().attribute("failed", equalTo("Base and Target currencies are the same")));
		
	}

	@Given("^I have entered inputs with a zero amount value :$")
	public void i_have_entered_inputs_with_a_zero_amount_value(List<Map<String, String>> inputs) throws Throwable {
		
		extractDataFromParameters(inputs);
	}

	@Then("^I should be told to provide a non-zero amount to convert$")
	public void i_should_be_told_to_provide_a_non_zero_amount_to_convert() throws Throwable {
		
		resultSubmission
			.andExpect(status().isOk())
			.andExpect(view().name(HOME_PAGE))
			.andExpect(model().attribute("failed", equalTo("Please provide a non-zero amount to convert")));
	}

	@Given("^I have entered inputs with an invalid date for historical rates$")
	public void i_have_entered_inputs_with_an_invalid_date_for_historical_rates(List<Map<String, String>> inputs) throws Throwable {
		
		extractDataFromParameters(inputs);
		
	}

	@Then("^I should be told that the date is invalid$")
	public void i_should_be_told_that_the_date_is_invalid() throws Throwable {
		resultSubmission
			.andExpect(status().isOk())
			.andExpect(view().name(HOME_PAGE))
			.andExpect(model().attribute("failed", equalTo("Date rate format provided is invalid")));
	}

	@Given("^I have entered inputs with a future date for historical rates$")
	public void i_have_entered_inputs_with_a_future_date_for_historical_rates(List<Map<String, String>> inputs) throws Throwable {
	   
		extractDataFromParameters(inputs);
		
	}

	@Then("^I should be told that the date is in the future$")
	public void i_should_be_told_that_the_date_is_in_the_future() throws Throwable {
		resultSubmission
			.andExpect(status().isOk())
			.andExpect(view().name(HOME_PAGE))
			.andExpect(model().attribute("failed", equalTo("Date rate provided is in the future")));
	}

	@Given("^I have entered inputs with wrong baseCurrency :$")
	public void i_have_entered_inputs_with_wrong_baseCurrency(List<Map<String, String>> inputs) throws Throwable {
		
		extractDataFromParameters(inputs);
		
	}

	@Then("^I should be told that the base currency is not supported$")
	public void i_should_be_told_that_the_base_currency_is_not_supported() throws Throwable {
		resultSubmission
			.andExpect(status().isOk())
			.andExpect(view().name(HOME_PAGE))
			.andExpect(model().attribute("failed", Matchers.anyOf(
					Matchers.containsString("Invalid `base` currency"),
					Matchers.containsString(("Bad Request"))))
			 );
	}

	@Given("^I have entered inputs with wrong targetCurrency :$")
	public void i_have_entered_inputs_with_wrong_targetCurrency(List<Map<String, String>> inputs) throws Throwable {
	    extractDataFromParameters(inputs);
	}

	@Then("^I should be told that the target currency is not supported$")
	public void i_should_be_told_that_the_target_currency_is_not_supported() throws Throwable {
		resultSubmission
			.andExpect(status().isOk())
			.andExpect(view().name(HOME_PAGE))
			.andExpect(model().attribute("failed", equalTo("Target currency not supported")));
	}

	
	public void extractDataFromParameters(List<Map<String, String>> inputs) throws Throwable {
		
	    this.baseCurrency = inputs.get(0).get("baseCurrency").toString();
	    this.targetCurrency = inputs.get(0).get("targetCurrency").toString();
	    this.amount = inputs.get(0).get("amount");
	    this.historicalDate = inputs.get(0).get("historicalDate").toString();
	}
}
