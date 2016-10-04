Feature: Currency rates conversion

	Convert any money value from one currency to another 
	at the latest/historical rates using an API endpoint.
	
	Scenario Outline: Successful currency conversion
	
		Given I have entered the following inputs : "<baseCurrency>", "<targetCurrency>", "<amount>" and "<historicalDate>"
		When the conversion function is run
		Then the ouput should be "<output>"
		
		Examples:
			| baseCurrency	| targetCurrency	| amount	| historicalDate	| output	|
			| USD			| EUR 	 			| 1			| 2016-09-30		| 0.889389	|
			| USD			| EUR 	 			| 3			| 2016-09-30		| 2.668167	|
		
	Scenario: Base currency and target currency should not be the same
		
		Given I have entered the same base and target currencies
		| baseCurrency	| targetCurrency	| amount	| historicalDate |
		| USD			| USD 	 			| 1			| 2016-10-01	 |
		When the conversion function is run
		Then I should be told that the base and target currencies are the same
		
	
	Scenario: Zero amount value cannot be processed
		 
		Given I have entered inputs with a zero amount value :
		| baseCurrency	| targetCurrency	| amount	| historicalDate |
		| USD			| EUR 	 			| 0			| 2016-10-02	 |
		When the conversion function is run
		Then I should be told to provide a non-zero amount to convert
		
	
	Scenario: Date for historical rates should be well-formatted
		
		Given I have entered inputs with an invalid date for historical rates 
		| baseCurrency	| targetCurrency	| amount	| historicalDate |
		| USD			| EUR 	 			| 1			| 20-09-30		 |
		When the conversion function is run 
		Then I should be told that the date is invalid
		
	
	Scenario: Date for historical rates should not be in the future
		
		Given I have entered inputs with a future date for historical rates
		| baseCurrency	| targetCurrency	| amount	| historicalDate |
		| USD			| EUR 	 			| 1			| 2030-09-30	 |
		When the conversion function is run 
		Then I should be told that the date is in the future
	
	
	Scenario: Base currency not supported by the exchange rates Api
		
		Given I have entered inputs with wrong baseCurrency :
		| baseCurrency	| targetCurrency	| amount	| historicalDate |
		| XXXX			| EUR 	 			| 1			| 2016-09-30	 |
		
		When the conversion function is run
		Then I should be told that the base currency is not supported
	
	
	Scenario: Target currency not supported by the exchange rates Api
		
		Given I have entered inputs with wrong targetCurrency :
		| baseCurrency	| targetCurrency	| amount	| historicalDate |
		| USD			| ZZZZ 	 			| 1			| 2016-09-30	 |
		
		When the conversion function is run
		Then I should be told that the target currency is not supported
			
