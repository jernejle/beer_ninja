package com.ratebeer.dao;

import java.util.List;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.rateabeer.pojo.Location;
import com.ratebeer.db.DB;

public class LocationDao {

	private EntityManager em;

	public boolean addLocation(Location loc) {
		em = DB.getDBFactory().createEntityManager();
		boolean added = false;
		try {
			em.getTransaction().begin();
			em.persist(loc);
			em.getTransaction().commit();
			added = true;
		} catch (Exception e) {
		} finally {
			em.close();
		}
		return added;
	}

	public Location getLocation(int id) {
		em = DB.getDBFactory().createEntityManager();
		Location loc = null;
		try {
			loc = em.find(Location.class, id);
		} catch (Exception e) {
		} finally {
			em.close();
		}
		return loc;
	}
	
	public List<Location> getUserLocations(int id) {
		em = DB.getDBFactory().createEntityManager();
		List<Location> locations = null;
		try {
			Query q = em.createQuery("SELECT l FROM Location l WHERE l.user.id = " + id);
			locations = (List<Location>) q.getResultList();
		} catch (Exception e) {
		} finally {
			em.close();
		}
		return locations;
	}

	public void updateLocation(Location loc) {
		em = DB.getDBFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(loc);
			em.getTransaction().commit();
		} catch (Exception e) {
		} finally {
			em.close();
		}
	}
	
	public void deleteLocation(int id) {
		em = DB.getDBFactory().createEntityManager();
		try {
			Location loc = em.find(Location.class, id);
			em.getTransaction().begin();
			em.remove(loc);
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
