Feature: Login
	
	In order to use the application as a registered user
	I have to sign in beforehand

	Scenario: Valid Login
		Given I am a valid registered user
		When I login to system
		Then I should be redirected to the home page

	Scenario: Invalid Login
		Given I am an invalid user
		When I login to system
		Then I should be told that I could not connect 