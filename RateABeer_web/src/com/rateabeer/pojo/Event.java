package com.rateabeer.pojo;

import java.sql.Date;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
@XmlRootElement
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	@NotNull
	private Date date;
	@NotNull
	private String description;
	@NotNull
	private String lat;
	@NotNull
	private String lon;
	@NotNull
	private int publicEvent;

	@ManyToOne
	@NotNull
	private User user;

	@OneToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "EVENT_GOING", joinColumns = { @JoinColumn(name = "EVENT_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "id") })
	private Collection<User> going;

	@OneToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "EVENT_INVITED", joinColumns = { @JoinColumn(name = "EVENT_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "id") })
	private Collection<User> invited;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlJavaTypeAdapter(SqlDateAdapter.class)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@XmlTransient
	public Collection<User> getGoing() {
		return going;
	}

	public void setGoing(Collection<User> going) {
		this.going = going;
	}

	@XmlTransient
	public Collection<User> getInvited() {
		return invited;
	}

	public void setInvited(Collection<User> invited) {
		this.invited = invited;
	}

	public int getPublicEvent() {
		return publicEvent;
	}

	public void setPublicEvent(int publicEvent) {
		this.publicEvent = publicEvent;
	}
	
}
