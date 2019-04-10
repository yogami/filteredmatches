package com.filteredmatches.service;

import java.util.List;

import com.filteredmatches.dto.FilterDTO;
import com.filteredmatches.dto.MatchDTO;

public interface IFilterMatchesService {
	public List<MatchDTO> getMatches(Integer userId, FilterDTO filterDTO) throws Exception;

}
