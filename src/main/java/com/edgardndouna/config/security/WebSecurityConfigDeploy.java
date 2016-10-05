package com.edgardndouna.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Profile("deploy")
public class WebSecurityConfigDeploy extends WebSecurityConfigurerAdapter {
   
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
    }
	
}
