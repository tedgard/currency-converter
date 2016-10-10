package com.edgardndouna.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.edgardndouna.domain.User;

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
			countries.sort(String::compareTo);
		}
		
		return countries;
	}
	
	
	/**
	 * Setting the context for the authenticated user
	 * @param password
	 * @param user
	 */
	public static void authenticatedUser(User user) {
		
		if(SecurityContextHolder.getContext().getAuthentication() != null)
			SecurityContextHolder.clearContext();
			
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
}
