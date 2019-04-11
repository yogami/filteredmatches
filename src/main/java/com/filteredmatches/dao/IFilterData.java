package com.filteredmatches.dao;

import java.util.List;

import com.filteredmatches.dto.FilterDTO;
import com.filteredmatches.dto.MatchDTO;
import com.filteredmatches.model.User;

public interface IFilterData {

	public List<MatchDTO> retrieveMatchesForCurrentUser(User currentUser,
			FilterDTO filterDTO) throws Exception;
}
