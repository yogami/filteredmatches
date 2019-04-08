package com.filteredmatches.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {

	private List<Connection> connectionPool = new ArrayList<Connection>();
	String url = "jdbc:h2:mem:";

	public ConnectionPool() {
		if (connectionPool == null || connectionPool.size() == 0) {
			try {
				connectionPool.add(DriverManager.getConnection(url));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}
	
	public Connection getConnection() {
		return connectionPool.get(0);
	}

}
