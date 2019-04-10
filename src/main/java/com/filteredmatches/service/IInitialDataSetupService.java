package com.filteredmatches.service;

public interface IInitialDataSetupService {
	
	public void loadDataFromJsonIntoDatabase() throws Exception;
	public void deleteDataFromDatabase() throws Exception;

}
