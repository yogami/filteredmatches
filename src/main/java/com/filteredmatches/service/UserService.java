package com.filteredmatches.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.filteredmatches.dao.IUserDAO;
import com.filteredmatches.model.User;

@Service("userService")
public class UserService implements IUserService {

	@Autowired
	@Qualifier("userData")
	IUserDAO userData;

	public User getCurrentUserById(Integer userId) throws Exception {

		return userData.retrieveCurrentuser(userId);
	}

}
