package com.filteredmatches.dao;

import java.sql.SQLException;

import com.filteredmatches.model.User;

public interface IUserDAO {
	
	public User retrieveCurrentuser(Integer userId) throws SQLException;

}
