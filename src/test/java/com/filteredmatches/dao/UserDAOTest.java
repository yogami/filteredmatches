package com.filteredmatches.dao;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.filteredmatches.config.AppConfig;
import com.filteredmatches.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class UserDAOTest {

	@Autowired
	@Qualifier("userData")
	private IUserDAO userData;

	// need this as a utility to setup data first
	@Autowired
	@Qualifier("loadData")
	private ILoadDAO loadData;

	@Before
	public void setUp() throws Exception {
		loadData.initializeData();
	}
	@After
	public void tearDown() throws Exception {
		loadData.deleteTable();
	}

	@Test
	public void shouldRetrieveCurrentUser() throws Exception {
		User currentUser = userData.retrieveCurrentuser(1);
		assertEquals("Caroline", currentUser.getDisplay_name());
	}

}
