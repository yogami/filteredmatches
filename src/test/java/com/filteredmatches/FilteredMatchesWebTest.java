package com.filteredmatches;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.filteredmatches.server.EmbeddedJettyServer;

public class FilteredMatchesWebTest {

	private static final String MATCHES_URL = "/matches/";
	private static final Integer USER_ID = 1;

	private EmbeddedJettyServer jettyServer = new EmbeddedJettyServer();

	@Before
	public void setUp() throws Exception {

		jettyServer.startServer();
	}

	@Test
	public void shouldReturnMatchesPage() {
		WebDriver client = new HtmlUnitDriver();
		client.get(
				jettyServer.getServerURI().toString() + MATCHES_URL + USER_ID);
		String pageSource = client.getPageSource();

		assertTrue(pageSource.contains("Natalia"));
	}

	@After
	public void tearDown() throws Exception {
		jettyServer.stopServer();
	}

}
