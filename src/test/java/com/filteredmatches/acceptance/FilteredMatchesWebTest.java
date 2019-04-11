package com.filteredmatches.acceptance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
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
import com.filteredmatches.dto.MatchDTO;
import com.filteredmatches.service.IInitialDataSetupService;
import com.google.gson.Gson;

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

		try {
			initialDataSetupService.deleteDataFromDatabase();
		} catch (Exception ex) {

		}
		MainApp.startOrStopApp("start");
		initialDataSetupService.loadDataFromJsonIntoDatabase();

	}

	@Test
	public void shouldRenderTheFilteredResultsDiv() throws Exception {
		WebDriver client = new HtmlUnitDriver();
		client.get(MainApp.getServerURI() + MATCHES_URL + USER_ID);
		String pageSource = client.getPageSource();
		assertTrue(pageSource.contains("<div id=\"matchResults\">"));
	}

	@Test
	public void shouldReturnAllResultsWithoutFilterForAPIcall()
			throws Exception {
		FilterDTO filterDTO = new FilterDTO();

		verifyIfTheStatedNumberOfRecordsWereLoadedForTheFilterCriteria(24,
				filterDTO);
	}

	@Test
	public void withNoPhotoFilterForAPIcall()
			throws Exception {
		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setHasPhoto("no");
		verifyIfTheStatedNumberOfRecordsWereLoadedForTheFilterCriteria(3,
				filterDTO);
	}
	
	@Test
	public void withPhotoFilterForAPIcall()
			throws Exception {
		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setHasPhoto("yes");
		verifyIfTheStatedNumberOfRecordsWereLoadedForTheFilterCriteria(21,
				filterDTO);
	}
	
	@Test
	public void withNoContactsExchangedFilterForAPIcall()
			throws Exception {
		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setHasContactsExchanged("no");
		verifyIfTheStatedNumberOfRecordsWereLoadedForTheFilterCriteria(13,
				filterDTO);
	}
	
	@Test
	public void withContactsExchangedFilterAPIcall()
			throws Exception {
		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setHasContactsExchanged("yes");

		verifyIfTheStatedNumberOfRecordsWereLoadedForTheFilterCriteria(11,
				filterDTO);

	}

	@Test
	public void withNotFavoriteFilterAPIcall()
			throws Exception {
		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setIsFavourite("no");

		verifyIfTheStatedNumberOfRecordsWereLoadedForTheFilterCriteria(19,
				filterDTO);

	}

	@Test
	public void withFavoriteFilterAPIcall()
			throws Exception {
		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setIsFavourite("yes");
		verifyIfTheStatedNumberOfRecordsWereLoadedForTheFilterCriteria(5,
				filterDTO);

	}

	@Test
	public void withCompatibilityScoreRangeFilterAPIcall()
			throws Exception {
		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setLowerLimitCompatibility("0.75");
		filterDTO.setUpperLimitCompatibility("1.0");

		verifyIfTheStatedNumberOfRecordsWereLoadedForTheFilterCriteria(21,
				filterDTO);

		filterDTO = new FilterDTO();
		filterDTO.setLowerLimitCompatibility("0.01");
		filterDTO.setUpperLimitCompatibility("0.75");

		verifyIfTheStatedNumberOfRecordsWereLoadedForTheFilterCriteria(3,
				filterDTO);

	}

	@Test
	public void withAgeFilterFilterAPIcall()
			throws Exception {

		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setLowerLimitAge("18");
		filterDTO.setUpperLimitAge("35");
		verifyIfTheStatedNumberOfRecordsWereLoadedForTheFilterCriteria(2,
				filterDTO);

		filterDTO = new FilterDTO();
		filterDTO.setLowerLimitAge("36");
		filterDTO.setUpperLimitAge("95");

		verifyIfTheStatedNumberOfRecordsWereLoadedForTheFilterCriteria(22,
				filterDTO);
	}

	@Test
	public void withHeightFilterFilterAPIcall()
			throws Exception {

		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setLowerLimitHeight("135");
		filterDTO.setUpperLimitHeight("160");

		verifyIfTheStatedNumberOfRecordsWereLoadedForTheFilterCriteria(15,
				filterDTO);

		filterDTO = new FilterDTO();
		filterDTO.setLowerLimitHeight("161");
		filterDTO.setUpperLimitHeight("210");

		verifyIfTheStatedNumberOfRecordsWereLoadedForTheFilterCriteria(9,
				filterDTO);

	}

	@Test
	public void withDistanceFilterFilterAPIcall()
			throws Exception {

		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setDistanceLimit("200");

		verifyIfTheStatedNumberOfRecordsWereLoadedForTheFilterCriteria(16,
				filterDTO);

		filterDTO = new FilterDTO();
		filterDTO.setDistanceLimit("100");

		verifyIfTheStatedNumberOfRecordsWereLoadedForTheFilterCriteria(2,
				filterDTO);

	}

	@Test
	public void withACombinationOfFiltersAPIcall()
			throws Exception {

		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setHasPhoto("yes");
		filterDTO.setHasContactsExchanged("yes");
		filterDTO.setIsFavourite("yes");
		filterDTO.setLowerLimitHeight("140");
		filterDTO.setUpperLimitHeight("200");

		verifyIfTheStatedNumberOfRecordsWereLoadedForTheFilterCriteria(3,
				filterDTO);

	}

	@Test
	public void withAnotherCombinationOfFiltersAPIcall()
			throws Exception {

		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setHasPhoto("yes");
		filterDTO.setHasContactsExchanged("yes");
		filterDTO.setIsFavourite("yes");
		filterDTO.setLowerLimitHeight("140");
		filterDTO.setUpperLimitHeight("200");
		filterDTO.setDistanceLimit("200");

		verifyIfTheStatedNumberOfRecordsWereLoadedForTheFilterCriteria(2,
				filterDTO);

	}

	@Test
	public void withoutPhotoAndFavouriteAPIcall()
			throws Exception {

		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setHasPhoto("no");

		filterDTO.setIsFavourite("yes");

		verifyIfTheStatedNumberOfRecordsWereLoadedForTheFilterCriteria(1,
				filterDTO);

	}

	private void verifyIfTheStatedNumberOfRecordsWereLoadedForTheFilterCriteria(
			int numberOfRecordsToVerify, FilterDTO filterDTO) throws Exception {
		Gson gson = new Gson();
		String postBody = gson.toJson(filterDTO);

		HttpClient httpClient = HttpClientBuilder.create().build(); // Use this
																	// instead
		HttpPost post = new HttpPost(
				MainApp.getServerURI() + API_MATCHES_URL + USER_ID);

		HttpResponse response = null;
		String apiResponse = null;
		try {
			post.setHeader("Content-type", "application/json");
			post.setHeader("Accept", "application/json");
			post.setEntity(new StringEntity(postBody));
			response = httpClient.execute(post);
			apiResponse = EntityUtils.toString(response.getEntity());

		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(numberOfRecordsToVerify,
				StringUtils.countOccurrencesOf(apiResponse, "display_name"));
	}

	@After
	public void tearDown() throws Exception {
		MainApp.startOrStopApp("stop");
		initialDataSetupService.deleteDataFromDatabase();

	}

}
