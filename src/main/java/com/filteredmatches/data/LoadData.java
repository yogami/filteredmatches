package com.filteredmatches.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.filteredmatches.model.City;
import com.filteredmatches.model.User;
import com.filteredmatches.model.Users;
import com.google.gson.Gson;

public class LoadData {

	String url = "jdbc:h2:mem:";

	private static final String CREATE_TABLE_SQL = "create table USERS(ID IDENTITY , DISPLAY_NAME NVARCHAR(64) , "
			+ "AGE INT , JOB_TITLE VARCHAR(256) , HEIGHT_IN_CM INT , CITY_NAME NVARCHAR(64) , "
			+ "CITY_LAT FLOAT , CITY_LON FLOAT , MAIN_PHOTO_URL VARCHAR(2048) , "
			+ "COMPATIBILITY_SCORE FLOAT , CONTACTS_EXCHANGED INT , FAVOURITE BOOLEAN, "
			+ "RELIGION VARCHAR(256)) ";

	private static final String INSERT_SQL = "insert into USERS(DISPLAY_NAME, AGE, JOB_TITLE, HEIGHT_IN_CM, CITY_NAME, "
			+ "CITY_LAT, CITY_LON, MAIN_PHOTO_URL, COMPATIBILITY_SCORE, CONTACTS_EXCHANGED, "
			+ "FAVOURITE, RELIGION) VALUES(?,?,?,?,?,?,?,?,?,?,?,?) ";

	private static final String SELECT_ALL_USERS_SQL = "SELECT * FROM  USERS";

	private static final String SELECT_ALL_MATCHES_SQL = "SELECT *,( 3959 * acos( cos( radians(:lat) ) "
			+ "              * cos( radians( CITY_LAT ) ) "
			+ "              * cos( radians( CITY_LON ) - radians(:lon) ) "
			+ "              + sin( radians(:lat) ) "
			+ "              * sin( radians( CITY_LAT ) ) ) ) AS distance  FROM USERS"
			+ " WHERE ID != :id";

	private static final String FILTER_WITH_AGE = " AND AGE >= :lowerAge AND AGE<= :upperAge ";
	private static final String FILTER_WITH_HEIGHT = " AND HEIGHT_IN_CM >= :lowerHeight AND HEIGHT_IN_CM <= :upperHeight";
	private static final String FILTER_WITHCOMPATIBILITY_SCORE = " AND COMPATIBILITY_SCORE >= :lowerCompatibility AND COMPATIBILITY_SCORE <=:upperCompatibility";
	private static final String FILTER_WITH_CONTACTS_EXCHANGED = " AND CONTACTS_EXCHANGED > 0";
	private static final String FILTER_WITHOUT_CONTACTS_EXCHANGED = " AND CONTACTS_EXCHANGED = 0";
	private static final String FILTER_WITH_PHOTO = " AND MAIN_PHOTO_URL is not null AND  MAIN_PHOTO_URL != '' ";
	private static final String FILTER_WITHOUT_PHOTO = " AND MAIN_PHOTO_URL is null or MAIN_PHOTO_URL = '' ";
	private static final String FILTER_WITH_FAVOURITES = " AND FAVOURITE = true";
	private static final String FILTER_WITHOUT_FAVOURITES = " AND FAVOURITE = false";
	private static final String FILTER_WITH_DISTANCE = " WHERE U.distance < :distance";

	public Connection createConnection() throws Exception {
		return DriverManager.getConnection(url);
	}

	public boolean createDDL(Connection con) {

		try {

			Statement st = con.createStatement();
			st.executeUpdate(CREATE_TABLE_SQL);

			ResultSet rs = st.executeQuery(SELECT_ALL_USERS_SQL);
			ResultSetMetaData rsmd = rs.getMetaData();
			return rsmd.getColumnCount() > 1;

		} catch (SQLException ex) {

			ex.printStackTrace();
			return false;
		}

	}

	public List<User> parseJsonIntoMatchObjects(String jsonData) {
		Users users = new Gson().fromJson(jsonData, Users.class);
		return users.getMatches();

	}

