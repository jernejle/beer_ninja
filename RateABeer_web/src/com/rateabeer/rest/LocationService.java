package com.rateabeer.rest;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rateabeer.pojo.Beer;
import com.rateabeer.pojo.Location;
import com.rateabeer.pojo.User;
import com.ratebeer.dao.BeerDao;
import com.ratebeer.dao.LocationDao;
import com.ratebeer.dao.UserDao;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.rateabeer.settings.*;

@Path("/location")
public class LocationService {

	LocationDao locdao = new LocationDao();
	UserDao udao = new UserDao();
	BeerDao bdao = new BeerDao();

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
	public List<Location> searchLocations(@QueryParam("param") String param) {
		if (param == null || param == "" || param.length() < 3)
			return new ArrayList<Location>();
		List<Location> locations = locdao.searchLocations(param);
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

	@GET
	@Path("/search/locations")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Location> getLocationsFromGoogle(
			@QueryParam("param") String param) {
		if (param == null || param.equals(""))
			return new ArrayList<Location>();

		List<Location> locations = null;
		try {
			locations = locdao.getLocationFromGoogle(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return locations;
	}

	@POST
	@Path("/new")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addNew(Location l) {
		if (l.getLat() == null || l.getLon() == null)
			return;
		if (l.getName() == null || l.getName().equals("")) {
			// get location name
			System.out.println("get location name");
		} else {
			try {
				User u = udao.getUser(l.getUser().getId());
				Beer b = bdao.getBeer(l.getBeer().getId());
				if (u == null || b == null)
					return;
				l.setBeer(b);
				l.setUser(u);
				locdao.addLocation(l);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
