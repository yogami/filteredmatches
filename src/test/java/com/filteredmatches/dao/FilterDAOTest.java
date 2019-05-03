package com.filteredmatches.dao;

import static org.junit.Assert.assertEquals;

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
import com.filteredmatches.dao.IFilterDAO;
import com.filteredmatches.dao.ILoadDAO;
import com.filteredmatches.dao.IUserDAO;
import com.filteredmatches.dto.FilterDTO;
import com.filteredmatches.dto.MatchDTO;
import com.filteredmatches.model.City;
import com.filteredmatches.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class FilterDAOTest {

	// need this as a utility to setup data first
	@Autowired
	@Qualifier("loadData")
	private ILoadDAO loadData;

	// need this to retrieve current User

	@Autowired
	@Qualifier("userData")
	private IUserDAO userData;

	// class under test
	@Autowired
	@Qualifier("filterData")
	private IFilterDAO filterData;

	private User currentUser;

	@Before
	public void setUp() throws Exception {
		currentUser = setUpDataForFilteringTestsAndReturnFirstUser();
	}
	@After
	public void tearDown() throws Exception {
		loadData.deleteTable();
	}

	@Test
	public void shouldVerifyTheMatchesForCurrentUserWithNoFilters()
			throws Exception {

		List<MatchDTO> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, null);
		assertEquals(24, matches.size());

	}

	@Test
	public void shouldVerifyTheMatchesWithNoPhotoForCurrentUser()
			throws Exception {

		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setHasPhoto("no");

		List<MatchDTO> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(3, matches.size());

	}

	@Test
	public void shouldVerifyTheMatchesWithPhotoForCurrentUser()
			throws Exception {

		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setHasPhoto("yes");
		List<MatchDTO> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(21, matches.size());

	}

	@Test
	public void shouldVerifyTheMatchesWithNoContactsExchangedForCurrentUser()
			throws Exception {
		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setHasContactsExchanged("no");;

		List<MatchDTO> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(13, matches.size());

	}

	@Test
	public void shouldVerifyTheMatchesWithContactsExchangedForCurrentUser()
			throws Exception {
		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setHasContactsExchanged("yes");

		List<MatchDTO> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(11, matches.size());

	}

	@Test
	public void shouldVerifyMatchesWhichAreNotFavoriteForCurrentUser()
			throws Exception {
		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setIsFavourite("no");

		List<MatchDTO> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(19, matches.size());

	}

	@Test
	public void shouldVerifyMatchesWhichAreFavoriteForCurrentUser()
			throws Exception {
		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setIsFavourite("yes");

		List<MatchDTO> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(5, matches.size());

	}

	@Test
	public void shouldVerifyMatchesWithCompatibilityScoreRangeForCurrentUser()
			throws Exception {
		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setLowerLimitCompatibility("0.75");
		filterDTO.setUpperLimitCompatibility("1.0");

		List<MatchDTO> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(21, matches.size());

	}

	@Test
	public void shouldVerifyMatchesWithAgeFilterForCurrentUser()
			throws Exception {

		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setLowerLimitAge("18");
		filterDTO.setUpperLimitAge("35");

		List<MatchDTO> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(2, matches.size());

	}

	@Test
	public void shouldVerifyMatchesWithHeightFilterForCurrentUser()
			throws Exception {

		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setLowerLimitHeight("135");
		filterDTO.setUpperLimitHeight("160");

		List<MatchDTO> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(15, matches.size());

	}

	@Test
	public void shouldVerifyMatchesWithDistanceFilterForCurrentUser()
			throws Exception {

		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setDistanceLimit("200");

		List<MatchDTO> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(16, matches.size());

	}

	@Test
	public void shouldVerifyMatchesWithoutPhotoAndFavourite() throws Exception {

		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setHasPhoto("no");

		filterDTO.setIsFavourite("yes");

		List<MatchDTO> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(1, matches.size());

	}

	@Test
	public void shouldVerifyMatchesWithASpecificReligion() throws Exception {

		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setReligion("Atheist");
		List<MatchDTO> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(6, matches.size());
		
	}
	
	

	private User setUpDataForFilteringTestsAndReturnFirstUser()
			throws Exception {

		loadData.initializeData();
		User user = new User();
		user.setUserId(1);
		City city = new City();
		city.setLat(53.801f);
		city.setLon(-1.548567f);
		user.setCity(city);
		return user;
	}

}
