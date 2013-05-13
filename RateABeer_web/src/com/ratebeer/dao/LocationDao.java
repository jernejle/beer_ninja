package com.ratebeer.dao;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rateabeer.pojo.Beer;
import com.rateabeer.pojo.Location;
import com.rateabeer.settings._Settings;
import com.ratebeer.db.DB;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class LocationDao {

	private EntityManager em;

	public boolean addLocation(Location loc) {
		em = DB.getDBFactory().createEntityManager();
		boolean added = false;
		try {
			em.getTransaction().begin();
			em.persist(loc);
			em.getTransaction().commit();
			added = true;
		} catch (Exception e) {
		} finally {
			em.close();
		}
		return added;
	}

	public Location getLocation(int id) {
		em = DB.getDBFactory().createEntityManager();
		Location loc = null;
		try {
			loc = em.find(Location.class, id);
		} catch (Exception e) {
			System.out.println("exception");
		} finally {
			em.close();
		}
		return loc;
	}

	public List<Location> getUserLocations(int id) {
		em = DB.getDBFactory().createEntityManager();
		List<Location> locations = null;
		try {
			Query q = em
					.createQuery("SELECT l FROM Location l WHERE l.user.id = "
							+ id);
			locations = (List<Location>) q.getResultList();
		} catch (Exception e) {
		} finally {
			em.close();
		}
		return locations;
	}

	public List<Location> getBeerLocations(int beerId) {
		em = DB.getDBFactory().createEntityManager();
		List<Location> locations = null;
		try {
			Query q = em
					.createQuery("SELECT l FROM Location l WHERE l.beer.id = "
							+ beerId);
			locations = (List<Location>) q.getResultList();
		} catch (Exception e) {
		} finally {
			em.close();
		}
		return locations;
	}

	public void updateLocation(Location loc) {
		em = DB.getDBFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(loc);
			em.getTransaction().commit();
		} catch (Exception e) {
		} finally {
			em.close();
		}
	}

	public void deleteLocation(int id) {
		em = DB.getDBFactory().createEntityManager();
		try {
			Location loc = em.find(Location.class, id);
			em.getTransaction().begin();
			em.remove(loc);
			em.getTransaction().commit();
		} catch (Exception e) {
		} finally {
			em.close();
		}
	}

	public List<Location> getLocationsAndBeers(String param) {
		em = DB.getDBFactory().createEntityManager();
		List<Location> locations = null;
		try {
			Query q = em
					.createQuery(
							"SELECT l FROM Location l JOIN FETCH l.beer WHERE l.name = :name",
							Location.class);
			q.setParameter("name", param);
			locations = (List<Location>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			em.close();
		}
		return locations;
	}

	public List<Location> searchLocations(String param) {
		em = DB.getDBFactory().createEntityManager();
		List<Location> locations = null;
		try {
			Query q = em
					.createNativeQuery(
							"SELECT l.name, l.id, l.lat, l.lon FROM Location l WHERE UPPER(l.name) LIKE ?1 GROUP BY l.name",
							Location.class);
			q.setParameter(1, "%" + param.toUpperCase() + "%");
			locations = (List<Location>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			em.close();
		}
		return locations;
	}

	public List<Location> getLocationFromGoogle(String param) {
		List<Location> locations = null;
		try {
			Client client = Client.create();
			URI uri = new URI(
					"https://maps.googleapis.com/maps/api/place/textsearch/json?query="
							+ param + "&sensor=true&key="
							+ _Settings.googleAPIKey);

			WebResource webResource = client.resource(uri.toASCIIString());

			ClientResponse response = webResource.accept("application/json")
					.get(ClientResponse.class);

			if (response.getStatus() != 200) {
				return null;
			}

			locations = new ArrayList<Location>();
			String output = response.getEntity(String.class);
			JsonParser p = new JsonParser();
			JsonObject jo = p.parse(output).getAsJsonObject();
			JsonArray jarr = jo.getAsJsonArray("results");

			for (int i = 0; i < jarr.size(); i++) {
				JsonObject jt = jarr.get(i).getAsJsonObject();
				JsonObject geo = jt.getAsJsonObject("geometry")
						.getAsJsonObject("location");

				Location l = new Location();
				l.setName(jt.get("name").getAsString());
				l.setLat(geo.get("lat").getAsString());
				l.setLon(geo.get("lng").getAsString());
				locations.add(l);
			}
		} catch (Exception e) {
			return null;
		}
		return locations;
	}

	@PreDestroy
	public void destruct() {
		em.close();
	}
}
