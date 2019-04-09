package com.filteredmatches.data;

import java.util.LinkedHashMap;

import com.filteredmatches.FilterMappings;
import com.filteredmatches.model.User;

public class FilterMatchesSqlCreator {

	public static final String ID_PARAMETER = ":id";
	public static final String LATITUDE_PARAMETER = ":lat";
	public static final String LONGITUDE_PARAMETER = ":lon";
	public static final String LOWER_AGE_PARAMETER = ":lowerAge";
	public static final String UPPER_AGE_PARAMETER = ":upperAge";
	public static final String LOWER_HEIGHT_PARAMETER = ":lowerHeight";
	public static final String UPPER_HEIGHT_PARAMETER = ":upperHeight";
	public static final String LOWER_COMPATIBILITY_PARAMETER = ":lowerCompatibility";
	public static final String UPPER_COMPATIBILITY_PARAMETER = ":upperCOmpatibility";
	public static final String DISTANCE_PARAMETER = ":distance";

	private static final String SELECT_ALL_MATCHES_SQL = "SELECT *,( 3959 * acos( cos( radians("
			+ LATITUDE_PARAMETER + ") ) "
			+ "              * cos( radians( CITY_LAT ) ) "
			+ "              * cos( radians( CITY_LON ) - radians("
			+ LONGITUDE_PARAMETER + ") ) " + "              + sin( radians("
			+ LATITUDE_PARAMETER + ") ) "
			+ "              * sin( radians( CITY_LAT ) ) ) ) AS distance  FROM USERS"
			+ " WHERE ID != " + ID_PARAMETER;

	private static final String FILTER_WITH_AGE = " AND AGE >= "
			+ LOWER_AGE_PARAMETER + " AND AGE<=  " + UPPER_AGE_PARAMETER;
	private static final String FILTER_WITH_HEIGHT = " AND HEIGHT_IN_CM >= "
			+ LOWER_HEIGHT_PARAMETER + " AND HEIGHT_IN_CM <= "
			+ UPPER_HEIGHT_PARAMETER;
	private static final String FILTER_WITHCOMPATIBILITY_SCORE = " AND COMPATIBILITY_SCORE >= "
			+ LOWER_COMPATIBILITY_PARAMETER + " AND COMPATIBILITY_SCORE <= "
			+ UPPER_COMPATIBILITY_PARAMETER;
	private static final String FILTER_WITH_CONTACTS_EXCHANGED = " AND CONTACTS_EXCHANGED > 0";
	private static final String FILTER_WITHOUT_CONTACTS_EXCHANGED = " AND CONTACTS_EXCHANGED = 0";
	private static final String FILTER_WITH_PHOTO = " AND MAIN_PHOTO_URL is not null AND  MAIN_PHOTO_URL != '' ";
	private static final String FILTER_WITHOUT_PHOTO = " AND MAIN_PHOTO_URL is null or MAIN_PHOTO_URL = '' ";
	private static final String FILTER_WITH_FAVOURITES = " AND FAVOURITE = true";
	private static final String FILTER_WITHOUT_FAVOURITES = " AND FAVOURITE = false";
	private static final String FILTER_WITH_DISTANCE = " WHERE U.distance < "
			+ DISTANCE_PARAMETER;
	private static final String NO_FILTER_APPLIED = "";
	
	public String createFilterSql(User currentUser,LinkedHashMap<String, String> filters) {
		String selectSql = SELECT_ALL_MATCHES_SQL;
		selectSql = applyCurrentUserParameters(currentUser, selectSql);
		selectSql = applyFilters(filters, selectSql);
		return selectSql;
	}
	
	private String applyCurrentUserParameters(User currentUser,
			String selectSql) {
		selectSql = selectSql.replace(LATITUDE_PARAMETER,
				currentUser.getCity().getLat().toString());
		selectSql = selectSql.replace(LONGITUDE_PARAMETER,
				currentUser.getCity().getLon().toString());
		selectSql = selectSql.replace(ID_PARAMETER,
				currentUser.getUserId().toString());
		return selectSql;
	}

