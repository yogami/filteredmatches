package com.filteredmatches.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filteredmatches.dao.IReligionDAO;

@Service
public class ReligionService implements IReligionService {

	@Autowired
	private IReligionDAO religionDAO;

	@Override
	public List<String> getReligions() throws Exception {
		return religionDAO.getReligions();
	}

}
