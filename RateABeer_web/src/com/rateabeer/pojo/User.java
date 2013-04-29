package com.rateabeer.pojo;

import java.util.Collection;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@XmlRootElement
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private int id;
	
	@NotNull
	private String name;
	@NotNull
	private String lastName;
	@NotNull
	private String username;
	private String password;
	@NotNull
	private String email;
	private int fbid;
	
	@OneToMany(mappedBy="author")
	private Collection<Comment> comments;
	
	@OneToMany(mappedBy="user")
	private Collection<Rate> ratings;
	
	@OneToMany(mappedBy="user")
	private Collection<Location> locations;
	
	@ManyToMany(mappedBy="users")
	private Collection<Beer> favouriteBeers;
	
	@OneToMany(mappedBy="user")
	private Collection<Event> events;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getFbid() {
		return fbid;
	}

	public void setFbid(int fbid) {
		this.fbid = fbid;
	}

	@XmlTransient
	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}

	@XmlTransient
	public Collection<Rate> getRatings() {
		return ratings;
	}

	public void setRatings(Collection<Rate> ratings) {
		this.ratings = ratings;
	}

	@XmlTransient
	public Collection<Location> getLocations() {
		return locations;
	}

	public void setLocations(Collection<Location> locations) {
		this.locations = locations;
	}

	@XmlTransient
	public Collection<Beer> getFavouriteBeers() {
		return favouriteBeers;
	}

	public void setFavouriteBeers(Collection<Beer> favouriteBeers) {
		this.favouriteBeers = favouriteBeers;
	}

	@XmlTransient
	public Collection<Event> getEvents() {
		return events;
	}

	public void setEvents(Collection<Event> events) {
		this.events = events;
	}
	

}
