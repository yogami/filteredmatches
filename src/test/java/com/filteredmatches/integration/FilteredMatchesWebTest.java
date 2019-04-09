package com.filteredmatches.integration;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.filteredmatches.MainApp;
import com.filteredmatches.server.EmbeddedJettyServer;
import com.filteredmatches.service.InitialDataSetupService;

public class FilteredMatchesWebTest {

	private static final String MATCHES_URL = "/matches/";
	private static final Integer USER_ID = 1;

	InitialDataSetupService initialDataSetupService = new InitialDataSetupService();
	@Before
	public void setUp() throws Exception {

		try {
			initialDataSetupService.deleteDataFromDatabase();
		} catch (Exception ex) {

		}
		MainApp.startOrStopApp("start");

	}

	@Test
	public void shouldReturnMatchesPage() {
		WebDriver client = new HtmlUnitDriver();
		client.get(MainApp.getServerURI() + MATCHES_URL + USER_ID);
		String pageSource = client.getPageSource();

		assertTrue(pageSource.contains("Natalia"));
	}

	@After
	public void tearDown() throws Exception {
		MainApp.startOrStopApp("stop");
		initialDataSetupService.deleteDataFromDatabase();
	}

}
