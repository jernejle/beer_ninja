package com.rateabeer.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import com.rateabeer.pojo.User;
import com.ratebeer.dao.UserDao;
import com.sun.jersey.api.json.JSONWithPadding;
import com.sun.jersey.json.impl.provider.entity.JSONObjectProvider;

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
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({"application/javascript"})
	public JSONWithPadding login(User userData, @QueryParam("callback")String callback) {
		User user = new User();

		System.out.println("Podan username: " + userData);
//		System.out.println("Podan password: " + password);
		
//		user = udao.loginUser(userName, password);
		
		return new JSONWithPadding(user);
	}
	
	
//	@POST
//	@Path("/login")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public User loginJSON(User userData) {
//		User user = new User();
//
//		System.out.println("Podano: " + userData);
//		
////		user = udao.loginUser(userName, password);
//		
//		return user;
//	}
}
