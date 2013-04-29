package com.rateabeer.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.rateabeer.pojo.Rate;
import com.ratebeer.dao.RateDao;

@Path("/rate")
public class RateService {

	RateDao rdao = new RateDao();
	
	@GET
	@Path("/user/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Rate> getRatingsOfUser(@PathParam("id") int userId) {
		List<Rate> rates = rdao.getRatingsOfUser(userId);
		if (rates == null) rates = new ArrayList<Rate>();
		return rates;
	}
	
	@GET
	@Path("/beer/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Rate> getBeerRatings(@PathParam("id") int beerId) {
		List<Rate> rates = rdao.getBeerRatings(beerId);
		if (rates == null) rates = new ArrayList<Rate>();
		return rates;
	}
	
}
