package com.filteredmatches.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {

	String url = "jdbc:h2:mem:";

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
