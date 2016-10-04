package com.edgardndouna.domain.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.validator.routines.EmailValidator;

public class ToolBox {

	private static List<String> countries;
	
	/**
	 * Check the format of email using apache tomcat according to RFC 822 standards.
	 * @param email
	 * @return boolean
	 */
	public static boolean checkIfValidEmail(String email){
		
		boolean result = true;
		EmailValidator validator = EmailValidator.getInstance();
		try {
			if (!validator.isValid(email))
				result = false;
		} catch (Exception ex) {
			result = false;
		}
	   return result;
	}
	
	/**
	 * Check value date if future or not
	 * @param date
	 * @return boolean
	 */
	public static boolean checkIfDateIsInTheFuture(LocalDate date){
		if(LocalDate.now().isAfter(date)) return true;
		return false;
	}
	
	
	/**
	 * Generate list of countries with default Locale
	 * @return List<String>
	 */
	public static List<String> generateListOfCountries(){
		
		if(countries == null){
			String[] locales = Locale.getISOCountries();
			countries = new ArrayList<>();
			for (String countryCode : locales) {
				Locale obj = new Locale("", countryCode);
				countries.add(obj.getDisplayCountry());	
			}
		}
		
		return countries;
	}
}
