package com.filteredmatches.controller;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.filteredmatches.config.AppConfig;
import com.filteredmatches.controller.FilteredMatchesController;
import com.filteredmatches.dto.FilterDTO;
import com.filteredmatches.dto.MatchDTO;
import com.filteredmatches.service.IInitialDataSetupService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class FilteredMatchesControllerTest {
	//class under test
	@Autowired
	@Qualifier("matchesController")
	private FilteredMatchesController filteredMatchesController;
	
	@Autowired
	@Qualifier("initialSetup")
	private IInitialDataSetupService initialDataSetupService;
	
	@Before
	public void setUp() throws Exception {

		initialDataSetupService.loadDataFromJsonIntoDatabase();
	}

	@Test
	public void shouldReturnMatches() throws Exception {
		ModelAndView mv = filteredMatchesController.matches(1, new ModelMap());
		assertEquals( "matches",mv.getViewName());

	}

	@Test
	public void shouldReturnMatchesForRestfulMethodCall() throws Exception {
		FilterDTO filterDTO = new FilterDTO();

		List<MatchDTO> matches = filteredMatchesController.filteredMatches(1,
				filterDTO);

		assertEquals(24, matches.size());
	}

	@After
	public void tearDown() throws Exception {
		initialDataSetupService.deleteDataFromDatabase();
	}

}
