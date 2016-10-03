Feature: Login
	
	In order to use the application as a registered user
	I have to sign in beforehand

	Scenario: Valid Login
		Given I am a valid registered user : "john.doe@example.com" and "test1234"
		When I sign-in to system
		Then I should be redirected to home page

	Scenario: Invalid Login
		Given I am an invalid user : "jane.rae@example.com" and "qwerty1234"
		When I sign-in to system
		Then I should be told that I could not connect 
