package com.ratebeer.dao;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;

import com.rateabeer.pojo.User;
import com.ratebeer.db.DB;

public class UserDao {

	private EntityManager em;

	public boolean addUser(User user) {
		em = DB.getDBFactory().createEntityManager();
		boolean added = false;
		try {
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
			added = true;
		} catch (Exception e) {
		} finally {
			em.close();
		}
		return added;
	}

	public User getUser(int id) {
		em = DB.getDBFactory().createEntityManager();
		User u = null;
		try {
			u = em.find(User.class, id);
		} catch (Exception e) {
		} finally {
			em.close();
		}
		return u;
	}

	public void updateUser(User u) {
		em = DB.getDBFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(u);
			em.getTransaction().commit();
		} catch (Exception e) {
		} finally {
			em.close();
		}
	}
	
	public void deleteUser(int id) {
		em = DB.getDBFactory().createEntityManager();
		try {
			User u = em.find(User.class, id);
			em.getTransaction().begin();
			em.remove(u);
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			em.close();
		}
	}
	
	@PreDestroy
	public void destruct()
	{
	    em.close();
	}

}
