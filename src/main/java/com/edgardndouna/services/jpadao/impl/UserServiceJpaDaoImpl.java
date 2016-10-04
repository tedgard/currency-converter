package com.edgardndouna.services.jpadao.impl;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.edgardndouna.domain.User;
import com.edgardndouna.domain.util.ToolBox;
import com.edgardndouna.services.UserService;

@Service
@Profile("prod")
public class UserServiceJpaDaoImpl implements UserService {

	Logger logger = Logger.getLogger(this.getClass().getName());
	
	private EntityManagerFactory emf;
	private EntityManager em;
	
	@PersistenceUnit
	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
		this.em  = this.emf.createEntityManager();
	}
	
	@Override
	public List<String> getListOfCountries(){
		return ToolBox.generateListOfCountries();
	}

	@Override
	public User authenticateUser(String email, String password) {
		
		User user = null;
		try {
			user = em.createQuery("from User u WHERE u.email =:email and u.password =:password", User.class)
					.setParameter("email", email)
					.setParameter("password", password).getSingleResult();
		} catch (NoResultException e) {
			user = null;
		}
		catch(Exception e){
			logger.error("Error while authenticating user (email: "+email+", password: "+password+") | "+e.getMessage());
		}
		
		return user;
	}

	@Override
	public User saveOrUpdate(User user) {
		
		User userSaved = null;
		try {
			em.getTransaction().begin();
			userSaved = em.merge(user);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			logger.error("Error while saving/updating "+user+" | "+e.getMessage());
		}
		
		return userSaved;
	}

	@Override
	public User getUserById(int id) {
		return em.find(User.class, id);
	}

	@Override
	public boolean isEmailAlreadyRegistered(String email) {
		
		User user = null;
		try {
			user = em.createQuery("from User u WHERE u.email =:email", User.class)
					.setParameter("email", email).getSingleResult();
		} catch (NoResultException e) {
			user = null;
		}
		catch(Exception e){
			logger.error("Error while checking existing user (email: "+email+") | "+e.getMessage());
		}
		
		return user != null;
	}

	@Override
	public boolean isValidEmailAddress(String email) {
		return ToolBox.checkIfValidEmail(email);
	}
	
	@Override
	public boolean isReasonableDateOfBirth(LocalDate dateOrBirth){
		return ToolBox.checkIfDateIsInTheFuture(dateOrBirth);
	}

}
