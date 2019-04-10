package com.filteredmatches.data;

import java.sql.SQLException;

import com.filteredmatches.model.User;

public interface IUserData {
	
	public User retrieveCurrentuser(Integer userId) throws SQLException;

}
