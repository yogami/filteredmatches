package com.filteredmatches.dao;

import java.sql.Connection;


public abstract class BaseDAO {

	protected Connection con;

	public BaseDAO() {
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
