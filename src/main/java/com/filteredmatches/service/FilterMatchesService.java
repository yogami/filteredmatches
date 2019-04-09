package com.filteredmatches.service;

import java.util.List;

import com.filteredmatches.data.FilterData;
import com.filteredmatches.dto.FilterDTO;
import com.filteredmatches.dto.MatchDTO;
import com.filteredmatches.model.User;

public class FilterMatchesService {
	//TODO: annotate this
	FilterData filterData = new FilterData();
	//TODO:annotate this
	UserService userService = new UserService();
	public List<MatchDTO> getMatches(Integer userId, FilterDTO filterDTO) throws Exception{
		User currentUser = userService.getCurrentUserById(userId);
		return filterData.retrieveMatchesForCurrentUser(currentUser, filterDTO);
	}

}