	private String applyFilters(LinkedHashMap<String, String> filters,
			String selectSql) {
		if (filters != null && filters.size() > 0) {
			selectSql = applyBooleanFilters(filters, selectSql);
			selectSql = applyRangeFilters(filters, selectSql);
			selectSql = formulateNewSqlFWithOuterTableorDistanceLimit(filters,
					selectSql);

		}
		return selectSql;
	}

	private String applyBooleanFilters(LinkedHashMap<String, String> filters,
			String selectSql) {
		selectSql += getFilteredSql(filters, FilterMappings.HAS_PHOTO_FILTER,
				FILTER_WITH_PHOTO, FILTER_WITHOUT_PHOTO);
		selectSql += getFilteredSql(filters,
				FilterMappings.HAS_CONTACTS_EXCHANGED,
				FILTER_WITH_CONTACTS_EXCHANGED,
				FILTER_WITHOUT_CONTACTS_EXCHANGED);
		selectSql += getFilteredSql(filters, FilterMappings.IS_FAVOURITE,
				FILTER_WITH_FAVOURITES, FILTER_WITHOUT_FAVOURITES);
		return selectSql;
	}

	private String getFilteredSql(LinkedHashMap<String, String> filters,
			String typeOfIlter, String sqlFilterWith, String sqlFilterWithout) {
		String hasFilter = filters.get(typeOfIlter);
		if (hasFilter != null) {
			if (hasFilter.equals("yes"))
				return sqlFilterWith;

			else
				return sqlFilterWithout;
		}

		else
			return NO_FILTER_APPLIED;
	}

	private String applyRangeFilters(LinkedHashMap<String, String> filters,
			String selectSql) {
		selectSql = getRangeSql(filters, selectSql,
				FilterMappings.LOWER_LIMIT_COMPATIBILITY,
				FilterMappings.UPPER_LIMIT_COMPATIBILITY,
				FILTER_WITHCOMPATIBILITY_SCORE, LOWER_COMPATIBILITY_PARAMETER,
				UPPER_COMPATIBILITY_PARAMETER);
		selectSql = getRangeSql(filters, selectSql,
				FilterMappings.LOWER_LIMIT_AGE, FilterMappings.UPPER_LIMIT_AGE,
				FILTER_WITH_AGE, LOWER_AGE_PARAMETER, UPPER_AGE_PARAMETER);
		selectSql = getRangeSql(filters, selectSql,
				FilterMappings.LOWER_LIMIT_HEIGHT,
				FilterMappings.UPPER_LIMIT_HEIGHT, FILTER_WITH_HEIGHT,
				LOWER_HEIGHT_PARAMETER, UPPER_HEIGHT_PARAMETER);
		return selectSql;
	}

	private String getRangeSql(LinkedHashMap<String, String> filters,
			String selectSql, String lowerLimitFilter, String upperLimitFilter,
			String rangeFilterSql, String lowerLimitParameter,
			String upperLimitParameter) {
		String hasFilter = filters.get(lowerLimitFilter);
		if (hasFilter != null) {

			selectSql += rangeFilterSql;
			selectSql = selectSql.replace(lowerLimitParameter,
					filters.get(lowerLimitFilter));
			selectSql = selectSql.replace(upperLimitParameter,
					filters.get(upperLimitFilter));

		}

		return selectSql;
	}

	private String formulateNewSqlFWithOuterTableorDistanceLimit(
			LinkedHashMap<String, String> filters, String currentSql) {

		String hasDistanceFilter = filters.get(FilterMappings.DISTANCE_LIMIT);
		if (hasDistanceFilter != null) {

			currentSql = "SELECT * FROM (" + currentSql + ") U";
			currentSql += FILTER_WITH_DISTANCE;
			currentSql = currentSql.replace(DISTANCE_PARAMETER,
					filters.get(FilterMappings.DISTANCE_LIMIT));

		}

		return currentSql;

	}
}
