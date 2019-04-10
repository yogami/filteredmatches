package com.filteredmatches.acceptance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import com.filteredmatches.MainApp;
import com.filteredmatches.config.AppConfig;
import com.filteredmatches.dto.FilterDTO;
import com.filteredmatches.service.IInitialDataSetupService;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class FilteredMatchesWebTest {

	private static final String API_MATCHES_URL = "/api/matches/";
	private static final String MATCHES_URL = "/matches/";
	private static final Integer USER_ID = 1;

	@Autowired
	@Qualifier("initialSetup")
	private IInitialDataSetupService initialDataSetupService;// = new
																// InitialDataSetupService();
	@Before
	public void setUp() throws Exception {

		// try {
		// initialDataSetupService.deleteDataFromDatabase();
		// } catch (Exception ex) {
		//
		// }
		MainApp.startOrStopApp("start");

	}

	@Test
	@Ignore
	public void verifyApiCallFromClient() throws Exception {
		FilterDTO filterDTO = new FilterDTO();

		Gson gson = new Gson();
		String postBody = gson.toJson(filterDTO);
		HttpResponse<JsonNode> response = Unirest
				.post(MainApp.getServerURI() + API_MATCHES_URL + USER_ID)
				.header("accept", "application/json")
				.header("Content-Type", "application/json").body(postBody)
				.asJson();

		JsonNode jsonNode = response.getBody();

		assertEquals(24, StringUtils.countOccurrencesOf(jsonNode.toString(),
				"display_name"));
	}

	@Test
	public void shouldRenderTheFilteredResultsDiv()
			throws Exception {
		WebDriver client = new HtmlUnitDriver();
		client.get(MainApp.getServerURI() + MATCHES_URL + USER_ID);
		String pageSource = client.getPageSource();
		assertNotNull(pageSource.contains("filteredResults"));
	}

	@After
	public void tearDown() throws Exception {
		MainApp.startOrStopApp("stop");
		// initialDataSetupService.deleteDataFromDatabase();
	}

}
