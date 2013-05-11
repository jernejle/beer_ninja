package com.ratebeer.dao;

import java.util.List;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.Query;

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
		} catch (Exception e) {
		} finally {
			em.close();
		}
		return rate;
	}
	
	public List<Rate> getRatingsOfUser(int userId) {
		em = DB.getDBFactory().createEntityManager();
		List<Rate> rates = null;
		try {
			Query q = em.createQuery("SELECT r FROM Rate r WHERE r.user.id = " + userId);
			rates = (List<Rate>) q.getResultList();
		} catch (Exception e) {
		} finally {
			em.close();
		}
		return rates;
	}
	
	
	public List<Rate> getBeerRatings(int beerId) {
		em = DB.getDBFactory().createEntityManager();
		List<Rate> rates = null;
		try {
			Query q = em.createQuery("SELECT r FROM Rate r WHERE r.beer.id = " + beerId);
			rates = (List<Rate>) q.getResultList();
		} catch (Exception e) {
		} finally {
			em.close();
		}
		return rates;
	}
	
	public List<Rate> getBeerRatingForCategory(int beerId, int type) {
		em = DB.getDBFactory().createEntityManager();
		List<Rate> rates = null;
		try {
			Query q = em.createQuery("SELECT r FROM Rate r WHERE r.beer.id = " + beerId + " AND r.type = " + type);
			rates = (List<Rate>) q.getResultList();
		} catch (Exception e) {
			System.out.println("no objects " + beerId + " ");
		} finally {
			em.close();
		}
		return rates;
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