	public void insertUsers(Connection con, List<User> users) {

		try {

			PreparedStatement ps = con.prepareStatement(INSERT_SQL);
			for (User user : users) {
				ps.setString(1, user.getDisplay_name());
				ps.setShort(2, user.getAge());
				ps.setString(3, user.getJob_title());
				ps.setShort(4, user.getHeight_in_cm());
				ps.setString(5, user.getCity().getName());
				ps.setFloat(6, user.getCity().getLat());
				ps.setFloat(7, user.getCity().getLon());
				ps.setString(8, user.getMain_photo());
				ps.setFloat(9, user.getCompatibility_score());
				ps.setShort(10, user.getContacts_exchanged());
				ps.setBoolean(11, user.isFavourite());
				ps.setString(12, user.getReligion());
				ps.execute();

			}
		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}

	public List<User> retrieveMatchesForCurrentUser(Connection con,
			User currentUser, LinkedHashMap<String, String> filters) {
		String selectSql = SELECT_ALL_MATCHES_SQL;
		selectSql = selectSql.replace(":lat",
				currentUser.getCity().getLat().toString());
		selectSql = selectSql.replace(":lon",
				currentUser.getCity().getLon().toString());
		selectSql = selectSql.replace(":id",
				currentUser.getUserId().toString());

		if (filters != null && filters.size() > 0) {
			selectSql += getPhotoFilterSql(filters);
			selectSql += getContactsExchangedFilterSql(filters);
			selectSql += getFavoritesFilterSql(filters);
			selectSql = getCompatibilityScoreRangeSql(filters, selectSql);
			selectSql = getAgeLimitRangeSql(filters, selectSql);
			selectSql = getHeightLimitRangeSql(filters, selectSql);
			selectSql = formulateNewSqlFWithOuterTableorDistanceLimit(filters,
					selectSql);

		}
		List<User> users = new ArrayList<User>();
		try {

			PreparedStatement ps = con.prepareStatement(selectSql);
			

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				User user = populateUser(rs);
				users.add(user);

			}
		} catch (Exception ex) {
			ex.printStackTrace();

		}

		return users;

	}

	private User populateUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setUserId(rs.getInt(1));
		user.setDisplay_name(rs.getString(2));
		user.setAge(rs.getShort(3));
		user.setJob_title(rs.getString(4));
		user.setHeight_in_cm(rs.getShort(5));
		City city = new City();
		city.setName(rs.getString(6));
		city.setLat(rs.getFloat(7));
		city.setLon(rs.getFloat(8));
		user.setCity(city);
		user.setMain_photo(rs.getString(9));
		user.setCompatibility_score(rs.getFloat(10));
		user.setContacts_exchanged(rs.getShort(11));
		user.setFavourite(rs.getBoolean(12));
		user.setReligion(rs.getString(13));
		user.setDistanceInKms(rs.getFloat(14));
		return user;
	}

	private String getPhotoFilterSql(LinkedHashMap<String, String> filters) {

		String hasPhoto = filters.get("has_photo");
		if (hasPhoto != null) {
			if (hasPhoto.equals("yes"))
				return FILTER_WITH_PHOTO;

			else
				return FILTER_WITHOUT_PHOTO;
		}

		return "";

	}

	private String getContactsExchangedFilterSql(
			LinkedHashMap<String, String> filters) {

		String hasContactsExchangedFilter = filters
				.get("has_exchanged_contacts");
		if (hasContactsExchangedFilter != null) {
			if (hasContactsExchangedFilter.equals("yes"))
				return FILTER_WITH_CONTACTS_EXCHANGED;

			else
				return FILTER_WITHOUT_CONTACTS_EXCHANGED;
		}

		return "";

	}

	private String getFavoritesFilterSql(
			LinkedHashMap<String, String> filters) {

		String hasFavoritesFilter = filters.get("is_favorite");
		if (hasFavoritesFilter != null) {
			if (hasFavoritesFilter.equals("yes"))
				return FILTER_WITH_FAVOURITES;

			else
				return FILTER_WITHOUT_FAVOURITES;
		}

		return "";

	}

	private String getCompatibilityScoreRangeSql(
			LinkedHashMap<String, String> filters, String selectSql) {

		String hasCompatibilityScoreFilter = filters
				.get("lower_limit_compatibility");
		if (hasCompatibilityScoreFilter != null) {

			selectSql += FILTER_WITHCOMPATIBILITY_SCORE;
			selectSql = selectSql.replace(":lowerCompatibility",
					filters.get("lower_limit_compatibility"));
			selectSql = selectSql.replace(":upperCompatibility",
					filters.get("upper_limit_compatibility"));

		}

		return selectSql;

	}

	private String getAgeLimitRangeSql(LinkedHashMap<String, String> filters,
			String selectSql) {

		String hasAgeFilter = filters.get("lower_limit_age");
		if (hasAgeFilter != null) {

			selectSql += FILTER_WITH_AGE;
			selectSql = selectSql.replace(":lowerAge",
					filters.get("lower_limit_age"));
			selectSql = selectSql.replace(":upperAge",
					filters.get("upper_limit_age"));

		}

		return selectSql;

	}

	private String getHeightLimitRangeSql(
			LinkedHashMap<String, String> filters, String selectSql) {

		String hasHeightFilter = filters.get("lower_limit_height");
		if (hasHeightFilter != null) {

			
			selectSql += FILTER_WITH_HEIGHT;
			selectSql = selectSql.replace(":lowerHeight",
					filters.get("lower_limit_height"));
			selectSql = selectSql.replace(":upperHeight",
					filters.get("upper_limit_height"));

		}

		return selectSql;

	}

	private String formulateNewSqlFWithOuterTableorDistanceLimit(
			LinkedHashMap<String, String> filters, String currentSql) {

		String hasDistanceFilter = filters.get("distance_limit");
		if (hasDistanceFilter != null) {

			currentSql= "SELECT * FROM (" + currentSql + ") U";
			currentSql+=FILTER_WITH_DISTANCE;
			currentSql = currentSql.replace(":distance",
					filters.get("distance_limit"));

		}

		return currentSql;

	}

}
