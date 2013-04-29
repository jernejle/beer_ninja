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
	@Path("/invited/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Event> getInvitedEvents(@PathParam("id") int id) {
		List<Event> events = eventdao.getInvitedEvents(id);
		if (events == null) events = new ArrayList<Event>();
		return events;
	}
}
