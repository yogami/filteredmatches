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
	private static final String USER = "caroline";

	private EmbeddedJettyServer jettyServer = new EmbeddedJettyServer();;

	@Before
	public void setUp() throws Exception {

		jettyServer.startServer();
	}

	@Test
	public void shouldReturnBlah() {
		WebDriver client = new HtmlUnitDriver();
		client.get(jettyServer.getServerURI().toString() + MATCHES_URL + USER);
		String pageSource = client.getPageSource();
		assertTrue(pageSource.contains("Hello"));
	}

	@After
	public void tearDown() throws Exception {
		jettyServer.stopServer();
	}

}
