package com.rateabeer.rest;

import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.rateabeer.pojo.Comment;
import com.rateabeer.pojo.User;

@Path("/main")
public class Test {

	@GET
	@Path("test")
	@Produces(MediaType.APPLICATION_JSON)
	public User test(String userparam) {
		User u = new User();
		u.setUsername("jernej");
		u.setEmail("email");
		u.setId(1);
		Collection<Comment> col = new ArrayList<Comment>();
		Comment com = new Comment();
		com.setComment("this is comment");
		com.setAuthor(u);
		col.add(com);
		u.setComments(col);
		return u;
	}
	
	@POST
	@Path("post")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_XML)
	public String add(User u) {
		System.out.println(u.getEmail());
		return "bla";
	}
	
}
