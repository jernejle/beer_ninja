package com.rateabeer.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.rateabeer.pojo.Event;
import com.ratebeer.dao.EventDao;

@Path("/event")
public class EventService {

	EventDao eventdao = new EventDao();
	
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
	
}
