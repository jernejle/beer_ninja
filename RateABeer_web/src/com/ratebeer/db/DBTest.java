package com.ratebeer.db;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.rateabeer.pojo.Beer;
import com.rateabeer.pojo.Comment;
import com.rateabeer.pojo.Event;
import com.rateabeer.pojo.Friend;
import com.rateabeer.pojo.Location;
import com.rateabeer.pojo.Rate;
import com.rateabeer.pojo.User;
import com.ratebeer.dao.BeerDao;
import com.ratebeer.dao.CommentDao;
import com.ratebeer.dao.EventDao;
import com.ratebeer.dao.FriendDao;
import com.ratebeer.dao.LocationDao;
import com.ratebeer.dao.RateDao;
import com.ratebeer.dao.UserDao;

public class DBTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		UserDao userdao = new UserDao();
		BeerDao beerdao = new BeerDao();
		CommentDao comdao = new CommentDao();
		EventDao eventdao = new EventDao();
		FriendDao frienddao = new FriendDao();
		LocationDao locdao = new LocationDao();
		RateDao ratedao = new RateDao();
		
		User u1 = new User();
		u1.setEmail("u1@email");
		u1.setLastName("lastname u1");
		u1.setName("name u1");
		u1.setUsername("u1");
		userdao.addUser(u1);
		//u1 = userdao.getUser(1);
		
		User u2 = new User();
		u2.setEmail("u2@email");
		u2.setLastName("lastname u2");
		u2.setName("name u2");
		u2.setUsername("u2");
		userdao.addUser(u2);
		//u2 = userdao.getUser(2);
		
		java.util.Date date = new java.util.Date();
		Date dsql = new Date(date.getTime());
		
		Beer b1 = new Beer();
		b1.setAdded(dsql);
		b1.setDescription("description b1");
		b1.setName("name b1");
		b1.setPic("url b1");
		beerdao.addBeer(b1);
		//b1 = beerdao.getBeer(3);
		
		Beer b2 = new Beer();
		b2.setAdded(dsql);
		b2.setDescription("description b2");
		b2.setName("name b2");
		b2.setPic("url b2");
		beerdao.addBeer(b2);
		//b2 = beerdao.getBeer(4);
		
		Comment com1 = new Comment();
		com1.setAuthor(u1);
		com1.setBeer(b1);
		com1.setComment("comment com1");
		com1.setDate(dsql);
		comdao.addComment(com1);
		//com1 = comdao.getComment(501);

//		
//		List<Comment> comments = comdao.getBeerComments(51);
//		for (Comment c : comments) {
//			System.out.println(c.getComment());
//		}
//				
		Comment com2 = new Comment();
		com2.setAuthor(u1);
		com2.setBeer(b1);
		com2.setComment("comment u1");
		com2.setDate(dsql);
		comdao.addComment(com2);
		
		Event event1 = new Event();
		event1.setDate(dsql);
		event1.setDescription("description event 1");
		event1.setLat("598654.548");
		event1.setLon("598654.548");
		event1.setPublicEvent(1);
		event1.setUser(u2);
		eventdao.addEvent(event1);
		
		List<User> u_list = new ArrayList<User>();
		u_list.add(u1);
		u_list.add(u2);
		
		event1.setGoing(u_list);
		event1.setInvited(u_list);
		
		eventdao.updateEvent(event1);
		
		Event event2 = new Event();
		event2.setDate(dsql);
		event2.setDescription("description event 2");
		event2.setLat("598654.548");
		event2.setLon("598654.548");
		event2.setPublicEvent(1);
		event2.setUser(u2);
		eventdao.addEvent(event2);
		
		Friend f = new Friend();
		f.setConfirmed(1);
		f.setSender(u1);
		f.setReceiver(u2);
		frienddao.addFriend(f);
	
		Location loc1 = new Location();
		loc1.setLat("43655.557");
		loc1.setLon("43655.557");
		loc1.setName("location name");
		loc1.setBeer(b1);
		loc1.setUser(u1);
		locdao.addLocation(loc1);
	
		Location loc2 = new Location();
		loc2.setLat("43655.557");
		loc2.setLon("43655.557");
		loc2.setName("location name");
		loc2.setBeer(b2);
		loc2.setUser(u2);
		locdao.addLocation(loc2);
	
		Rate r1 = new Rate();
		r1.setBeer(b1);
		r1.setOcena(5);
		r1.setType(1);
		r1.setUser(u1);
		ratedao.addRate(r1);
	
		Rate r2 = new Rate();
		r2.setBeer(b2);
		r2.setOcena(5);
		r2.setType(1);
		r2.setUser(u2);
		ratedao.addRate(r2);
		
	}
}
