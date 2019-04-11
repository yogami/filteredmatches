package com.filteredmatches.dao;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.filteredmatches.config.AppConfig;
import com.filteredmatches.dao.ILoadDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class LoadDAOTest {

	// class under test
	// class under test
	@Autowired
	@Qualifier("loadData")
	private ILoadDAO loadData;

	@Test
	public void shouldVerifyIfTableStructureHasBeenCreated() throws Exception {

		assertTrue(loadData.initializeData());

	}

}
