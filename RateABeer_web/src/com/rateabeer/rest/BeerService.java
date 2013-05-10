package com.rateabeer.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.rateabeer.pojo.Beer;
import com.rateabeer.pojo.User;
import com.ratebeer.dao.BeerDao;
import com.ratebeer.dao.LocationDao;
import com.ratebeer.dao.UserDao;

@Path("/beer")
public class BeerService {

	BeerDao bdao = new BeerDao();
	LocationDao ldao = new LocationDao();
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Beer getBeer(@PathParam("id") int id) {
		Beer beer = bdao.getBeer(id);
		if (beer == null) beer = new Beer();
		return beer;
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Beer> searchBeers(@QueryParam("param") String param) {
		if (param == null || param == "" || param.length() < 3) return new ArrayList<Beer>();
		List<Beer> beers = bdao.searchBeers(param);
		if (beers == null) beers = new ArrayList<Beer>();
		return beers;
	}
	
	@GET
	@Path("/last")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Beer> getRandomBeers() {
		int num = 15;
		List<Beer> beers = bdao.getLast(num);
		if (beers == null) beers = new ArrayList<Beer>();
		return beers;
	}
	
	@GET
	@Path("/location/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Beer> getBeersOnLocation(@PathParam("id") int locationId) {
		String locationName = ldao.getLocation(locationId).getName();
		List<Beer> beers = bdao.getBeersByLocation(locationName);
		return beers;
	}
	
}
