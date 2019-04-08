package com.filteredmatches.service;

import com.filteredmatches.data.LoadData;

public class InitialDataSetupService {
	
	
	LoadData loadData = new LoadData();
	public void loadDataFromJsonIntoDatabase() throws Exception{
		loadData.initializeData();
	}
	public void deleteDataFromDatabase() throws Exception {
		loadData.deleteTable();
		
	}

}
