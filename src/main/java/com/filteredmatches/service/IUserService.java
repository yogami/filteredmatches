package com.filteredmatches.service;

import com.filteredmatches.model.User;

public interface IUserService {
	
	public User getCurrentUserById(Integer userId) throws Exception;

}
