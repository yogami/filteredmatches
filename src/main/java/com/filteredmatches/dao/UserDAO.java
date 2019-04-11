package com.filteredmatches.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.filteredmatches.model.City;
import com.filteredmatches.model.User;

@Repository("userData")
public class UserDAO extends BaseDAO implements IUserDAO {
	
	private static final String SELECT_CURRENT_USER_SQL = "SELECT * FROM USERS WHERE ID = ?";
	
	

	public User retrieveCurrentuser(Integer userId) throws SQLException {
		String selectSql = SELECT_CURRENT_USER_SQL;
		User currentUser = null;
        PreparedStatement ps = con.prepareStatement(selectSql);
		ps.setInt(1, userId);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			currentUser = populateUser(rs);

		}

		return currentUser;
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
}
