package com.filteredmatches.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.filteredmatches.dto.FilterDTO;
import com.filteredmatches.dto.MatchDTO;
import com.filteredmatches.service.IFilterMatchesService;
import com.filteredmatches.service.IInitialDataSetupService;



@Controller("matchesController")
public class FilteredMatchesController {
	// TODO: exception handling and correct response. Create custom
	// RuntimeExceptions and handle them appropriately. Show the right page and
	// error message

	// only used first time when page load to setup the data
	@Autowired
	@Qualifier("initialSetup")
	private IInitialDataSetupService initialDataSetupService;

	@Autowired
	@Qualifier("matchesService")
	private IFilterMatchesService filterMatchesService;

	// this is only neeeded when the page loads for the first time to locate the
	// view
	@RequestMapping(path = "/matches/{id}", method = {RequestMethod.GET,
			RequestMethod.POST})
	@ResponseBody
	public ModelAndView matches(@PathVariable("id") Integer userId,
			ModelMap modelMap) throws Exception {
		initialDataSetupService.loadDataFromJsonIntoDatabase();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("matches");
		modelMap.addAttribute("id", userId);
		modelAndView.addAllObjects(modelMap);
		return modelAndView;

	}

	@RequestMapping(value = "/api/matches/{id}", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public List<MatchDTO> filteredMatches(@PathVariable("id") Integer userId,
			@RequestBody FilterDTO filters) throws Exception {

		return filterMatchesService.getMatches(userId, filters);

	}

}
