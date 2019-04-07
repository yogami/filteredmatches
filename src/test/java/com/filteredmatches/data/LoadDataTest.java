package com.filteredmatches.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.filteredmatches.model.User;

public class LoadDataTest {
	String JSON_DATA = "{\n" + "  \"matches\": [\n" + "    {\n"
			+ "      \"display_name\": \"Caroline\",\n" + "      \"age\": 41,\n"
			+ "      \"job_title\": \"Corporate Lawyer\",\n"
			+ "      \"height_in_cm\": 153,\n" + "      \"city\": {\n"
			+ "        \"name\": \"Leeds\",\n" + "        \"lat\": 53.801277,\n"
			+ "        \"lon\": -1.548567\n" + "      },\n"
			+ "      \"main_photo\": \"http://thecatapi.com/api/images/get?format=src&type=gif\",\n"
			+ "      \"compatibility_score\": 0.76,\n"
			+ "      \"contacts_exchanged\": 2,\n"
			+ "      \"favourite\": true,\n"
			+ "      \"religion\": \"Atheist\"\n" + "    },\n" + "    {\n"
			+ "      \"display_name\": \"Sharon\",\n" + "      \"age\": 47,\n"
			+ "      \"job_title\": \"Doctor\",\n"
			+ "      \"height_in_cm\": 161,\n" + "      \"city\": {\n"
			+ "        \"name\": \"Solihull\",\n"
			+ "        \"lat\": 52.412811,\n" + "        \"lon\": -1.778197\n"
			+ "      },\n"
			+ "      \"main_photo\": \"http://thecatapi.com/api/images/get?format=src&type=gif\",\n"
			+ "      \"compatibility_score\": 0.97,\n"
			+ "      \"contacts_exchanged\": 0,\n"
			+ "      \"favourite\": false,\n"
			+ "      \"religion\": \"Islam\"\n" + "    },\n" + "    {\n"
			+ "      \"display_name\": \"Natalia\",\n" + "      \"age\": 38,\n"
			+ "      \"job_title\": \"Project Manager\",\n"
			+ "      \"height_in_cm\": 144,\n" + "      \"city\": {\n"
			+ "        \"name\": \"Cardiff\",\n"
			+ "        \"lat\": 51.481583,\n" + "        \"lon\": -3.179090\n"
			+ "      },\n"
			+ "      \"main_photo\": \"http://thecatapi.com/api/images/get?format=src&type=gif\",\n"
			+ "      \"compatibility_score\": 0.47,\n"
			+ "      \"contacts_exchanged\": 5,\n"
			+ "      \"favourite\": false,\n"
			+ "      \"religion\": \"Christian\"\n" + "    },\n" + "    {\n"
			+ "      \"display_name\": \"Marta\",\n" + "      \"age\": 55,\n"
			+ "      \"job_title\": \"Finance\",\n"
			+ "      \"height_in_cm\": 140,\n" + "      \"city\": {\n"
			+ "        \"name\": \"Eastbourne\",\n"
			+ "        \"lat\": 50.768036,\n" + "        \"lon\": 0.290472\n"
			+ "      },\n"
			+ "      \"main_photo\": \"http://thecatapi.com/api/images/get?format=src&type=gif\",\n"
			+ "      \"compatibility_score\": 0.95,\n"
			+ "      \"contacts_exchanged\": 0,\n"
			+ "      \"favourite\": false,\n"
			+ "      \"religion\": \"Agnostic\"\n" + "    },\n" + "    {\n"
			+ "      \"display_name\": \"Maria\",\n" + "      \"age\": 43,\n"
			+ "      \"job_title\": \"CEO\",\n"
			+ "      \"height_in_cm\": 175,\n" + "      \"city\": {\n"
			+ "        \"name\": \"London\",\n"
			+ "        \"lat\": 51.509865,\n" + "        \"lon\": -0.118092\n"
			+ "      },\n"
			+ "      \"main_photo\": \"http://thecatapi.com/api/images/get?format=src&type=gif\",\n"
			+ "      \"compatibility_score\": 0.88,\n"
			+ "      \"contacts_exchanged\": 0,\n"
			+ "      \"favourite\": false,\n"
			+ "      \"religion\": \"Christian\"\n" + "    },\n" + "    {\n"
			+ "      \"display_name\": \"Stephanie\",\n"
			+ "      \"age\": 39,\n"
			+ "      \"job_title\": \"Project Manager\",\n"
			+ "      \"height_in_cm\": 153,\n" + "      \"city\": {\n"
			+ "        \"name\": \"London\",\n"
			+ "        \"lat\": 51.509865,\n" + "        \"lon\": -0.118092\n"
			+ "      },\n" + "      \"compatibility_score\": 0.87,\n"
			+ "      \"contacts_exchanged\": 4,\n"
			+ "      \"favourite\": false,\n"
			+ "      \"religion\": \"Christian\"\n" + "    },\n" + "    {\n"
			+ "      \"display_name\": \"Claire\",\n" + "      \"age\": 48,\n"
			+ "      \"job_title\": \"GP\",\n"
			+ "      \"height_in_cm\": 167,\n" + "      \"city\": {\n"
			+ "        \"name\": \"London\",\n"
			+ "        \"lat\": 51.509865,\n" + "        \"lon\": -0.118092\n"
			+ "      },\n"
			+ "      \"main_photo\": \"http://thecatapi.com/api/images/get?format=src&type=gif\",\n"
			+ "      \"compatibility_score\": 0.83,\n"
			+ "      \"contacts_exchanged\": 6,\n"
			+ "      \"favourite\": false,\n"
			+ "      \"religion\": \"Atheist\"\n" + "    },\n" + "    {\n"
			+ "      \"display_name\": \"Colette\",\n" + "      \"age\": 39,\n"
			+ "      \"job_title\": \"Doctor - Hospital\",\n"
			+ "      \"height_in_cm\": 177,\n" + "      \"city\": {\n"
			+ "        \"name\": \"Swindon\",\n"
			+ "        \"lat\": 51.568535,\n" + "        \"lon\": -1.772232\n"
			+ "      },\n"
			+ "      \"main_photo\": \"http://thecatapi.com/api/images/get?format=src&type=gif\",\n"
			+ "      \"compatibility_score\": 0.89,\n"
			+ "      \"contacts_exchanged\": 2,\n"
			+ "      \"favourite\": false,\n"
			+ "      \"religion\": \"Christian\"\n" + "    },\n" + "    {\n"
			+ "      \"display_name\": \"Caroline\",\n" + "      \"age\": 43,\n"
			+ "      \"job_title\": \"Marketing Consultant\",\n"
			+ "      \"height_in_cm\": 160,\n" + "      \"city\": {\n"
			+ "        \"name\": \"Oxford\",\n"
			+ "        \"lat\": 51.752022,\n" + "        \"lon\": -1.257677\n"
			+ "      },\n"
			+ "      \"main_photo\": \"http://thecatapi.com/api/images/get?format=src&type=gif\",\n"
			+ "      \"compatibility_score\": 0.91,\n"
			+ "      \"contacts_exchanged\": 1,\n"
			+ "      \"favourite\": false,\n"
			+ "      \"religion\": \"Atheist\"\n" + "    },\n" + "    {\n"
			+ "      \"display_name\": \"Kate\",\n" + "      \"age\": 42,\n"
			+ "      \"job_title\": \"Psychologist\",\n"
			+ "      \"height_in_cm\": 160,\n" + "      \"city\": {\n"
			+ "        \"name\": \"Salisbury\",\n"
			+ "        \"lat\": 51.068787,\n" + "        \"lon\": -1.794472\n"
			+ "      },\n"
			+ "      \"main_photo\": \"http://thecatapi.com/api/images/get?format=src&type=gif\",\n"
			+ "      \"compatibility_score\": 0.97,\n"
			+ "      \"contacts_exchanged\": 10,\n"
			+ "      \"favourite\": false,\n"
			+ "      \"religion\": \"Buddhist\"\n" + "    },\n" + "    {\n"
			+ "      \"display_name\": \"Katie\",\n" + "      \"age\": 40,\n"
			+ "      \"job_title\": \"Lawyer\",\n"
			+ "      \"height_in_cm\": 148,\n" + "      \"city\": {\n"
			+ "        \"name\": \"Weymouth\",\n"
			+ "        \"lat\": 50.614429,\n" + "        \"lon\": -2.457621\n"
			+ "      },\n" + "      \"compatibility_score\": 0.94,\n"
			+ "      \"contacts_exchanged\": 0,\n"
			+ "      \"favourite\": false,\n"
			+ "      \"religion\": \"Atheist\"\n" + "    },\n" + "    {\n"
			+ "      \"display_name\": \"Clare\",\n" + "      \"age\": 40,\n"
			+ "      \"job_title\": \"Accountant\",\n"
			+ "      \"height_in_cm\": 144,\n" + "      \"city\": {\n"
			+ "        \"name\": \"Bournemouth\",\n"
			+ "        \"lat\": 50.720806,\n" + "        \"lon\": -1.904755\n"
			+ "      },\n"
			+ "      \"main_photo\": \"http://thecatapi.com/api/images/get?format=src&type=gif\",\n"
			+ "      \"compatibility_score\": 0.90,\n"
			+ "      \"contacts_exchanged\": 8,\n"
			+ "      \"favourite\": false,\n"
			+ "      \"religion\": \"Christian\"\n" + "    },\n" + "    {\n"
			+ "      \"display_name\": \"Laura\",\n" + "      \"age\": 39,\n"
			+ "      \"job_title\": \"Lawyer\",\n"
			+ "      \"height_in_cm\": 160,\n" + "      \"city\": {\n"
			+ "        \"name\": \"Plymouth\",\n"
			+ "        \"lat\": 50.376289,\n" + "        \"lon\": -4.143841\n"
			+ "      },\n"
			+ "      \"main_photo\": \"http://thecatapi.com/api/images/get?format=src&type=gif\",\n"
			+ "      \"compatibility_score\": 0.89,\n"
			+ "      \"contacts_exchanged\": 0,\n"
			+ "      \"favourite\": false,\n"
			+ "      \"religion\": \"Christian\"\n" + "    },\n" + "    {\n"
			+ "      \"display_name\": \"Katlin\",\n" + "      \"age\": 39,\n"
			+ "      \"job_title\": \"Barrister\",\n"
			+ "      \"height_in_cm\": 153,\n" + "      \"city\": {\n"
			+ "        \"name\": \"Inverness\",\n"
			+ "        \"lat\": 57.477772,\n" + "        \"lon\": -4.224721\n"
			+ "      },\n"
			+ "      \"main_photo\": \"http://thecatapi.com/api/images/get?format=src&type=gif\",\n"
			+ "      \"compatibility_score\": 0.87,\n"
			+ "      \"contacts_exchanged\": 0,\n"
			+ "      \"favourite\": true,\n"
			+ "      \"religion\": \"Jewish\"\n" + "    },\n" + "    {\n"
			+ "      \"display_name\": \"Tracy\",\n" + "      \"age\": 39,\n"
			+ "      \"job_title\": \"Lawyer\",\n"
			+ "      \"height_in_cm\": 153,\n" + "      \"city\": {\n"
			+ "        \"name\": \"Aberdeen\",\n"
			+ "        \"lat\": 57.149651,\n" + "        \"lon\": -2.099075\n"
			+ "      },\n"
			+ "      \"main_photo\": \"http://thecatapi.com/api/images/get?format=src&type=gif\",\n"
			+ "      \"compatibility_score\": 0.87,\n"
			+ "      \"contacts_exchanged\": 0,\n"
			+ "      \"favourite\": false,\n"
			+ "      \"religion\": \"Christian\"\n" + "    },\n" + "    {\n"
			+ "      \"display_name\": \"Angie\",\n" + "      \"age\": 50,\n"
			+ "      \"job_title\": \"Accountant\",\n"
			+ "      \"height_in_cm\": 153,\n" + "      \"city\": {\n"
			+ "        \"name\": \"Ayr\",\n" + "        \"lat\": 55.458565,\n"
			+ "        \"lon\": -4.629179\n" + "      },\n"
			+ "      \"main_photo\": \"http://thecatapi.com/api/images/get?format=src&type=gif\",\n"
			+ "      \"compatibility_score\": 0.93,\n"
			+ "      \"contacts_exchanged\": 8,\n"
			+ "      \"favourite\": true,\n"
			+ "      \"religion\": \"Atheist\"\n" + "    },\n" + "    {\n"
			+ "      \"display_name\": \"Samantha\",\n" + "      \"age\": 32,\n"
			+ "      \"job_title\": \"Project Manager\",\n"
			+ "      \"height_in_cm\": 161,\n" + "      \"city\": {\n"
			+ "        \"name\": \"Belfast\",\n"
			+ "        \"lat\": 54.607868,\n" + "        \"lon\": -5.926437\n"
			+ "      },\n"
			+ "      \"main_photo\": \"http://thecatapi.com/api/images/get?format=src&type=gif\",\n"
			+ "      \"compatibility_score\": 0.89,\n"
			+ "      \"contacts_exchanged\": 0,\n"
			+ "      \"favourite\": false,\n"
			+ "      \"religion\": \"Christian\"\n" + "    },\n" + "    {\n"
			+ "      \"display_name\": \"Elizabeth\",\n"
			+ "      \"age\": 41,\n" + "      \"job_title\": \"Dentist\",\n"
			+ "      \"height_in_cm\": 145,\n" + "      \"city\": {\n"
			+ "        \"name\": \"Londonderry\",\n"
			+ "        \"lat\": 55.006763,\n" + "        \"lon\": -7.318268\n"
			+ "      },\n"
			+ "      \"main_photo\": \"http://thecatapi.com/api/images/get?format=src&type=gif\",\n"
			+ "      \"compatibility_score\": 0.83,\n"
			+ "      \"contacts_exchanged\": 4,\n"
			+ "      \"favourite\": true,\n" + "      \"religion\": \"Islam\"\n"
			+ "    },\n" + "    {\n" + "      \"display_name\": \"Emma\",\n"
			+ "      \"age\": 40,\n" + "      \"job_title\": \"Banker\",\n"
			+ "      \"height_in_cm\": 150,\n" + "      \"city\": {\n"
			+ "        \"name\": \"Leeds\",\n" + "        \"lat\": 53.801277,\n"
			+ "        \"lon\": -1.548567\n" + "      },\n"
			+ "      \"main_photo\": \"http://thecatapi.com/api/images/get?format=src&type=gif\",\n"
			+ "      \"compatibility_score\": 0.73,\n"
			+ "      \"contacts_exchanged\": 0,\n"
			+ "      \"favourite\": false,\n"
			+ "      \"religion\": \"Christian\"\n" + "    },\n" + "    {\n"
			+ "      \"display_name\": \"Diana\",\n" + "      \"age\": 44,\n"
			+ "      \"job_title\": \"Consultant\",\n"
			+ "      \"height_in_cm\": 153,\n" + "      \"city\": {\n"
			+ "        \"name\": \"London\",\n"
			+ "        \"lat\": 51.509865,\n" + "        \"lon\": -0.118092\n"
			+ "      },\n"
			+ "      \"main_photo\": \"http://thecatapi.com/api/images/get?format=src&type=gif\",\n"
			+ "      \"compatibility_score\": 0.50,\n"
			+ "      \"contacts_exchanged\": 0,\n"
			+ "      \"favourite\": false,\n"
			+ "      \"religion\": \"Atheist\"\n" + "    },\n" + "    {\n"
			+ "      \"display_name\": \"Kysha\",\n" + "      \"age\": 45,\n"
			+ "      \"job_title\": \"Lawyer\",\n"
			+ "      \"height_in_cm\": 144,\n" + "      \"city\": {\n"
			+ "        \"name\": \"London\",\n"
			+ "        \"lat\": 51.509865,\n" + "        \"lon\": -0.118092\n"
			+ "      },\n" + "      \"compatibility_score\": 0.88,\n"
			+ "      \"contacts_exchanged\": 10,\n"
			+ "      \"favourite\": true,\n" + "      \"religion\": \"Islam\"\n"
			+ "    },\n" + "    {\n" + "      \"display_name\": \"Anne\",\n"
			+ "      \"age\": 38,\n"
			+ "      \"job_title\": \"Marketing Consultant\",\n"
			+ "      \"height_in_cm\": 170,\n" + "      \"city\": {\n"
			+ "        \"name\": \"Swindon\",\n"
			+ "        \"lat\": 51.568535,\n" + "        \"lon\": -1.772232\n"
			+ "      },\n"
			+ "      \"main_photo\": \"http://thecatapi.com/api/images/get?format=src&type=gif\",\n"
			+ "      \"compatibility_score\": 0.88,\n"
			+ "      \"contacts_exchanged\": 0,\n"
			+ "      \"favourite\": false,\n"
			+ "      \"religion\": \"Jewish\"\n" + "    },\n" + "    {\n"
			+ "      \"display_name\": \"Daniela\",\n" + "      \"age\": 37,\n"
			+ "      \"job_title\": \"Doctor\",\n"
			+ "      \"height_in_cm\": 177,\n" + "      \"city\": {\n"
			+ "        \"name\": \"Bournemouth\",\n"
			+ "        \"lat\": 50.720806,\n" + "        \"lon\": -1.904755\n"
			+ "      },\n"
			+ "      \"main_photo\": \"http://thecatapi.com/api/images/get?format=src&type=gif\",\n"
			+ "      \"compatibility_score\": 0.76,\n"
			+ "      \"contacts_exchanged\": 0,\n"
			+ "      \"favourite\": false,\n"
			+ "      \"religion\": \"Christian\"\n" + "    },\n" + "    {\n"
			+ "      \"display_name\": \"Katherine\",\n"
			+ "      \"age\": 39,\n" + "      \"job_title\": \"Lawyer\",\n"
			+ "      \"height_in_cm\": 177,\n" + "      \"city\": {\n"
			+ "        \"name\": \"London\",\n"
			+ "        \"lat\": 51.509865,\n" + "        \"lon\": -0.118092\n"
			+ "      },\n"
			+ "      \"main_photo\": \"http://thecatapi.com/api/images/get?format=src&type=gif\",\n"
			+ "      \"compatibility_score\": 0.99,\n"
			+ "      \"contacts_exchanged\": 50,\n"
			+ "      \"favourite\": true,\n"
			+ "      \"religion\": \"Atheist\"\n" + "    },\n" + "    {\n"
			+ "      \"display_name\": \"Susan\",\n" + "      \"age\": 25,\n"
			+ "      \"job_title\": \"Project Manager\",\n"
			+ "      \"height_in_cm\": 166,\n" + "      \"city\": {\n"
			+ "        \"name\": \"Harlow\",\n"
			+ "        \"lat\": 51.772938,\n" + "        \"lon\": 0.102310\n"
			+ "      },\n"
			+ "      \"main_photo\": \"http://thecatapi.com/api/images/get?format=src&type=gif\",\n"
			+ "      \"compatibility_score\": 0.88,\n"
			+ "      \"contacts_exchanged\": 0,\n"
			+ "      \"favourite\": false,\n"
			+ "      \"religion\": \"Christian\"\n" + "    }\n" + "  ]\n" + "}";
	LoadData loadData = new LoadData();

