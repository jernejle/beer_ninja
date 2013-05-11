package com.rateabeer.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.rateabeer.pojo.Beer;
import com.rateabeer.pojo.Rate;
import com.rateabeer.pojo.User;
import com.ratebeer.dao.BeerDao;
import com.ratebeer.dao.RateDao;
import com.ratebeer.dao.UserDao;

@Path("/rate")
public class RateService {

	RateDao rdao = new RateDao();
	UserDao udao = new UserDao();
	BeerDao bdao = new BeerDao();

	@GET
	@Path("/user/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Rate> getRatingsOfUser(@PathParam("id") int userId) {
		List<Rate> rates = rdao.getRatingsOfUser(userId);
		if (rates == null)
			rates = new ArrayList<Rate>();
		return rates;
	}

	@GET
	@Path("/beer/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Rate> getBeerRatings(@PathParam("id") int beerId) {
		List<Rate> rates = rdao.getBeerRatings(beerId);
		if (rates == null)
			rates = new ArrayList<Rate>();
		return rates;
	}

	@POST
	@Path("/new")
	@Consumes(MediaType.APPLICATION_JSON)
	public void newRate(List<Rate> rates) {
		if (rates.size() < 3)
			return;
		User u = udao.getUser(rates.get(0).getUser().getId());
		Beer b = bdao.getBeer(rates.get(0).getBeer().getId());
		if (u == null || b == null)
			return;
		for (Rate r : rates) {
			r.setUser(u);
			r.setBeer(b);
			rdao.addRate(r);
		}

		ArrayList<Rate[]> allRatings = new ArrayList<Rate[]>();

		try {
			List<Rate> aRatings = rdao.getBeerRatingForCategory(b.getId(), 1);
			List<Rate> fRatings = rdao.getBeerRatingForCategory(b.getId(), 2);
			List<Rate> acRatings = rdao.getBeerRatingForCategory(b.getId(), 3);
			
			allRatings.add(aRatings.toArray(new Rate[acRatings.size()]));
			allRatings.add(fRatings.toArray(new Rate[fRatings.size()]));
			allRatings.add(acRatings.toArray(new Rate[acRatings.size()]));

			b.updateBeerRatings(allRatings);
			bdao.updateBeer(b);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		

	}

}
