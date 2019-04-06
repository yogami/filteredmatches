package com.filteredmatches.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

public class FilteredMatchesControllerTest {

	@Test
	public void shouldReturnMatches() {
		FilteredMatchesController filteredMatchesController = new FilteredMatchesController();
		ModelAndView mv = filteredMatchesController.matches("john");
		assertEquals(mv.getViewName(), "matches");
	}

}
