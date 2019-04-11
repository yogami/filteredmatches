package com.filteredmatches.dao;

import java.sql.Connection;


public abstract class BaseData {

	protected Connection con;

	public BaseData() {
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
