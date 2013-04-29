package com.ratebeer.dao;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;

import com.rateabeer.pojo.Comment;
import com.rateabeer.pojo.Rate;
import com.ratebeer.db.DB;

public class RateDao {

	private EntityManager em;

	public boolean addRate(Rate rate) {
		em = DB.getDBFactory().createEntityManager();
		boolean added = false;
		try {
			em.getTransaction().begin();
			em.persist(rate);
			em.getTransaction().commit();
			added = true;
		} catch (Exception e) {
		} finally {
			em.close();
		}
		return added;
	}

	public Rate getRate(int id) {
		em = DB.getDBFactory().createEntityManager();
		Rate rate = null;
		try {
			rate = em.find(Rate.class, id);
			return rate;
		} catch (Exception e) {
		} finally {
			em.close();
		}
		return rate;
	}

	public void updateRate(Rate rate) {
		em = DB.getDBFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(rate);
			em.getTransaction().commit();
		} catch (Exception e) {
		} finally {
			em.close();
		}
	}
	
	public void deleteRate(int id) {
		em = DB.getDBFactory().createEntityManager();
		try {
			Rate rate = em.find(Rate.class, id);
			em.getTransaction().begin();
			em.remove(rate);
			em.getTransaction().commit();
		} catch (Exception e) {
		} finally {
			em.close();
		}
	}
	
	@PreDestroy
	public void destruct() {
		em.close();
	}
}
