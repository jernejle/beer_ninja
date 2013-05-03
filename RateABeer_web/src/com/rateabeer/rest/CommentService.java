package com.rateabeer.rest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.rateabeer.pojo.Beer;
import com.rateabeer.pojo.Comment;
import com.rateabeer.pojo.User;
import com.ratebeer.dao.BeerDao;
import com.ratebeer.dao.CommentDao;
import com.ratebeer.dao.UserDao;

@Path("/comment")
public class CommentService {

	CommentDao comdao = new CommentDao();
	BeerDao bdao = new BeerDao();
	UserDao udao = new UserDao();
	
	@GET
	@Path("/beer/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getBeerComments(@PathParam("id") int id, @DefaultValue("1") @QueryParam("page") int page) {
		page = Math.abs(page);
		int limit = 10;
		int offset = (page*limit) - limit;
		List<Comment> comments = comdao.getBeerComments(id);
		if (comments == null || offset >= comments.size()) return new ArrayList<Comment>();
		
		List<Comment> withoffset = new ArrayList<Comment>();
		for (int i = offset; withoffset.size() < limit; i++ ) {
			try {
				Comment com = comments.get(i);
				withoffset.add(com);
			} catch (Exception e) {
				break;
			}
		}
		
		return withoffset;
	}
	
	@POST
	@Path("/newcomment/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addComment(@PathParam("id") int beerId, Comment c) {
		int userId = 0;
		try {
			userId = c.getAuthor().getId();
		} catch (Exception e) {
			return;
		}
		Beer beer = bdao.getBeer(beerId);
		User u = udao.getUser(userId);
		if (beer == null || u == null) return;
		java.util.Date date = new java.util.Date();
		Date dsql = new Date(date.getTime());
		c.setDate(dsql);
		c.setAuthor(u);
		c.setBeer(beer);
		comdao.addComment(c);
	}
	
	@PUT
	@Path("/edit/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateComment(@PathParam("id") int commentId, Comment c) {
		System.out.println(c.getComment());
	}
	
}
