package com.filteredmatches.data;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.filteredmatches.FilterMappings;
import com.filteredmatches.model.City;
import com.filteredmatches.model.User;

public class FilterDataTest {

	//need this as a utility to setup data first
	private static LoadData loadData = new LoadData();
	
	//need this to retrieve current User
	private UserData userData = new UserData();
	
	//class under test
	private FilterData filterData = new FilterData();

	private static User currentUser;
	LinkedHashMap<String, String> filters = new LinkedHashMap<String, String>();

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

		setUpFilterKeyValue(FilterMappings.HAS_PHOTO_FILTER, "no");
		List<User> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filters);
		assertEquals(3, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyTheMatchesWithPhotoForCurrentUser()
			throws Exception {

		setUpFilterKeyValue(FilterMappings.HAS_PHOTO_FILTER, "yes");
		List<User> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filters);
		assertEquals(21, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyTheMatchesWithNoContactsExchangedForCurrentUser()
			throws Exception {

		setUpFilterKeyValue(FilterMappings.HAS_CONTACTS_EXCHANGED, "no");
		List<User> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filters);
		assertEquals(13, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyTheMatchesWithContactsExchangedForCurrentUser()
			throws Exception {

		setUpFilterKeyValue(FilterMappings.HAS_CONTACTS_EXCHANGED, "yes");
		List<User> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filters);
		assertEquals(11, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWhichAreNotFavoriteForCurrentUser()
			throws Exception {

		setUpFilterKeyValue(FilterMappings.IS_FAVOURITE, "no");
		List<User> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filters);
		assertEquals(19, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWhichAreFavoriteForCurrentUser()
			throws Exception {

		setUpFilterKeyValue(FilterMappings.IS_FAVOURITE, "yes");
		List<User> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filters);
		assertEquals(5, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWithCompatibilityScoreRangeForCurrentUser()
			throws Exception {

		setUpFilterKeyValue(FilterMappings.LOWER_LIMIT_COMPATIBILITY, "0.75");
		setUpFilterKeyValue(FilterMappings.UPPER_LIMIT_COMPATIBILITY, "1.0");
		List<User> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filters);
		assertEquals(21, matches.size());

		setUpFilterKeyValue(FilterMappings.LOWER_LIMIT_COMPATIBILITY, "0.01");
		setUpFilterKeyValue(FilterMappings.UPPER_LIMIT_COMPATIBILITY, "0.75");
		matches = filterData.retrieveMatchesForCurrentUser(currentUser,
				filters);
		assertEquals(3, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWithAgeFilterForCurrentUser()
			throws Exception {

		setUpFilterKeyValue(FilterMappings.LOWER_LIMIT_AGE, "18");
		setUpFilterKeyValue(FilterMappings.UPPER_LIMIT_AGE, "35");
		List<User> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filters);
		assertEquals(2, matches.size());

		setUpFilterKeyValue(FilterMappings.LOWER_LIMIT_AGE, "36");
		setUpFilterKeyValue(FilterMappings.UPPER_LIMIT_AGE, "95");
		matches = filterData.retrieveMatchesForCurrentUser(currentUser,
				filters);
		assertEquals(22, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWithHeightFilterForCurrentUser()
			throws Exception {

		setUpFilterKeyValue(FilterMappings.LOWER_LIMIT_HEIGHT, "135");
		setUpFilterKeyValue(FilterMappings.UPPER_LIMIT_HEIGHT, "160");
		List<User> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filters);
		assertEquals(15, matches.size());

		setUpFilterKeyValue(FilterMappings.LOWER_LIMIT_HEIGHT, "161");
		setUpFilterKeyValue(FilterMappings.UPPER_LIMIT_HEIGHT, "210");
		matches = filterData.retrieveMatchesForCurrentUser(currentUser,
				filters);
		assertEquals(9, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWithDistanceFilterForCurrentUser()
			throws Exception {

		setUpFilterKeyValue(FilterMappings.DISTANCE_LIMIT, "200");

		List<User> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filters);
		assertEquals(16, matches.size());

		setUpFilterKeyValue(FilterMappings.DISTANCE_LIMIT, "100");

		matches = filterData.retrieveMatchesForCurrentUser(currentUser,
				filters);
		assertEquals(2, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWithACombinationOfFilters()
			throws Exception {

		setUpFilterKeyValue(FilterMappings.HAS_PHOTO_FILTER, "yes");
		setUpFilterKeyValue(FilterMappings.HAS_CONTACTS_EXCHANGED, "yes");
		setUpFilterKeyValue(FilterMappings.IS_FAVOURITE, "yes");
		setUpFilterKeyValue(FilterMappings.LOWER_LIMIT_HEIGHT, "140");
		setUpFilterKeyValue(FilterMappings.UPPER_LIMIT_HEIGHT, "200");

		List<User> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filters);
		assertEquals(3, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWithAnotherCombinationOfFilters()
			throws Exception {

		setUpFilterKeyValue(FilterMappings.HAS_PHOTO_FILTER, "yes");
		setUpFilterKeyValue(FilterMappings.HAS_CONTACTS_EXCHANGED, "yes");
		setUpFilterKeyValue(FilterMappings.IS_FAVOURITE, "yes");
		setUpFilterKeyValue(FilterMappings.LOWER_LIMIT_HEIGHT, "140");
		setUpFilterKeyValue(FilterMappings.UPPER_LIMIT_HEIGHT, "200");

		setUpFilterKeyValue(FilterMappings.DISTANCE_LIMIT, "200");

		List<User> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filters);
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

	private void setUpFilterKeyValue(String key, String value) {
		filters.put(key, value);

	}

}