	private Connection con = null;;

	@Before
	public void setUp() throws Exception {
		con = loadData.createConnection();
	}

	@Test
	public void shouldVerifyIfTableStructureHasBeenCreated() throws Exception {

		assertTrue(loadData.createDDL(con));

	}

	@Test
	public void shouldVerifyIfJsonHasBeenConvertedIntoListOfObjects()
			throws Exception {

		List<User> allUsers = loadData.parseJsonIntoMatchObjects(JSON_DATA);
		assertNotNull(allUsers);
		verifyTheSecondMatch(allUsers);
	}

	private void verifyTheSecondMatch(List<User> allUsers) {
		assertEquals(allUsers.size(), 25);
		User match = allUsers.get(1);
		assertNotNull(allUsers);
		assertEquals("Solihull", match.getCity().getName());
	}

	@Test
	public void shouldInsertUsersAndVerifyTheMatchesForCurrentUserWithNoFilters()
			throws Exception {
		User currentUser = setUpDataForFilteringTestsAndReturnFirstUser();

		List<User> matches = loadData.retrieveMatchesForCurrentUser(con,
				currentUser, null);
		assertEquals(24, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyTheMatchesWithNoPhotoForCurrentUser()
			throws Exception {
		User currentUser = setUpDataForFilteringTestsAndReturnFirstUser();

		LinkedHashMap<String, String> filters = new LinkedHashMap<String, String>();

		filters.put("has_photo", "no");
		List<User> matches = loadData.retrieveMatchesForCurrentUser(con,
				currentUser, filters);
		assertEquals(3, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyTheMatchesWithPhotoForCurrentUser()
			throws Exception {
		User currentUser = setUpDataForFilteringTestsAndReturnFirstUser();

		LinkedHashMap<String, String> filters = new LinkedHashMap<String, String>();

		filters.put("has_photo", "yes");
		List<User> matches = loadData.retrieveMatchesForCurrentUser(con,
				currentUser, filters);
		assertEquals(21, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyTheMatchesWithNoContactsExchangedForCurrentUser()
			throws Exception {
		User currentUser = setUpDataForFilteringTestsAndReturnFirstUser();

		LinkedHashMap<String, String> filters = new LinkedHashMap<String, String>();

		filters.put("has_exchanged_contacts", "no");
		List<User> matches = loadData.retrieveMatchesForCurrentUser(con,
				currentUser, filters);
		assertEquals(13, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyTheMatchesWithContactsExchangedForCurrentUser()
			throws Exception {
		User currentUser = setUpDataForFilteringTestsAndReturnFirstUser();

		LinkedHashMap<String, String> filters = new LinkedHashMap<String, String>();

		filters.put("has_exchanged_contacts", "yes");
		List<User> matches = loadData.retrieveMatchesForCurrentUser(con,
				currentUser, filters);
		assertEquals(11, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWhichAreNotFavoriteForCurrentUser()
			throws Exception {
		User currentUser = setUpDataForFilteringTestsAndReturnFirstUser();

		LinkedHashMap<String, String> filters = new LinkedHashMap<String, String>();

		filters.put("is_favorite", "no");
		List<User> matches = loadData.retrieveMatchesForCurrentUser(con,
				currentUser, filters);
		assertEquals(19, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWhichAreFavoriteForCurrentUser()
			throws Exception {
		User currentUser = setUpDataForFilteringTestsAndReturnFirstUser();

		LinkedHashMap<String, String> filters = new LinkedHashMap<String, String>();

		filters.put("is_favorite", "yes");
		List<User> matches = loadData.retrieveMatchesForCurrentUser(con,
				currentUser, filters);
		assertEquals(5, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWithCompatibilityScoreRangeForCurrentUser()
			throws Exception {
		User currentUser = setUpDataForFilteringTestsAndReturnFirstUser();

		LinkedHashMap<String, String> filters = new LinkedHashMap<String, String>();

		filters.put("lower_limit_compatibility", "0.75");
		filters.put("upper_limit_compatibility", "1.0");
		List<User> matches = loadData.retrieveMatchesForCurrentUser(con,
				currentUser, filters);
		assertEquals(21, matches.size());

		filters.put("lower_limit_compatibility", "0.01");
		filters.put("upper_limit_compatibility", "0.75");
		matches = loadData.retrieveMatchesForCurrentUser(con, currentUser,
				filters);
		assertEquals(3, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWithAgeFilterForCurrentUser()
			throws Exception {
		User currentUser = setUpDataForFilteringTestsAndReturnFirstUser();

		LinkedHashMap<String, String> filters = new LinkedHashMap<String, String>();

		filters.put("lower_limit_age", "18");
		filters.put("upper_limit_age", "35");
		List<User> matches = loadData.retrieveMatchesForCurrentUser(con,
				currentUser, filters);
		assertEquals(2, matches.size());

		filters.put("lower_limit_age", "36");
		filters.put("upper_limit_age", "95");
		matches = loadData.retrieveMatchesForCurrentUser(con, currentUser,
				filters);
		assertEquals(22, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWithHeightFilterForCurrentUser()
			throws Exception {
		User currentUser = setUpDataForFilteringTestsAndReturnFirstUser();

		LinkedHashMap<String, String> filters = new LinkedHashMap<String, String>();

		filters.put("lower_limit_height", "135");
		filters.put("upper_limit_height", "160");
		List<User> matches = loadData.retrieveMatchesForCurrentUser(con,
				currentUser, filters);
		assertEquals(15, matches.size());

		filters.put("lower_limit_height", "161");
		filters.put("upper_limit_height", "210");
		matches = loadData.retrieveMatchesForCurrentUser(con, currentUser,
				filters);
		assertEquals(9, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWithDistanceFilterForCurrentUser()
			throws Exception {
		User currentUser = setUpDataForFilteringTestsAndReturnFirstUser();

		LinkedHashMap<String, String> filters = new LinkedHashMap<String, String>();

		filters.put("distance_limit", "200");

		List<User> matches = loadData.retrieveMatchesForCurrentUser(con,
				currentUser, filters);
		assertEquals(16, matches.size());

		filters.put("distance_limit", "100");

		matches = loadData.retrieveMatchesForCurrentUser(con, currentUser,
				filters);
		assertEquals(2, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWithACombinationOfFilters()
			throws Exception {

		User currentUser = setUpDataForFilteringTestsAndReturnFirstUser();

		LinkedHashMap<String, String> filters = new LinkedHashMap<String, String>();

		filters.put("has_photo", "yes");
		filters.put("has_exchanged_contacts", "yes");
		filters.put("is_favorite", "yes");
		filters.put("lower_limit_height", "140");
		filters.put("upper_limit_height", "200");

		List<User> matches = loadData.retrieveMatchesForCurrentUser(con,
				currentUser, filters);
		assertEquals(3, matches.size());

	}

	@Test
	public void shouldInsertUsersAndVerifyMatchesWithAnotherCombinationOfFilters()
			throws Exception {

		User currentUser = setUpDataForFilteringTestsAndReturnFirstUser();

		LinkedHashMap<String, String> filters = new LinkedHashMap<String, String>();

		filters.put("has_photo", "yes");
		filters.put("has_exchanged_contacts", "yes");
		filters.put("is_favorite", "yes");
		filters.put("lower_limit_height", "140");
		filters.put("upper_limit_height", "200");

		filters.put("distance_limit", "200");

		List<User> matches = loadData.retrieveMatchesForCurrentUser(con,
				currentUser, filters);
		assertEquals(2, matches.size());

	}

	private User setUpDataForFilteringTestsAndReturnFirstUser()
			throws Exception {

		loadData.createDDL(con);
		List<User> allUsers = loadData.parseJsonIntoMatchObjects(JSON_DATA);
		loadData.insertUsers(con, allUsers);
		assertTrue(allUsers.size() > 1);
		User user = allUsers.get(0);
		user.setUserId(1);
		return user;
	}

}
