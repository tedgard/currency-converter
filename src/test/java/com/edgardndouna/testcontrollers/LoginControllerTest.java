package com.edgardndouna.testcontrollers;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
	monochrome = false,
	dryRun = false,
	features = "src/main/resources/features/login.feature"
)
public class LoginControllerTest {
}
