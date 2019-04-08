package com.filteredmatches.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import com.filteredmatches.model.User;

public class ReadJsonDataTest {
	
	//class under test
	private ReadJsonData readJsonData = new ReadJsonData();
	
	@Test
	public void shouldVerifyIfJsonHasBeenConvertedIntoListOfObjects()
			throws Exception {

		List<User> allUsers = readJsonData.getUserListFromJsonFile();
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
