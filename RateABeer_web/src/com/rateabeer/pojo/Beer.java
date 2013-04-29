package com.rateabeer.pojo;

import java.io.Serializable;
import java.sql.Date;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@XmlRootElement
public class Beer implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	@NotNull
	private String name;
	@NotNull
	private String description;
	@NotNull
	private Date added;
	@NotNull
	private String pic;
	
	private int rateAroma; // type1
	private int rateAlcoholContent; // type2
	private int rateFlavour; // type3

	@OneToMany(mappedBy = "beer")
	private Collection<Comment> comments;

	@OneToMany(mappedBy = "beer")
	private Collection<Rate> ratings;

	@OneToMany(mappedBy = "beer")
	private Collection<Location> locations;

	@ManyToMany
	@JoinTable(joinColumns = @JoinColumn(name = "BEER_ID", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "id"))
	private Collection<User> users;

	@XmlTransient
	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getAdded() {
		return added;
	}

	public void setAdded(Date added) {
		this.added = added;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
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
	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	public int getRateAroma() {
		return rateAroma;
	}

	public void setRateAroma(int rateAroma) {
		this.rateAroma = rateAroma;
	}

	public int getRateAlcoholContent() {
		return rateAlcoholContent;
	}

	public void setRateAlcoholContent(int rateAlcoholContent) {
		this.rateAlcoholContent = rateAlcoholContent;
	}

	public int getRateFlavour() {
		return rateFlavour;
	}

	public void setRateFlavour(int rateFlavour) {
		this.rateFlavour = rateFlavour;
	}

}
