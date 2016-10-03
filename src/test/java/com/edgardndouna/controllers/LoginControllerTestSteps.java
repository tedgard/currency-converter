package com.edgardndouna.controllers;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.edgardndouna.services.UserService;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import domain.User;

public class LoginControllerTestSteps {

	private MockMvc mockMvc;

	@Mock //Mocking object
	UserService userService;
	
	@InjectMocks //sets up controller and injects mock objects into it
	LoginController loginController;

	private String email;
	private String password;
	
	//For testing the result of the controller in all "Then" methods
	private ResultActions resultSubmission;
	
	@Before
	public void setup(){
		loginController = new LoginController();
		mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
	}
	
	//Preparing the mockMvc for "When" methods
	public void initializeMockForAllGiven(String e, String p) throws Throwable{
		MockitoAnnotations.initMocks(this);
		
		mockMvc.perform(get("/login"))
    		.andExpect(status().isOk())
    		.andExpect(view().name("log-in"))
    		.andExpect(model().attribute("email", instanceOf(String.class)))
    		.andExpect(model().attribute("password", instanceOf(String.class)));
		
		verifyZeroInteractions(userService);
    	
    	email = e;
    	password = p;
	}
	
	
	@Given("^I am a valid registered user : \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_am_a_valid_registered_user_and(String e, String p) throws Throwable {
		
		initializeMockForAllGiven(e, p);
		
		User registeredUser = new User(2, "John Doe", "john.doe@example.com", "test1234", "1987-09-01", "1267 Dora St", "80000", "Amiens", "France");
		
		Mockito.when(userService.authenticateUser(e, p)).thenReturn(registeredUser);
	}

	@When("^I sign-in to system$")
	public void i_login_to_system() throws Throwable {
		
		resultSubmission = mockMvc.perform(post("/authenticateUser")
				.param("email", email)
				.param("password", password));
	}
	
	@Then("^I should be redirected to home page$")
	public void i_should_be_redirected_to_home_page() throws Throwable {
		resultSubmission
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/home"));
	}

	@Given("^I am an invalid user : \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_am_an_invalid_user_and(String e, String p) throws Throwable {
		
		initializeMockForAllGiven(e, p);
		
		Mockito.when(userService.authenticateUser(e, p)).thenReturn(null);
	}

	@Then("^I should be told that I could not connect$")
	public void i_should_be_told_that_I_could_not_connect() throws Throwable {
		
		resultSubmission
			.andExpect(status().isOk())
			.andExpect(view().name("log-in"))
			.andExpect(model().attribute("failed", is("Incorrect email or password")));
	}
	
}
