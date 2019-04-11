package com.filteredmatches.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.filteredmatches.dao.ILoadDAO;

@Service("initialSetup")
public class InitialDataSetupService implements IInitialDataSetupService {

	@Autowired
	@Qualifier("loadData")
	private ILoadDAO loadData;
	
	
	public void loadDataFromJsonIntoDatabase() throws Exception {
		loadData.initializeData();
	}
	public void deleteDataFromDatabase() throws Exception {
		loadData.deleteTable();

	}

}
