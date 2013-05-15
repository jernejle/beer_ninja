package com.rateabeer.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.rateabeer.pojo.Event;
import com.rateabeer.pojo.User;
import com.ratebeer.dao.EventDao;
import com.ratebeer.dao.UserDao;

@Path("/event")
public class EventService {

	EventDao eventdao = new EventDao();
	
	@GET
	@Path("/events")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Event> getAllEvents() {
		List<Event> events = eventdao.getAllEvents();
		if (events == null) events = new ArrayList<Event>();
		
		for (Event e : events) {
			System.out.println("Invited: " + e.getInvited());
			
		}
		
		return events;
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Event getEvent(@PathParam("id") int id) {
		Event e = eventdao.getEvent(id);
		if (e == null) e = new Event();
		return e;
	}
	
	@GET
	@Path("/invited/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Event> getInvitedEvents(@PathParam("id") int userId) {
		List<Event> events = eventdao.getInvitedEvents(userId);
		if (events == null) events = new ArrayList<Event>();
		return events;
	}
	
	@GET
	@Path("/going/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Event> getGoingEvents(@PathParam("id") int userId) {
		List<Event> events = eventdao.getGoingEvents(userId);
		if (events == null) events = new ArrayList<Event>();
		return events;
	}
	
	@POST
	@Path("/create/{id}/invited")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createEventInvited(@PathParam("id") int idEvent, List<User> invited) {
		
		System.out.println("Dodajanje povabljenih za dogodek id: " + idEvent);
		System.out.println("Povabljeni: " + invited.size());
		
		boolean result = false;
		try {
			Event event = eventdao.getEvent(idEvent);
			event.getInvited().addAll(invited);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "{'result': '"+result+"'}";
	}
	
	@POST
	@Path("/create/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createEvent(Event e) {
		System.out.println("Invited: " + e.getInvited());
		for (User us : e.getInvited()) {
			System.out.println("Invited: " + us.getId());
		}
		
		System.out.println("Sprejet Event: " + e);
		System.out.println("User: " + e.getUser().getId());
		User user = e.getUser();
		
		UserDao ud = new UserDao();
		user = ud.getUser(user.getId());
		System.out.println("Pridobljen user: " + user);
		e.setUser(user);
		System.out.println("Public event: " + e.getPublicEvent());
		Event event = new Event();
		event.setUser(user);
		
		System.out.println(e.getDate());
		System.out.println("Pridobljen user ID: " + user.getId());
		System.out.println("Is public event: " + e.getPublicEvent());
		System.out.println("Event description: " + e.getDescription());
		System.out.println("Lon: " + e.getLon());
		System.out.println("Lat: " + e.getLat());
		event = new Event();
		event.setUser(user);
		event.setDate(new java.sql.Date(new Date().getTime()));
		event.setPublicEvent(e.getPublicEvent());
		event.setDescription(e.getDescription());
		event.setLat(e.getLat());
		event.setLon(e.getLon());
		Event savedEvent = eventdao.addEvent(event);
		
		boolean result = (savedEvent == null) ? false : true;
		int idEvent = (savedEvent == null) ? -1 : savedEvent.getId();
		
		return "{'result': '"+result+"', 'idEvent' : '"+idEvent+"'}";
	}
	
}
