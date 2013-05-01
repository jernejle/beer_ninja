package com.ratebeer.dao;

import java.util.List;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.rateabeer.pojo.Beer;
import com.ratebeer.db.DB;

public class BeerDao {

	private EntityManager em;

	public boolean addBeer(Beer beer) {
		em = DB.getDBFactory().createEntityManager();
		boolean added = false;
		try {
			em.getTransaction().begin();
			em.persist(beer);
			em.getTransaction().commit();
			added = true;
		} catch (Exception e) {
		} finally {
			em.close();
		}
		return added;
	}

	public Beer getBeer(int id) {
		em = DB.getDBFactory().createEntityManager();
		Beer beer = null;
		try {
			beer = em.find(Beer.class, id);
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			em.close();
		}
		return beer;
	}

	public void updateBeer(Beer beer) {
		em = DB.getDBFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(beer);
			em.getTransaction().commit();
		} catch (Exception e) {
		} finally {
			em.close();
		}
	}

	public void deleteBeer(int id) {
		em = DB.getDBFactory().createEntityManager();
		try {
			Beer beer = em.find(Beer.class, id);
			em.getTransaction().begin();
			em.remove(beer);
			em.getTransaction().commit();
		} catch (Exception e) {
		} finally {
			em.close();
		}
	}
	
	public List<Beer> searchBeers(String param) {
		em = DB.getDBFactory().createEntityManager();
		List<Beer> beers = null;
		try {
			Query q = em.createQuery("SELECT b FROM Beer b WHERE UPPER(b.name) LIKE :param ORDER BY b.name");
			q.setParameter("param", "%" + param.toUpperCase() + "%");
			beers = (List<Beer>)q.getResultList();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			em.close();
		}
		return beers;
	}
	
	public List<Beer> getLast(int num) {
		em = DB.getDBFactory().createEntityManager();
		List<Beer> beers = null;
		try {
			Query q = em.createQuery("SELECT b FROM Beer b ORDER BY b.id DESC").setMaxResults(num);
			beers = (List<Beer>)q.getResultList();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			em.close();
		}
		return beers;
	}

	@PreDestroy
	public void destruct() {
		em.close();
	}
}
