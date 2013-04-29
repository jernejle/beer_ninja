package com.rateabeer.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.rateabeer.pojo.Beer;
import com.rateabeer.pojo.User;
import com.ratebeer.dao.BeerDao;
import com.ratebeer.dao.UserDao;

@Path("/beer")
public class BeerService {

	BeerDao bdao = new BeerDao();
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Beer getBeer(@PathParam("id") int id) {
		Beer beer = bdao.getBeer(id);
		if (beer == null) beer = new Beer();
		return beer;
	}
	
}
