package com.filteredmatches.service;

import com.filteredmatches.data.FilterData;
import com.filteredmatches.model.User;

public class UserService {

	FilterData filterData = new FilterData();

	public User getCurrentUserById(Integer userId) {

		return filterData.retrieveCurrentuser(userId);
	}

}
