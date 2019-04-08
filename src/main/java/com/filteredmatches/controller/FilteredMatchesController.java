package com.filteredmatches.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.filteredmatches.model.User;
import com.filteredmatches.service.FilterMatchesService;
import com.filteredmatches.service.UserService;

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

}
