package com.filteredmatches.data;

import java.util.List;

import com.filteredmatches.model.User;

public interface IReadSourceData {
	
	public List<User> getUserListFromSpecifiedSource() throws Exception;

}
