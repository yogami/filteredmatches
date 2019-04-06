package com.filteredmatches.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FilteredMatchesController {

	@RequestMapping(path = "/matches/{name}", method = {RequestMethod.GET,
			RequestMethod.POST})
	@ResponseBody
	public ModelAndView matches(@PathVariable("name") String name) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("matches");
		return modelAndView;

	}

}
