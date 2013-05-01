package com.rateabeer.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import com.rateabeer.pojo.Comment;
import com.ratebeer.dao.CommentDao;

@Path("/comment")
public class CommentService {

	CommentDao comdao = new CommentDao();
	
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
	
}
