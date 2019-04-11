package com.filteredmatches.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {

	//TODO: read this from a property file somewhere
	String url = "jdbc:h2:mem:";

	//TODO: create a cache like list  to actually call it a connectionpool
	private static final ConnectionPool CONNECTION_POOL = new ConnectionPool();
	
	private Connection connection = null;

	private ConnectionPool() {

		try {
			connection = DriverManager.getConnection(url);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static ConnectionPool getInstance() {
		return CONNECTION_POOL;
	}

	public Connection getConnection() {
		return connection;
	}

}
