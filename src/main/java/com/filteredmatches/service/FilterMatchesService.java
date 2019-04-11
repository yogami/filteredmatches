package com.filteredmatches.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.filteredmatches.dao.IFilterData;
import com.filteredmatches.dto.FilterDTO;
import com.filteredmatches.dto.MatchDTO;
import com.filteredmatches.model.User;

@Service("matchesService")
public class FilterMatchesService implements IFilterMatchesService {

	@Autowired
	@Qualifier("filterData")
	IFilterData filterData;
	
	@Autowired
	@Qualifier("userService")
	IUserService userService;
	
	public List<MatchDTO> getMatches(Integer userId, FilterDTO filterDTO)
			throws Exception {
		User currentUser = userService.getCurrentUserById(userId);
		return filterData.retrieveMatchesForCurrentUser(currentUser, filterDTO);
	}

}
