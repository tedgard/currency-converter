package com.edgardndouna.testservices;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import com.edgardndouna.config.ApiRateConfig;
import com.edgardndouna.config.ApiRateConverterException;
import com.edgardndouna.services.ApiRateService;
import com.edgardndouna.services.impl.ApiRateServiceImpl;

public class ApiRateServiceTest {
	
	private ApiRateService apiRateService;
	
	private ApiRateConfig apiRateConfig;
	
	@Before
	public void setupUserService(){
		 apiRateConfig = new ApiRateConfig();
		 	apiRateConfig.setName("openexchangerates.org");
		 	apiRateConfig.setAppId("ad0462c83c7f444d8ed9128cc0538bd8");
		 	apiRateConfig.setAppIdField("app_id");
		 	apiRateConfig.setUrlLatestRate("https://openexchangerates.org/api/latest.json");
		 	apiRateConfig.setUrlHistoricalRate("https://openexchangerates.org/api/historical/");
		 	apiRateConfig.setUrlHistoricalRateExt(".json");
		 	apiRateConfig.setSupportedCurrencies("EUR,EUR,USD,GBP,NZD,AUD,JPY,HUF,ANG,CNY,PHP,SEK");
		 	
		 apiRateService = new ApiRateServiceImpl(apiRateConfig);
	}
	
	@Test
	public void shouldReturnTheExpectedValue() throws ApiRateConverterException{
		
		double result = apiRateService.convertRateFromTo("USD", "EUR", "2016-09-30", "1.0");
		
		assertThat(result, Matchers.equalTo(0.889389));
	}
	
	
	@Test(expected = ApiRateConverterException.class)
	public void shouldNotProcessFutureDateRate() throws ApiRateConverterException{
		
		try {
			apiRateService.convertRateFromTo("USD", "EUR", "2020-09-30", "1.0");
		} catch (Exception e) {
			assertThat(e.getMessage(), Matchers.equalTo("Date rate provided is in the future"));
			throw e;
		}
	}
	
	@Test(expected = ApiRateConverterException.class)
	public void shouldNotProcessWithAnInvalidApiKey() throws ApiRateConverterException{
		
		apiRateConfig.setAppId("loremipsum");
		
		try {
			apiRateService.convertRateFromTo("USD", "EUR", "2010-09-30", "1.0");
		} catch (Exception e) {
			assertThat(e.getMessage(), Matchers.anyOf(
					Matchers.containsString("An unknown error has occured with the Api"),
					Matchers.containsString("Communication error with the Rates Converter Api")));
			throw e;
		}
	}
	
	@Test(expected = ApiRateConverterException.class)
	public void shouldNotProcessUnknownBaseCurrency() throws ApiRateConverterException{
		
		try {
			apiRateService.convertRateFromTo("ZZZZ", "EUR", "2016-09-30", "1.0");
		} catch (Exception e) {
			assertThat(e.getMessage(), Matchers.anyOf(
					Matchers.containsString("Invalid `base` currency"),
					Matchers.containsString("Bad Request")));
			throw e;
		}
	}
	
	@Test(expected = ApiRateConverterException.class)
	public void shouldNotProcessWhenBaseAndTargetCurrencyAreTheSame() throws ApiRateConverterException{
		
		try {
			apiRateService.convertRateFromTo("USD", "USD", "2016-09-30", "1.0");
		} catch (Exception e) {
			assertThat(e.getMessage(), Matchers.containsString("Base and Target currencies are the same"));
			throw e;
		}
	}
	
	@Test(expected = ApiRateConverterException.class)
	public void shouldNotProcessUnknownTargetCurrency() throws ApiRateConverterException{
		
		try {
			apiRateService.convertRateFromTo("USD", "EUPR", "2016-09-30", "1.0");
		} catch (Exception e) {
			assertThat(e.getMessage(), Matchers.equalTo("Target currency not supported"));
			throw e;
		}
	}
	
	@Test(expected = ApiRateConverterException.class)
	public void shouldNotProcessIfZeroAmountProvided() throws ApiRateConverterException{
		
		try {
			apiRateService.convertRateFromTo("USD", "EUP", "2016-09-30", "0");
		} catch (Exception e) {
			assertThat(e.getMessage(), Matchers.equalTo("Please provide a non-zero amount to convert"));
			throw e;
		}
	}
	
}
