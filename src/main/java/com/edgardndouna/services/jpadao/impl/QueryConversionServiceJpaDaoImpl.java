package com.edgardndouna.services.jpadao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.edgardndouna.domain.QueryConversion;
import com.edgardndouna.services.QueryConversionService;

@Service
@Profile("prod")
public class QueryConversionServiceJpaDaoImpl implements QueryConversionService{

	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	private EntityManagerFactory emf;
	private EntityManager em;
	
	@PersistenceUnit
	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
		this.em = this.emf.createEntityManager();
	}

	@Override
	public List<QueryConversion> loadLastTenQueriesPerformedByUser(int id) {
		return em.createQuery("SELECT q from QueryConversion q inner join q.user u WHERE u.id =:userId ORDER BY q.id DESC", 
				QueryConversion.class)
				.setParameter("userId", id)
				.setMaxResults(10)
				.getResultList();
	}

	@Override
	public void saveQuery(QueryConversion query) {
		try {
			em.getTransaction().begin();
			em.merge(query);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			logger.error("Error while saving/updating "+query+" | "+e.getMessage());
		}
	}
	
}
