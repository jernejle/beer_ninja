package com.ratebeer.dao;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
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

	@PreDestroy
	public void destruct() {
		em.close();
	}
}
