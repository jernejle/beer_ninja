package com.ratebeer.dao;

import java.util.List;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.rateabeer.pojo.Friend;
import com.ratebeer.db.DB;

public class FriendDao {

	private EntityManager em;

	public boolean addFriend(Friend f) {
		em = DB.getDBFactory().createEntityManager();
		boolean added = false;
		try {
			em.getTransaction().begin();
			em.persist(f);
			em.getTransaction().commit();
			added = true;
		} catch (Exception e) {
		} finally {
			em.close();
		}
		return added;
	}

	public List<Friend> getUserFriends(int userId) {
		em = DB.getDBFactory().createEntityManager();
		List<Friend> friends_list = null;
		try {
			Query q = em.createQuery("SELECT x FROM FRIEND WHERE x.sender = " + userId + " OR x.receiver = " + userId);
			friends_list = (List<Friend>) q.getResultList();
		} catch (Exception e) {
		} finally {
			em.close();
		}
		return friends_list;
	}

	public void updateFriend(Friend f) {
		em = DB.getDBFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(f);
			em.getTransaction().commit();
		} catch (Exception e) {
		} finally {
			em.close();
		}
	}
	
	public void deleteFriend(int id) {
		em = DB.getDBFactory().createEntityManager();
		try {
			FriendDao f = em.find(FriendDao.class, id);
			em.getTransaction().begin();
			em.remove(f);
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
