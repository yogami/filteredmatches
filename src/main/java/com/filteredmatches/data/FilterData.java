package com.filteredmatches.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.filteredmatches.model.City;
import com.filteredmatches.model.User;

public class FilterData {

	private static final String SELECT_CURRENT_USER_SQL = "SELECT * FROM USERS WHERE ID = ?";

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

	private Connection con;

	public FilterData(ConnectionPool cpool) {
		try {
			con = cpool.getConnection();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public List<User> retrieveMatchesForCurrentUser(User currentUser,
			LinkedHashMap<String, String> filters) {
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
				User user = populateMatch(rs);
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
		return user;
	}
	
	private User populateMatch(ResultSet rs) throws SQLException {
		User user = populateUser(rs);
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

	private String getHeightLimitRangeSql(LinkedHashMap<String, String> filters,
			String selectSql) {

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

			currentSql = "SELECT * FROM (" + currentSql + ") U";
			currentSql += FILTER_WITH_DISTANCE;
			currentSql = currentSql.replace(":distance",
					filters.get("distance_limit"));

		}

		return currentSql;

	}

	public User retrieveCurrentuser(Integer userId) {
		String selectSql = SELECT_CURRENT_USER_SQL;
		User currentUser = null;

		try {

			PreparedStatement ps = con.prepareStatement(selectSql);
			ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
				currentUser = populateUser(rs);

			}
		} catch (Exception ex) {
			ex.printStackTrace();

		}

		return currentUser;
	}

}
