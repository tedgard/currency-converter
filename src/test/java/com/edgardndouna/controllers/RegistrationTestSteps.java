package com.edgardndouna.controllers;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class RegistrationTestSteps {

	@Given("^I have chosen to register$")
	public void i_have_chosen_to_register() throws Throwable {
	    //the user clicks on the registration link
	}

	@When("^I register with valid details$")
	public void i_register_with_valid_details() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^I should be redirected to the home page$")
	public void i_should_be_redirected_to_the_home_page() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@When("^I enter an invalid email$")
	public void i_enter_an_invalid_email() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^I should be told that the email is incorrect$")
	public void i_should_be_told_that_the_email_is_incorrect() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@When("^I enter an email address that has already registered$")
	public void i_enter_an_email_address_that_has_already_registered() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^I should be told that the email already exists$")
	public void i_should_be_told_that_the_email_already_exists() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@When("^I enter a date of birth in the future$")
	public void i_enter_a_date_of_birth_in_the_future() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^I should be told that the date of birth is incorrect$")
	public void i_should_be_told_that_the_date_of_birth_is_incorrect() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}
	
}
