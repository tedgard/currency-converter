package com.edgardndouna.controllers;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.edgardndouna.services.UserService;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import domain.User;

public class RegistrationControllerTestSteps {

	
	private MockMvc mockMvc;
	
	@Mock //Mocking objects
	private UserService userService;
	
	@InjectMocks //sets up controller and injects mock objects into it
	private RegistrationController registrationController;
	
	private User user;
	private List<User> users;
	
	//For testing the result of the controller
	ResultActions resultSubmission;
	
	@Before
	public void setup(){
		registrationController = new RegistrationController();
		mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
		users = createStubUsers();
	}
	
	@Given("^I have chosen to register with valid input:$")
	public void i_have_chosen_to_register_with_valid_input(List<User> userInput) throws Throwable {
		
		MockitoAnnotations.initMocks(this);
		mockMvc.perform(get("/register"))
    	.andExpect(status().isOk())
    	.andExpect(view().name("registration"))
    	.andExpect(model().attribute("user", instanceOf(User.class)));
		
    	verifyZeroInteractions(userService);
    	
		user = userInput.get(0);
	}

	@When("^I submit the form$")
	public void i_submit_the_form() throws Throwable {
		
		MockitoAnnotations.initMocks(this);
		Mockito.when(userService.saveOrUpdate(Matchers.<User>any())).thenReturn(user);
		Mockito.when(userService.isReasonableDateOfBirth(Matchers.<Date>any())).thenReturn(true);
		Mockito.when(userService.isValidEmailAddress(Matchers.<String>any())).thenReturn(true);
		Mockito.when(userService.isEmailAlreadyRegistered(Matchers.<String>any())).thenReturn(false);
		
		resultSubmission = mockMvc.perform(post("/saveUser")
				.param("fullName", user.getFullName())
				.param("email", user.getEmail())
				.param("password", user.getPassword())
				.param("dateOfBirth", user.getDateOfBirth())
				.param("address", user.getAddress())
				.param("zipCode", user.getZipCode())
				.param("city", user.getCity())
				.param("country", user.getCountry()));
		
		verify(userService, atLeastOnce());
	}

	@Then("^I should be redirected to the home page$")
	public void i_should_be_redirected_to_the_home_page() throws Throwable {
		resultSubmission
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/home"));
	}

	@Given("^I have chosen to register with an invalid email:$")
	public void i_have_chosen_to_register_with_an_invalid_email(DataTable arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
	    // E,K,V must be a scalar (String, Integer, Date, enum etc)
	    throw new PendingException();
	}

	@Then("^I should be told that the email is incorrect$")
	public void i_should_be_told_that_the_email_is_incorrect() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Given("^I have chosen to register email address that has already registered:$")
	public void i_have_chosen_to_register_email_address_that_has_already_registered(DataTable arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
	    // E,K,V must be a scalar (String, Integer, Date, enum etc)
	    throw new PendingException();
	}

	@Then("^I should be told that the email already exists$")
	public void i_should_be_told_that_the_email_already_exists() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Given("^I have chosen to register with a date of birth in the future:$")
	public void i_have_chosen_to_register_with_a_date_of_birth_in_the_future(DataTable arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
	    // E,K,V must be a scalar (String, Integer, Date, enum etc)
	    throw new PendingException();
	}

	@Then("^I should be told that the date of birth is incorrect$")
	public void i_should_be_told_that_the_date_of_birth_is_incorrect() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}
	
	
	public List<User> createStubUsers(){
		return Arrays.asList(
				new User(1, "John Doe", "john.doe@example.com", "test1234", "1987-01-09", "45 Dora St", "80000", "Amiens", "France"),
				new User(2, "Mary Smith", "mary.smith@example.com", "test4321", "1988-09-01", "98 Jefferson St", "80000", "Amiens", "France"));
	}
	
}
