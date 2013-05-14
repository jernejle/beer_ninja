package com.rateabeer.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.rateabeer.pojo.Beer;
import com.rateabeer.pojo.User;
import com.ratebeer.dao.UserDao;
import com.sun.jersey.api.json.JSONWithPadding;

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
	
	@GET
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers() {
		List<User> users = udao.getUsers();
		if (users == null) users = new ArrayList<User>();
		return users;
	}
	
	// WS for jsonp request
	@GET
	@Path("/login/{username}/{password}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({"application/javascript"})
	public JSONWithPadding login(@PathParam("username") String username, @PathParam("username") String password, @QueryParam("callback")String callback) {
		User user = null;

		//u1 : u1
		user = udao.loginUser(username, password);
		
		if (user == null) {
			user = new User();
		}
		
		return new JSONWithPadding(user, callback);
	}
	
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User loginJSON(User userData) {
		System.out.println("Podano: " + userData);
		System.out.println("Username: " + userData.getUsername());
		System.out.println("Password: " + userData.getPassword());
		
		User user = udao.loginUser(userData.getUsername(), userData.getPassword());

		if (user == null)
			user = new User();
		
		return user;
	}
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User addUser(User userData) {
		System.out.println("Podano: " + userData);
		System.out.println("Username: " + userData.getUsername());
		System.out.println("Password: " + userData.getPassword());
		
		User user = udao.addUser(userData);

		if (user == null)
			user = new User();
		
		System.out.println("Generiran ID po registraciji: " + user.getId());
		
		return user;
	}
}
