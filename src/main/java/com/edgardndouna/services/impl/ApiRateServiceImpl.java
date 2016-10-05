package com.edgardndouna.services.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.edgardndouna.config.ApiRateConfig;
import com.edgardndouna.config.ApiRateConverterException;
import com.edgardndouna.services.ApiRateService;

@Service
public class ApiRateServiceImpl implements ApiRateService {

	private static final String ERRO_DESCRIPTION_FIELD = "description";
	private static final String ERROR_FIELD = "error";
	private static final String RATES_FIELD = "rates";

	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	private ApiRateConfig apiConfig;
	
	@Autowired
	public ApiRateServiceImpl(ApiRateConfig apiConfig) {
		this.apiConfig = apiConfig;
	}
	
	public List<String> getSupportedCurrencies(){
		return apiConfig.getSupportedCurrencies();
	}
	
	@SuppressWarnings("unchecked")
	public double convertRateFromTo(String baseCurrency, String targetCurrency, String dateRate, String amount)
			throws ApiRateConverterException {
		
		logger.info("Firing up ConvertRateFromTo (Endpoint Api: "+apiConfig.getName()+") With : baseCurrency="+baseCurrency+", targetCurrency="+targetCurrency+", dateRate="+dateRate+", amount="+amount);
		
		//Transforming no dateRate into now for a 'latest' query
		if(dateRate == null || dateRate.isEmpty())
			dateRate = LocalDate.now().toString();
		
		//Aborting if no amount to convert
		if(amount == null || amount.isEmpty() || Double.parseDouble(amount) ==0 ){
			throw new ApiRateConverterException("Please provide a non-zero amount to convert");
		}
		
		//Aborting if dateRate format is invalid
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			LocalDate.parse(dateRate, formatter);
		} catch (Exception e1) {
			throw new ApiRateConverterException("Date rate format provided is invalid");
		}
		
		//Aborting if baseCurrency and targetCurrency are the same
		if(baseCurrency.equals(targetCurrency))
			throw new ApiRateConverterException("Base and Target currencies are the same");
		
		//Aborting if dateRate provided is in the future
		if(LocalDate.parse(dateRate).isAfter(LocalDate.now()))
			throw new ApiRateConverterException("Date rate provided is in the future");
		
		//RestFul caller
		RestTemplate restTemplate = new RestTemplate();
		Map<String, Object> response = null;
		String url = null;
		
		//We need to determine if this is a "historical" query or a "latest" one
		if(dateRate.equals(LocalDate.now().toString())){

			//Dealing with the latest rate query 
			logger.info("Firing up section "+apiConfig.getUrlLatestRate()+ " | For DateRate: "+dateRate);
			url = composeRESTFulUrlCall(apiConfig.getUrlLatestRate(),"?",apiConfig.getAppIdField(), "=", apiConfig.getAppId(), "&", "base", "=", baseCurrency);
		}
		else{
			
			//Dealing with a historical rate query 
			logger.info("Firing up section "+apiConfig.getUrlHistoricalRate()+ " | For DateRate: "+dateRate);
			url = composeRESTFulUrlCall(apiConfig.getUrlHistoricalRate(),dateRate, apiConfig.getUrlHistoricalRateExt(),"?",apiConfig.getAppIdField(), "=", apiConfig.getAppId(), "&", "base", "=", baseCurrency);
		}	
		
		logger.info("Final Url composed : "+url);
		
		try {
			response = restTemplate.getForObject(url, HashMap.class);
		} catch (RestClientException e) {
			logger.error("Error while communicating with the Rate Api | URL: "+url+" | ErrorMessage: "+e.getMessage());
			throw new ApiRateConverterException("Communication error with the Rates Converter Api : "+e.getMessage());
		}
		
		//Checking if there is any error 
		if(response.get(ERROR_FIELD) != null){
			throw new ApiRateConverterException(
					(response.get(ERRO_DESCRIPTION_FIELD) != null ?
							response.get(ERRO_DESCRIPTION_FIELD).toString() : 
								"An unknown error has occured with the Api"));
		}
		
		//Here we can access the rates, from response, in search of our targetCurrency
		logger.debug("RESTFul Api Response: "+response);
		Map<String, Object> rates = (Map<String, Object>) response.get(RATES_FIELD);
		
		//Aborting if targetCurrency is not supported
		if(rates.get(targetCurrency) == null){
			throw new ApiRateConverterException(
					(response.get(ERRO_DESCRIPTION_FIELD) != null ?
							response.get(ERRO_DESCRIPTION_FIELD).toString() : 
								"Target currency not supported"));
		}
		
		double targetRate = Double.parseDouble(rates.get(targetCurrency).toString());
		double resultConv = Double.parseDouble(amount) * targetRate;
		
		logger.info("Result after conversion (amount="+amount+", from="+baseCurrency+", to="+targetCurrency+", dateRate:"+dateRate+") : "+resultConv+" | With TargetRate: "+targetRate);
		
		//Wrapping it all up 
		return resultConv;
	}
	
	/*
	 * Composing the final RestFul Url before the call
	 */
	private String composeRESTFulUrlCall(String url, String...params){
		
		StringBuilder finalUrl = new StringBuilder();
			finalUrl.append(url);
			for(String param : params) finalUrl.append(param);
			return finalUrl.toString();
	}

}
