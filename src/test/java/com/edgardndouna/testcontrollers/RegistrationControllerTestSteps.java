package com.edgardndouna.testcontrollers;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.edgardndouna.controllers.RegistrationController;
import com.edgardndouna.domain.User;
import com.edgardndouna.services.UserService;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class RegistrationControllerTestSteps {

	
	private static final String REGISTER_PAGE = "registration";

	private MockMvc mockMvc;
	
	@Mock //Mocking object
	private UserService userService;
	
	@InjectMocks //sets up controller and injects mock objects into it
	private RegistrationController registrationController;
	
	private User user;
	
	//For testing the result of the controller in all "Then" methods
	private ResultActions resultSubmission;
	
	@Before
	public void setup(){
		registrationController = new RegistrationController();
		mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
	}
	
	//Preparing the mockMvc for "When" methods
	public void initializeMockForAllGiven(User u) throws Throwable{
		MockitoAnnotations.initMocks(this);
		
		mockMvc.perform(get("/register"))
    		.andExpect(status().isOk())
    		.andExpect(view().name("registration"))
    		.andExpect(model().attribute("user", instanceOf(User.class)));
    	
    	user = u;
	}
	
	@Given("^I have chosen to register with valid input:$")
	public void i_have_chosen_to_register_with_valid_input(List<User> userInput) throws Throwable {
		
		initializeMockForAllGiven(userInput.get(0));
		
		Mockito.when(userService.isReasonableDateOfBirth(LocalDate.parse(user.getDateOfBirth()))).thenReturn(true);
		Mockito.when(userService.isValidEmailAddress(user.getEmail())).thenReturn(true);
		Mockito.when(userService.isEmailAlreadyRegistered(user.getEmail())).thenReturn(false);
		Mockito.when(userService.saveOrUpdate(Matchers.<User>any())).thenReturn(user);
	}

	@When("^I submit the form$")
	public void i_submit_the_form() throws Throwable {
	
		resultSubmission = mockMvc.perform(post("/registerUser")
				.param("fullName", user.getFullName())
				.param("email", user.getEmail())
				.param("password", user.getPassword())
				.param("dateOfBirth", user.getDateOfBirth())
				.param("address", user.getAddress())
				.param("zipCode", user.getZipCode())
				.param("city", user.getCity())
				.param("country", user.getCountry()));
		
	}

	@Then("^I should be redirected to the home page$")
	public void i_should_be_redirected_to_the_home_page() throws Throwable {
		resultSubmission
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/home"));
	}

	@Given("^I have chosen to register with an invalid email:$")
	public void i_have_chosen_to_register_with_an_invalid_email(List<User> userInput) throws Throwable {
		
		initializeMockForAllGiven(userInput.get(0));
		
		Mockito.when(userService.isReasonableDateOfBirth(LocalDate.parse(user.getDateOfBirth()))).thenReturn(true);
		Mockito.when(userService.isValidEmailAddress(user.getEmail())).thenReturn(false);
		Mockito.when(userService.isEmailAlreadyRegistered(user.getEmail())).thenReturn(false);

	}

	@Then("^I should be told that the email is incorrect$")
	public void i_should_be_told_that_the_email_is_incorrect() throws Throwable {
		
		resultSubmission
			.andExpect(status().isOk())
			.andExpect(view().name(REGISTER_PAGE))
			.andExpect(model().attribute("failed", is("The email address "+user.getEmail()+" is not valid")));
	}

	@Given("^I have chosen to register email address that has already registered:$")
	public void i_have_chosen_to_register_email_address_that_has_already_registered(List<User> userInput) throws Throwable {
		
		initializeMockForAllGiven(userInput.get(0));
		
		Mockito.when(userService.isEmailAlreadyRegistered(user.getEmail())).thenReturn(true);
		Mockito.when(userService.isReasonableDateOfBirth(LocalDate.parse(user.getDateOfBirth()))).thenReturn(true);
		Mockito.when(userService.isValidEmailAddress(user.getEmail())).thenReturn(true);
	}

	@Then("^I should be told that the email already exists$")
	public void i_should_be_told_that_the_email_already_exists() throws Throwable {
	
		resultSubmission
			.andExpect(status().isOk())
			.andExpect(view().name(REGISTER_PAGE))
			.andExpect(model().attribute("failed", is("The email address "+user.getEmail()+" already exists")));
	}

	@Given("^I have chosen to register with a date of birth in the future:$")
	public void i_have_chosen_to_register_with_a_date_of_birth_in_the_future(List<User> userInput) throws Throwable {
		
		initializeMockForAllGiven(userInput.get(0));
		
		Mockito.when(userService.isReasonableDateOfBirth(LocalDate.parse(user.getDateOfBirth()))).thenReturn(false);
		Mockito.when(userService.isValidEmailAddress(user.getEmail())).thenReturn(true);
		Mockito.when(userService.isEmailAlreadyRegistered(user.getEmail())).thenReturn(false);
	}

	@Then("^I should be told that the date of birth should be in the past$")
	public void i_should_be_told_that_the_date_of_birth_is_incorrect() throws Throwable {
		
		resultSubmission
			.andExpect(status().isOk())
			.andExpect(view().name(REGISTER_PAGE))
			.andExpect(model().attribute("failed", is("The date of birth should be in the past")));
	}
		
}
