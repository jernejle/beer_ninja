package com.ratebeer.dao;

import java.util.List;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.rateabeer.pojo.Comment;
import com.ratebeer.db.DB;

public class CommentDao {

	private EntityManager em;

	public boolean addComment(Comment comment) {
		em = DB.getDBFactory().createEntityManager();
		boolean added = false;
		try {
			em.getTransaction().begin();
			em.persist(comment);
			em.getTransaction().commit();
			added = true;
		} catch (Exception e) {
		} finally {
			em.close();
		}
		return added;
	}

	public Comment getComment(int id) {
		em = DB.getDBFactory().createEntityManager();
		Comment com = null;
		try {
			com = em.find(Comment.class, id);
		} catch (Exception e) {
		} finally {
			em.close();
		}
		return com;
	}

	public void updateComment(Comment com) {
		em = DB.getDBFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(com);
			em.getTransaction().commit();
		} catch (Exception e) {
		} finally {
			em.close();
		}
	}
	
	public void deleteComment(int id) {
		em = DB.getDBFactory().createEntityManager();
		try {
			Comment com = em.find(Comment.class, id);
			em.getTransaction().begin();
			em.remove(com);
			em.getTransaction().commit();
		} catch (Exception e) {
		} finally {
			em.close();
		}
	}
	
	public List<Comment> getBeerComments(int beerId) {
		em = DB.getDBFactory().createEntityManager();
		List<Comment> comments = null;
		try {
			Query q = em.createQuery("SELECT x FROM Comment x WHERE x.beer.id = " + beerId + " ORDER BY x.id DESC");
			comments = (List<Comment>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			em.close();
		}
		return comments;
	}
	
	@PreDestroy
	public void destruct() {
		em.close();
	}
}
