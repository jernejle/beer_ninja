package com.rateabeer.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.rateabeer.pojo.Beer;
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
		if (locations == null)
			locations = new ArrayList<Location>();
		return locations;
	}

	@GET
	@Path("/beer/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Location> getBeerLocations(@PathParam("id") int id) {
		List<Location> locations = locdao.getBeerLocations(id);
		if (locations == null)
			locations = new ArrayList<Location>();
		return locations;
	}

	// search locations
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Location> searchLocations(@QueryParam("param") String param, @DefaultValue("0") @QueryParam("limit") int limit) {
		if (param == null || param == "" || param.length() < 3)
			return new ArrayList<Location>();
		List<Location> locations = locdao.searchLocations(param, Math.abs(limit));
		if (locations == null)
			locations = new ArrayList<Location>();
		return locations;
	}

	// get beers available on specific locations
	@GET
	@Path("/beers/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Location> getMarkedLocations(@PathParam("id") int locationId) {
		String locationName = null;
		try {
			locationName = locdao.getLocation(locationId).getName();
		} catch (Exception e) {
			System.out.println("no such location");
			return null;
		}
		List<Location> locations = locdao.getLocationsAndBeers(locationName);
		return locations;
	}
}
