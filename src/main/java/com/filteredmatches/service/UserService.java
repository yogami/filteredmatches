package com.filteredmatches.service;

import com.filteredmatches.data.UserData;
import com.filteredmatches.model.User;

public class UserService {

	
	//TODO: annotate this
	UserData userData = new UserData();

	public User getCurrentUserById(Integer userId) throws Exception {

		return userData.retrieveCurrentuser(userId);
	}

}
