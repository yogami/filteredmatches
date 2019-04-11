package com.filteredmatches.dao;

import java.sql.SQLException;

import com.filteredmatches.model.User;

public interface IUserData {
	
	public User retrieveCurrentuser(Integer userId) throws SQLException;

}
