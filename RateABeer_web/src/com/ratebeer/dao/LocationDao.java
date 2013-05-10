package com.ratebeer.dao;

import java.util.List;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.rateabeer.pojo.Beer;
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
			System.out.println("exception");
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
	
	
	public List<Location> getBeerLocations(int beerId) {
		em = DB.getDBFactory().createEntityManager();
		List<Location> locations = null;
		try {
			Query q = em.createQuery("SELECT l FROM Location l WHERE l.beer.id = " + beerId);
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
	
	public List<Location> getLocationsAndBeers(String param) {
		em = DB.getDBFactory().createEntityManager();
		List<Location> locations = null;
		try {
			Query q = em.createQuery("SELECT l FROM Location l INNER JOIN FETCH l.beer WHERE l.name = :name", Location.class);
			q.setParameter("name",param);
			locations = (List<Location>)q.getResultList();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			em.close();
		}
		return locations;
	}
	
	public List<Location> searchLocations(String param, int rowNum) {
		em = DB.getDBFactory().createEntityManager();
		List<Location> locations = null;
		try {
			String limitStr = (rowNum > 0) ? "LIMIT " + rowNum : "";
			Query q = em.createNativeQuery("SELECT l.name, l.id, l.lat, l.lon FROM Location l WHERE UPPER(l.name) LIKE ?1 " + limitStr, Location.class);
			q.setParameter(1, "%" + param.toUpperCase() + "%");
			locations = (List<Location>)q.getResultList();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			em.close();
		}
		return locations;
	}
	
	@PreDestroy
	public void destruct() {
		em.close();
	}
}
