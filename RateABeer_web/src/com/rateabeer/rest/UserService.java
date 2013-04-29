package com.rateabeer.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.rateabeer.pojo.User;
import com.ratebeer.dao.UserDao;

@Path("/user")
public class UserService {

	UserDao udao = new UserDao();
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("id") int id) {
		User u = udao.getUser(id);
		if (u == null) u = new User();
		return u;
	}
}
