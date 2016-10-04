package com.edgardndouna.services;

import java.util.List;

import com.edgardndouna.config.ApiRateConverterException;

public interface ApiRateService {

	public List<String> getSupportedCurrencies();
	
	public double convertRateFromTo(String baseCurrency, String targetCurrency, String dateRate, String amount) 
			throws ApiRateConverterException;
	
}
