package com.filteredmatches.dao;

import java.util.List;

import com.filteredmatches.model.User;

public interface IReadSourceDAO {
	
	public List<User> getUserListFromSpecifiedSource() throws Exception;

}
