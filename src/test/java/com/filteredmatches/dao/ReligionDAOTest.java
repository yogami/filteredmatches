package com.filteredmatches.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.filteredmatches.config.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class ReligionDAOTest {

	
	
	@Autowired
	private IReligionDAO religionDAO;

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
	public void shouldTestForDistinctReligions() throws Exception {
		List<String> religions = religionDAO.getReligions();
		assertEquals(6,religions.size());
		assertTrue(religions.contains("Islam"));
		assertTrue(religions.contains("Atheist"));
		assertTrue(religions.contains("Christian"));
		assertTrue(religions.contains("Buddhist"));
		assertTrue(religions.contains("Jewish"));
		assertTrue(religions.contains("Agnostic"));
	}

}
