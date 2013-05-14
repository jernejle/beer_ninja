package com.ratebeer.dao;

import java.util.List;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.rateabeer.pojo.Event;
import com.ratebeer.db.DB;

public class EventDao {

	private EntityManager em;

	public boolean addEvent(Event event) {
		em = DB.getDBFactory().createEntityManager();
		boolean added = false;
		try {
			em.getTransaction().begin();
			em.persist(event);
			em.getTransaction().commit();
			added = true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			em.close();
		}
		return added;
	}

	public Event getEvent(int id) {
		em = DB.getDBFactory().createEntityManager();
		Event event = null;
		try {
			event = em.find(Event.class, id);
		} catch (Exception e) {
		} finally {
			em.close();
		}
		return event;
	}

	public void updateEvent(Event event) {
		em = DB.getDBFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(event);
			em.getTransaction().commit();
		} catch (Exception e) {
		} finally {
			em.close();
		}
	}
	
	public void deleteEvent(int id) {
		em = DB.getDBFactory().createEntityManager();
		try {
			Event event = em.find(Event.class, id);
			em.getTransaction().begin();
			em.remove(event);
			em.getTransaction().commit();
		} catch (Exception e) {
		} finally {
			em.close();
		}
	}
	
	public List<Event> getInvitedEvents(int userId) {
		em = DB.getDBFactory().createEntityManager();
		List<Event> events = null;
		try {
			Query q = em.createQuery("SELECT e FROM Event e INNER JOIN e.invited i where i.id = " + userId);
			events = (List<Event>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			em.close();
		}
		return events;
	}
	
	public List<Event> getGoingEvents(int userId) {
		em = DB.getDBFactory().createEntityManager();
		List<Event> events = null;
		try {
			Query q = em.createQuery("SELECT e FROM Event e INNER JOIN e.going i where i.id = " + userId);
			events = (List<Event>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			em.close();
		}
		return events;
	}
	
	@PreDestroy
	public void destruct() {
		em.close();
	}
	
}
