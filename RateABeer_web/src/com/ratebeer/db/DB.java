package com.ratebeer.db;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DB {

	private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("RateABeer_web");
	
	private DB() {}
	public static EntityManagerFactory getDBFactory() {
		return factory;
	}
	
}
