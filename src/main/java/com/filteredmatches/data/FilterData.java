package com.filteredmatches.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.filteredmatches.dto.FilterDTO;
import com.filteredmatches.model.City;
import com.filteredmatches.model.User;

public class FilterData extends BaseData {

	private FilterMatchesSqlCreator filterMatchesSqlCreator = new FilterMatchesSqlCreator();

	public List<User> retrieveMatchesForCurrentUser(User currentUser,
			FilterDTO filterDTO) throws Exception {

		String selectSql = filterMatchesSqlCreator.createFilterSql(currentUser,
				filterDTO);
		return runMatchesQuery(selectSql);

	}

	private List<User> runMatchesQuery(String selectSql) throws SQLException {
		List<User> users = new ArrayList<User>();

		PreparedStatement ps = con.prepareStatement(selectSql);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			User user = populateMatch(rs);
			users.add(user);
		}

		return users;
	}

	private User populateMatch(ResultSet rs) throws SQLException {
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

}
