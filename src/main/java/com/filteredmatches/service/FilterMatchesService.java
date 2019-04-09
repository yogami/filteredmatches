package com.filteredmatches.service;

import java.util.LinkedHashMap;
import java.util.List;

import com.filteredmatches.data.FilterData;
import com.filteredmatches.dto.FilterDTO;
import com.filteredmatches.model.User;

public class FilterMatchesService {
	FilterData filterData = new FilterData();
	public List<User> getMatchesForCurrentUser(User currentUser, FilterDTO filterDTO) throws Exception{
		return filterData.retrieveMatchesForCurrentUser(currentUser, filterDTO);
	}

}
