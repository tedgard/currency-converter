package com.edgardndouna.services;

import config.ApiRateConverterException;

public interface ApiRateService {

	public double convertRateFromTo(String baseCurrency, String targetCurrency, String dateRate, double amount) 
			throws ApiRateConverterException;
	
}
