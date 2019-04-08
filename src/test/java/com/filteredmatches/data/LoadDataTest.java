package com.filteredmatches.data;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LoadDataTest {

	//class under test
	private LoadData loadData = new LoadData();

	@Test
	public void shouldVerifyIfTableStructureHasBeenCreated() throws Exception {

		assertTrue(loadData.initializeData());

	}

}
