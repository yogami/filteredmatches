package com.filteredmatches.controller;

import java.util.LinkedHashMap;
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
import com.filteredmatches.model.User;
import com.filteredmatches.service.FilterMatchesService;
import com.filteredmatches.service.UserService;
import com.google.gson.Gson;

@Controller
public class FilteredMatchesController {

	private UserService userService = new UserService();
	private FilterMatchesService filterMatchesService = new FilterMatchesService();

	@RequestMapping(path = "/matches/{id}", method = {RequestMethod.GET,
			RequestMethod.POST})
	@ResponseBody
	public ModelAndView matches(@PathVariable("id") Integer userId,
			ModelMap modelMap) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("matches");

		User currentUser = userService.getCurrentUserById(userId);
		List<User> users = filterMatchesService
				.getMatchesForCurrentUser(currentUser, null);

		modelMap.addAttribute("users", users);
		modelAndView.addAllObjects(modelMap);
		return modelAndView;

	}

	@RequestMapping(value = "/api/matches/{id}", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public String filteredMatches(@PathVariable("id") Integer userId,
			@RequestBody FilterDTO filters) throws Exception {

		User currentUser = userService.getCurrentUserById(userId);
		
		List<User> users = filterMatchesService
				.getMatchesForCurrentUser(currentUser, filters);

		Gson gson = new Gson();
		String jsonString = gson.toJson(users);
		return jsonString;

	}

}
