package com.filteredmatches.data;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.filteredmatches.dto.FilterDTO;
import com.filteredmatches.model.City;
import com.filteredmatches.model.User;

public class FilterDataTest {

	// need this as a utility to setup data first
	private static LoadData loadData = new LoadData();

	// need this to retrieve current User
	private UserData userData = new UserData();

	// class under test
	private FilterData filterData = new FilterData();

	private static User currentUser;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		currentUser = setUpDataForFilteringTestsAndReturnFirstUser();
	}
	@AfterClass
	public static void tearDown() throws Exception {
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

		List<User> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, null);
		assertEquals(24, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyTheMatchesWithNoPhotoForCurrentUser()
			throws Exception {

		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setHasPhoto("no");

		List<User> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(3, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyTheMatchesWithPhotoForCurrentUser()
			throws Exception {

		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setHasPhoto("yes");
		List<User> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(21, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyTheMatchesWithNoContactsExchangedForCurrentUser()
			throws Exception {
		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setHasContactsExchanged("no");;

		List<User> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(13, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyTheMatchesWithContactsExchangedForCurrentUser()
			throws Exception {
		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setHasContactsExchanged("yes");

		List<User> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(11, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWhichAreNotFavoriteForCurrentUser()
			throws Exception {
		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setIsFavourite("no");

		List<User> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(19, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWhichAreFavoriteForCurrentUser()
			throws Exception {
		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setIsFavourite("yes");

		List<User> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(5, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWithCompatibilityScoreRangeForCurrentUser()
			throws Exception {
		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setLowerLimitCompatibility("0.75");
		filterDTO.setUpperLimitCompatibility("1.0");

		List<User> matches = filterData
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

		List<User> matches = filterData
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
		
		
		List<User> matches = filterData
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

		

		List<User> matches = filterData
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
		
		List<User> matches = filterData
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
		

		
		List<User> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filterDTO);
		assertEquals(2, matches.size());

	}

	private static User setUpDataForFilteringTestsAndReturnFirstUser()
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
