package com.filteredmatches.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.util.StringUtils;

import com.filteredmatches.MainApp;
import com.filteredmatches.dto.FilterDTO;
import com.filteredmatches.service.InitialDataSetupService;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class FilteredMatchesWebTest {

	private static final String API_MATCHES_URL = "/api/matches/";
	private static final String MATCHES_URL = "/matches";
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
	public void shouldReturnMatchesWHenThePageLoads() throws Exception {
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
	@Ignore
	public void shouldRenderPageCorrectlyWIthMatchesWhenThePageLoads() throws Exception{
		WebDriver client = new HtmlUnitDriver();
		client.get(MainApp.getServerURI() + MATCHES_URL + USER_ID);
		String pageSource = client.getPageSource();
		assertNotNull(pageSource.contains("filteredResults"));
		assertNotNull(pageSource.contains("Natalia"));
		assertEquals(24,StringUtils.countOccurrencesOf(pageSource.toString(),
				"Age:"));
	}

	@After
	public void tearDown() throws Exception {
		MainApp.startOrStopApp("stop");
		initialDataSetupService.deleteDataFromDatabase();
	}

}
