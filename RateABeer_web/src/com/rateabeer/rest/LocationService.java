package com.rateabeer.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.rateabeer.pojo.Location;
import com.ratebeer.dao.LocationDao;

@Path("/location")
public class LocationService {

	LocationDao locdao = new LocationDao();
	
	@GET
	@Path("/user/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Location> getUserLocations(@PathParam("id") int id) {
		List<Location> locations = locdao.getUserLocations(id);
		if (locations == null) locations = new ArrayList<Location>();
		return locations;
	}
	
	@GET
	@Path("/beer/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Location> getBeerLocations(@PathParam("id") int id) {
		List<Location> locations = locdao.getBeerLocations(id);
		if (locations == null) locations = new ArrayList<Location>();
		return locations;
	}
	
}
