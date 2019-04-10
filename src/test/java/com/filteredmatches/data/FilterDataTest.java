package com.filteredmatches.data;

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
import com.filteredmatches.data.IFilterData;
import com.filteredmatches.data.ILoadData;
import com.filteredmatches.data.IUserData;
import com.filteredmatches.dto.FilterDTO;
import com.filteredmatches.dto.MatchDTO;
import com.filteredmatches.model.City;
import com.filteredmatches.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class FilterDataTest {

	// need this as a utility to setup data first
	@Autowired
	@Qualifier("loadData")
	private ILoadData loadData;

	// need this to retrieve current User

	@Autowired
	@Qualifier("userData")
	private IUserData userData;

	// class under test
	@Autowired
	@Qualifier("filterData")
	private IFilterData filterData;

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
	public void shouldRetrieveCurrentUser() throws Exception {
		User currentUser = userData.retrieveCurrentuser(1);
		assertEquals("Caroline", currentUser.getDisplay_name());
	}
	@Test
	public void shouldInsertUsersAndVerifyTheMatchesForCurrentUserWithNoFilters()
			throws Exception {

		List<MatchDTO> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, null);
		assertEquals(24, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyTheMatchesWithNoPhotoForCurrentUser()
			throws Exception {

		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setHasPhoto("no");

		List<MatchDTO> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(3, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyTheMatchesWithPhotoForCurrentUser()
			throws Exception {

		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setHasPhoto("yes");
		List<MatchDTO> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(21, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyTheMatchesWithNoContactsExchangedForCurrentUser()
			throws Exception {
		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setHasContactsExchanged("no");;

		List<MatchDTO> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(13, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyTheMatchesWithContactsExchangedForCurrentUser()
			throws Exception {
		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setHasContactsExchanged("yes");

		List<MatchDTO> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(11, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWhichAreNotFavoriteForCurrentUser()
			throws Exception {
		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setIsFavourite("no");

		List<MatchDTO> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(19, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWhichAreFavoriteForCurrentUser()
			throws Exception {
		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setIsFavourite("yes");

		List<MatchDTO> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(5, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWithCompatibilityScoreRangeForCurrentUser()
			throws Exception {
		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setLowerLimitCompatibility("0.75");
		filterDTO.setUpperLimitCompatibility("1.0");

		List<MatchDTO> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(21, matches.size());

		filterDTO = new FilterDTO();
		filterDTO.setLowerLimitCompatibility("0.01");
		filterDTO.setUpperLimitCompatibility("0.75");

		matches = filterData.retrieveMatchesForCurrentUser(currentUser,
				filterDTO);
		assertEquals(3, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWithAgeFilterForCurrentUser()
			throws Exception {

		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setLowerLimitAge("18");
		filterDTO.setUpperLimitAge("35");

		List<MatchDTO> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(2, matches.size());

		filterDTO = new FilterDTO();
		filterDTO.setLowerLimitAge("36");
		filterDTO.setUpperLimitAge("95");

		matches = filterData.retrieveMatchesForCurrentUser(currentUser,
				filterDTO);
		assertEquals(22, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWithHeightFilterForCurrentUser()
			throws Exception {

		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setLowerLimitHeight("135");
		filterDTO.setUpperLimitHeight("160");

		List<MatchDTO> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(15, matches.size());

		filterDTO = new FilterDTO();
		filterDTO.setLowerLimitHeight("161");
		filterDTO.setUpperLimitHeight("210");

		matches = filterData.retrieveMatchesForCurrentUser(currentUser,
				filterDTO);
		assertEquals(9, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWithDistanceFilterForCurrentUser()
			throws Exception {

		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setDistanceLimit("200");

		List<MatchDTO> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(16, matches.size());

		filterDTO = new FilterDTO();
		filterDTO.setDistanceLimit("100");

		matches = filterData.retrieveMatchesForCurrentUser(currentUser,
				filterDTO);
		assertEquals(2, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWithACombinationOfFilters()
			throws Exception {

		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setHasPhoto("yes");
		filterDTO.setHasContactsExchanged("yes");
		filterDTO.setIsFavourite("yes");
		filterDTO.setLowerLimitHeight("140");
		filterDTO.setUpperLimitHeight("200");

		List<MatchDTO> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(3, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWithAnotherCombinationOfFilters()
			throws Exception {

		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setHasPhoto("yes");
		filterDTO.setHasContactsExchanged("yes");
		filterDTO.setIsFavourite("yes");
		filterDTO.setLowerLimitHeight("140");
		filterDTO.setUpperLimitHeight("200");
		filterDTO.setDistanceLimit("200");

		List<MatchDTO> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(2, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWithoutPhotoAndFavourite()
			throws Exception {

		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setHasPhoto("no");

		filterDTO.setIsFavourite("yes");

		List<MatchDTO> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(1, matches.size());

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
