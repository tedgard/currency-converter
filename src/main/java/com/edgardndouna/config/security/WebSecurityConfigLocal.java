package com.edgardndouna.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Profile("local")
public class WebSecurityConfigLocal extends WebSecurityConfigurerAdapter {
   
	@Override
    protected void configure(HttpSecurity http) throws Exception {
       
		http
            .authorizeRequests()
                .antMatchers("/", "/register", "/css/**", "/img/**", "/webjars/**", 
                			 "/authenticateUser", "/registerUser", "/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and()   
                
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                
            .logout()
                .permitAll();
		
		//In order to make '/h2-console' work locally
		http.csrf().disable();
		http.headers().frameOptions().disable();
    }
	
}
