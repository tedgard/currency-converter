Feature: Currency rates conversion

	Convert any money value from one currency to another 
	at the latest/historical rates using an API endpoint.
	
	Scenario Outline: Successful currency conversion
	
		Given the data "<baseCurrency>", "<targetCurrency>", "<amount>" and "<historicalDate>"
		When the conversion function is run
		Then the ouput should be "<output>"
		
		Examples:
			| baseCurrency	| targetCurrency	| amount	| historicalDate	| output	|
			| USD			| EUR 	 			| 1			| 2016-09-30		| 0.89175	|
			| USD			| EUR 	 			| 3			| 2016-09-30		| 2.67525	|
		
	Scenario: Base currency and target currency should not be the same
		
		Given I have entered the same base "USD" and target "USD" currencies
		When the conversion function is run
		Then I should be told that the base and target currencies are the same
		
	
	Scenario: No amount or zero value to convert was provided
		 
		Given I have entered no amount "" or "0" value
		When the conversion function is run
		Then I should be told that no amount or 0 value has been provided
		
	
	Scenario: Date for historical rates should not be in the future
		
		Given I have entered a future date for historical rates "2020-01-01"
		When the conversion function is run 
		Then I should be told that the date is in the future
	
	Scenario: Error while communicating with the exchange rates API
		
		Given I have entered correct inputs:
		| baseCurrency	| targetCurrency	| amount	| historicalDate	|
		| USD			| EUR 	 			| 1			| 2016-09-30		|
		
		When the conversion function is run and fails
		Then I should be told that the conversion could not be performed
	
		
	
		
		
	