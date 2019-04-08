package com.filteredmatches.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.filteredmatches.model.User;

public class LoadDataTest {

	
	LoadData loadData = new LoadData();

	private String jsonDataFromFile = "";
	private static final String JSON_FILE_NAME = "users.json";

	@Before
	public void setUp() throws Exception {
		jsonDataFromFile = loadData.getFile(JSON_FILE_NAME);

	}

	@Test
	public void shouldVerifyIfTableStructureHasBeenCreated() throws Exception {

		assertTrue(loadData.createDDL());

	}

	@Test
	public void shouldVerifyIfJsonHasBeenConvertedIntoListOfObjects()
			throws Exception {

		List<User> allUsers = loadData
				.parseJsonIntoMatchObjects(jsonDataFromFile);
		assertNotNull(allUsers);
		verifyTheSecondMatch(allUsers);
	}

	private void verifyTheSecondMatch(List<User> allUsers) {
		assertEquals(allUsers.size(), 25);
		User match = allUsers.get(1);
		assertNotNull(allUsers);
		assertEquals("Solihull", match.getCity().getName());
	}

}
