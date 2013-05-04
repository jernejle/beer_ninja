package com.ratebeer.dao;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.rateabeer.pojo.User;
import com.ratebeer.db.DB;

public class UserDao {

	private EntityManager em;

	public User addUser(User user) {
		em = DB.getDBFactory().createEntityManager();

		try {
			em.getTransaction().begin();
			em.persist(user);
			em.flush();
			em.getTransaction().commit();
		} catch (Exception e) {
			user = null;
		} finally {
			em.close();
		}

		return user;
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
	
	public User loginUser(String userName, String password) {
		User user = null;
		
		em = DB.getDBFactory().createEntityManager();
		try {
			Query q = em.createQuery("SELECT u FROM User u WHERE u.username=:userName AND u.password=:password");
			q.setParameter("userName", userName);
			q.setParameter("password", password);
			em.getTransaction().begin();
			user = (User) q.getSingleResult();
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			em.close();
		}
		
		return user;
	}
	
	@PreDestroy
	public void destruct()
	{
	    em.close();
	}

}
