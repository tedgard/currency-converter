Feature: Registration

	Registration should be quick and easy
	with some verification regarding the provided inputs
	
	Scenario: Successful registration 
	
		New users should be redirected to the home page once 
		the registration has succeeded
		
		Given I have chosen to register with valid input:
		| fullName | email				| password	| dateOfBirth | address 		| zipCode	| city 		| country |
		| Edgard   | edgard@example.com | test1234	| 1987-09-01  | 827 London St	| 80000		| Amiens	| France  |
		When I submit the form 
		Then I should be redirected to the home page
		
	Scenario: Invalid email address provided
	
		Someone tries to provide an invalid email address
		
		Given I have chosen to register with an invalid email:
		| fullName | email				| password	| dateOfBirth | address 		| zipCode	| city 		| country |
		| John Doe | test@test  		| test1234	| 1987-09-01  | 123 John Doe St	| 80000		| Amiens	| France  |
		When I submit the form 
		Then I should be told that the email is incorrect
		
	Scenario: Duplicate email address provided
	
		Someone tries to use an email address that already exists
		
		Given I have chosen to register email address that has already registered:
		| fullName   | email				 | password	| dateOfBirth | address 		| zipCode	| city 		| country |
		| Mary Smith | john.doe@example.com  | test3211	| 1988-02-02  | 123 John Doe St	| 80000		| Amiens	| France  |
		When I submit the form
		Then I should be told that the email already exists
		
	Scenario: Should enter a reasonable date of birth
	
		The user has to enter a reasonable date of birth namely in the past
		
		Given I have chosen to register with a date of birth in the future:
		| fullName | email				   | password	| dateOfBirth | address 		| zipCode	| city 		| country |
		| John Doe | john.doe@example.com  | test1234	| 2020-01-01  | 123 John Doe St	| 80000		| Amiens	| France  |
		When I submit the form 
		Then I should be told that the date of birth should be in the past
		