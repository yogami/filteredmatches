package com.filteredmatches.controller;

import java.util.List;

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
import com.filteredmatches.model.User;
import com.filteredmatches.service.FilterMatchesService;
import com.filteredmatches.service.UserService;
import com.google.gson.Gson;

@Controller
public class FilteredMatchesController {
//TODO: exception handling and correct response. Create  custom RuntimeExceptions and handle them appropriately. Show the right page and error message
	
	//TODO:annotation for userservice
	
	
	private FilterMatchesService filterMatchesService = new FilterMatchesService();

	@RequestMapping(path = "/matches/{id}", method = {RequestMethod.GET,
			RequestMethod.POST})
	@ResponseBody
	public ModelAndView matches(@PathVariable("id") Integer userId,
			ModelMap modelMap) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("matches");		
		modelMap.addAttribute("id", userId);
		modelAndView.addAllObjects(modelMap);
		return modelAndView;

	}

	@RequestMapping(value = "/api/matches/{id}", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public String filteredMatches(@PathVariable("id") Integer userId,
			@RequestBody FilterDTO filters) throws Exception {

		
		
		//TODO: rename this method to getMatches().
		List<MatchDTO> matches = filterMatchesService
				.getMatches(userId, filters);

		//TODO: investigate if you can just return an array of objects and spring internally 
		//converts the array into json. This way you can skip this gson business
		Gson gson = new Gson();
		String jsonString = gson.toJson(matches);
		return jsonString;

	}

}
