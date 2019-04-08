package com.filteredmatches.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.filteredmatches.data.ConnectionPool;
import com.filteredmatches.data.FilterData;
import com.filteredmatches.data.LoadData;
import com.filteredmatches.model.User;

@Controller
public class FilteredMatchesController {

	@RequestMapping(path = "/matches/{id}", method = {RequestMethod.GET,
			RequestMethod.POST})
	@ResponseBody
	public ModelAndView matches(@PathVariable("id") Integer userId,
			ModelMap modelMap) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("matches");

		List<User> users = new LinkedList<User>();
		ConnectionPool cpool = new ConnectionPool();

		LoadData loadData = new LoadData(cpool);
		loadData.initialieData();
		FilterData filterData = new FilterData(cpool);
		User currentUser = filterData.retrieveCurrentuser(userId);
		users = filterData.retrieveMatchesForCurrentUser(currentUser, null);
		modelMap.addAttribute("users", users);
		modelAndView.addAllObjects(modelMap);
		return modelAndView;

	}

}
