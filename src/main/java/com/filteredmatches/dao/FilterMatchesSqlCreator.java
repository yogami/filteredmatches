package com.filteredmatches.dao;

import org.h2.util.StringUtils;
import org.springframework.stereotype.Component;

import com.filteredmatches.dto.FilterDTO;
import com.filteredmatches.model.User;

@Component("filtermatchesSqlCreator")
public class FilterMatchesSqlCreator {

	private static final String ID_PARAMETER = ":id";
	private static final String LATITUDE_PARAMETER = ":lat";
	private static final String LONGITUDE_PARAMETER = ":lon";
	private static final String LOWER_AGE_PARAMETER = ":lowerAge";
	private static final String UPPER_AGE_PARAMETER = ":upperAge";
	private static final String LOWER_HEIGHT_PARAMETER = ":lowerHeight";
	private static final String UPPER_HEIGHT_PARAMETER = ":upperHeight";
	private static final String LOWER_COMPATIBILITY_PARAMETER = ":lowerCompatibility";
	private static final String UPPER_COMPATIBILITY_PARAMETER = ":upperCOmpatibility";
	private static final String DISTANCE_PARAMETER = ":distance";

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
	private static final String FILTER_WITHOUT_PHOTO = " AND (MAIN_PHOTO_URL is null or MAIN_PHOTO_URL = '') ";
	private static final String FILTER_WITH_FAVOURITES = " AND FAVOURITE = TRUE";
	private static final String FILTER_WITHOUT_FAVOURITES = " AND FAVOURITE = FALSE";
	private static final String FILTER_WITH_DISTANCE_LESS_THAN = " WHERE U.distance < "+ DISTANCE_PARAMETER;
			
	private static final String NO_FILTER_APPLIED = "";

	public String createFilterSql(User currentUser, FilterDTO filterDTO) {
		String selectSql = SELECT_ALL_MATCHES_SQL;
		selectSql = applyCurrentUserParameters(currentUser, selectSql);
		selectSql = applyFilters(filterDTO, selectSql);
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

	private String applyFilters(FilterDTO filterDTO, String selectSql) {
		if (filterDTO != null) {
			selectSql = applyBooleanFilters(filterDTO, selectSql);
			selectSql = applyRangeFilters(filterDTO, selectSql);
			selectSql = formulateNewSqlFWithOuterTableonDistanceLimit(filterDTO,
					selectSql);

		}
		return selectSql;
	}

	private String applyBooleanFilters(FilterDTO filterDTO, String selectSql) {
		selectSql += getFilteredSql(filterDTO.getHasPhoto(), FILTER_WITH_PHOTO,
				FILTER_WITHOUT_PHOTO);
		selectSql += getFilteredSql(filterDTO.getHasContactsExchanged(),
				FILTER_WITH_CONTACTS_EXCHANGED,
				FILTER_WITHOUT_CONTACTS_EXCHANGED);
		selectSql += getFilteredSql(filterDTO.getIsFavourite(),
				FILTER_WITH_FAVOURITES, FILTER_WITHOUT_FAVOURITES);
		return selectSql;
	}

	private String getFilteredSql(String booleanFilter, String sqlFilterWith,
			String sqlFilterWithout) {

		if (!StringUtils.isNullOrEmpty(booleanFilter)) {
			if (booleanFilter.equals("yes"))
				return sqlFilterWith;

			else
				return sqlFilterWithout;
		}

		else
			return NO_FILTER_APPLIED;
	}

	private String applyRangeFilters(FilterDTO filterDTO, String selectSql) {
		selectSql = getRangeSql(selectSql,
				filterDTO.getLowerLimitCompatibility(),
				filterDTO.getUpperLimitCompatibility(),
				FILTER_WITHCOMPATIBILITY_SCORE, LOWER_COMPATIBILITY_PARAMETER,
				UPPER_COMPATIBILITY_PARAMETER);
		selectSql = getRangeSql(selectSql,
				filterDTO.getLowerLimitAge(), filterDTO.getUpperLimitAge(),
				FILTER_WITH_AGE, LOWER_AGE_PARAMETER, UPPER_AGE_PARAMETER);
		selectSql = getRangeSql( selectSql,
				filterDTO.getLowerLimitHeight(),
				filterDTO.getUpperLimitHeight(), FILTER_WITH_HEIGHT,
				LOWER_HEIGHT_PARAMETER, UPPER_HEIGHT_PARAMETER);
		return selectSql;
	}

	private String getRangeSql( String selectSql,
			String lowerLimit, String upperLimit, String rangeFilterSql,
			String lowerLimitParameter, String upperLimitParameter) {

		if (lowerLimit != null) {

			selectSql += rangeFilterSql;
			selectSql = selectSql.replace(lowerLimitParameter, lowerLimit);
			selectSql = selectSql.replace(upperLimitParameter, upperLimit);

		}

		return selectSql;
	}

	private String formulateNewSqlFWithOuterTableonDistanceLimit(
			FilterDTO filterDTO, String currentSql) {

		String distanceLimit = filterDTO.getDistanceLimit();
		if (distanceLimit != null) {

			currentSql = "SELECT * FROM (" + currentSql + ") U";
			currentSql += FILTER_WITH_DISTANCE_LESS_THAN;
			currentSql = currentSql.replace(DISTANCE_PARAMETER, distanceLimit);

		}

		return currentSql;

	}
}
