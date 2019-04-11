package com.filteredmatches.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.filteredmatches.config.AppConfig;
import com.filteredmatches.dao.IReadSourceData;
import com.filteredmatches.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class ReadJsonDataTest {
	
	//class under test
	@Autowired
	@Qualifier("readJson")
	private IReadSourceData readJsonData;
	
	@Test
	public void shouldVerifyIfJsonHasBeenConvertedIntoListOfObjects()
			throws Exception {

		List<User> allUsers = readJsonData.getUserListFromSpecifiedSource();
		assertNotNull(allUsers);
		verifyTheSecondMatch(allUsers);
	}

	private void verifyTheSecondMatch(List<User> allUsers) {
		assertEquals(25, allUsers.size());
		User match = allUsers.get(1);
		assertNotNull(allUsers);
		assertEquals("Solihull", match.getCity().getName());
	}

}
