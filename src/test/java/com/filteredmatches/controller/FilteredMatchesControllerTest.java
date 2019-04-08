package com.filteredmatches.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.filteredmatches.model.User;

public class FilteredMatchesControllerTest {

	@Test
	public void shouldReturnMatches() {
		FilteredMatchesController filteredMatchesController = new FilteredMatchesController();
		ModelAndView mv = filteredMatchesController.matches(1, new ModelMap());
		assertEquals(mv.getViewName(), "matches");
		ModelMap modelMap = mv.getModelMap();
		List<User> matches = (List<User>) modelMap.get("users");
		assertNotNull(matches);
		assertEquals(24, matches.size());
	}

}
