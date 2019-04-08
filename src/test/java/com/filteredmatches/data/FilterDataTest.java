package com.filteredmatches.data;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.filteredmatches.model.City;
import com.filteredmatches.model.User;

public class FilterDataTest {

	//need this as a utility to setup data first
	private static LoadData loadData = new LoadData();
	
	//class under test
	FilterData filterData = new FilterData();

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
		User currentUser = filterData.retrieveCurrentuser(1);
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

		setUpFilterKeyValue("has_photo", "no");
		List<User> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filters);
		assertEquals(3, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyTheMatchesWithPhotoForCurrentUser()
			throws Exception {

		setUpFilterKeyValue("has_photo", "yes");
		List<User> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filters);
		assertEquals(21, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyTheMatchesWithNoContactsExchangedForCurrentUser()
			throws Exception {

		setUpFilterKeyValue("has_exchanged_contacts", "no");
		List<User> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filters);
		assertEquals(13, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyTheMatchesWithContactsExchangedForCurrentUser()
			throws Exception {

		setUpFilterKeyValue("has_exchanged_contacts", "yes");
		List<User> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filters);
		assertEquals(11, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWhichAreNotFavoriteForCurrentUser()
			throws Exception {

		setUpFilterKeyValue("is_favorite", "no");
		List<User> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filters);
		assertEquals(19, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWhichAreFavoriteForCurrentUser()
			throws Exception {

		setUpFilterKeyValue("is_favorite", "yes");
		List<User> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filters);
		assertEquals(5, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWithCompatibilityScoreRangeForCurrentUser()
			throws Exception {

		setUpFilterKeyValue("lower_limit_compatibility", "0.75");
		setUpFilterKeyValue("upper_limit_compatibility", "1.0");
		List<User> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filters);
		assertEquals(21, matches.size());

		setUpFilterKeyValue("lower_limit_compatibility", "0.01");
		setUpFilterKeyValue("upper_limit_compatibility", "0.75");
		matches = filterData.retrieveMatchesForCurrentUser(currentUser,
				filters);
		assertEquals(3, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWithAgeFilterForCurrentUser()
			throws Exception {

		setUpFilterKeyValue("lower_limit_age", "18");
		setUpFilterKeyValue("upper_limit_age", "35");
		List<User> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filters);
		assertEquals(2, matches.size());

		setUpFilterKeyValue("lower_limit_age", "36");
		setUpFilterKeyValue("upper_limit_age", "95");
		matches = filterData.retrieveMatchesForCurrentUser(currentUser,
				filters);
		assertEquals(22, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWithHeightFilterForCurrentUser()
			throws Exception {

		setUpFilterKeyValue("lower_limit_height", "135");
		setUpFilterKeyValue("upper_limit_height", "160");
		List<User> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filters);
		assertEquals(15, matches.size());

		setUpFilterKeyValue("lower_limit_height", "161");
		setUpFilterKeyValue("upper_limit_height", "210");
		matches = filterData.retrieveMatchesForCurrentUser(currentUser,
				filters);
		assertEquals(9, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWithDistanceFilterForCurrentUser()
			throws Exception {

		setUpFilterKeyValue("distance_limit", "200");

		List<User> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filters);
		assertEquals(16, matches.size());

		setUpFilterKeyValue("distance_limit", "100");

		matches = filterData.retrieveMatchesForCurrentUser(currentUser,
				filters);
		assertEquals(2, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWithACombinationOfFilters()
			throws Exception {

		setUpFilterKeyValue("has_photo", "yes");
		setUpFilterKeyValue("has_exchanged_contacts", "yes");
		setUpFilterKeyValue("is_favorite", "yes");
		setUpFilterKeyValue("lower_limit_height", "140");
		setUpFilterKeyValue("upper_limit_height", "200");

		List<User> matches = filterData
				.retrieveMatchesForCurrentUser(currentUser, filters);
		assertEquals(3, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWithAnotherCombinationOfFilters()
			throws Exception {

		setUpFilterKeyValue("has_photo", "yes");
		setUpFilterKeyValue("has_exchanged_contacts", "yes");
		setUpFilterKeyValue("is_favorite", "yes");
		setUpFilterKeyValue("lower_limit_height", "140");
		setUpFilterKeyValue("upper_limit_height", "200");

		setUpFilterKeyValue("distance_limit", "200");

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
