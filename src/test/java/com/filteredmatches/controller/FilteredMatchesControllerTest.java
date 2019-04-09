package com.filteredmatches.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.filteredmatches.dto.FilterDTO;
import com.filteredmatches.model.User;
import com.filteredmatches.service.InitialDataSetupService;

public class FilteredMatchesControllerTest {
	FilteredMatchesController filteredMatchesController = new FilteredMatchesController();
	InitialDataSetupService initialDataSetupService = new InitialDataSetupService();
	@Before
	public void setUp() throws Exception{
		
		initialDataSetupService.loadDataFromJsonIntoDatabase();
	}

	@Test
	public void shouldReturnMatches() throws Exception {
		ModelAndView mv = filteredMatchesController.matches(1, new ModelMap());
		assertEquals(mv.getViewName(), "matches");
		ModelMap modelMap = mv.getModelMap();
		List<User> matches = (List<User>) modelMap.get("users");
		assertNotNull(matches);
		assertEquals(24, matches.size());
	}
	
	@Test
	public void shouldReturnMatchesForRestfulMethodCall() throws Exception {
		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setHasPhoto("no");
		String jsonString = filteredMatchesController.filteredMatches(1, filterDTO);
	
	    assertEquals(3,StringUtils.countOccurrencesOf(jsonString, "display_name"));
	}
	
	
	@After
	public void tearDown() throws Exception{
		initialDataSetupService.deleteDataFromDatabase();
	}

}
