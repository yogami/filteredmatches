package com.filteredmatches.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.filteredmatches.model.User;

@Repository("loadData")
public class LoadData extends BaseData implements ILoadData {

	private static final String CREATE_TABLE_SQL = "create table USERS(ID IDENTITY , DISPLAY_NAME NVARCHAR(64) , "
			+ "AGE INT , JOB_TITLE VARCHAR(256) , HEIGHT_IN_CM INT , CITY_NAME NVARCHAR(64) , "
			+ "CITY_LAT FLOAT , CITY_LON FLOAT , MAIN_PHOTO_URL VARCHAR(2048) , "
			+ "COMPATIBILITY_SCORE FLOAT , CONTACTS_EXCHANGED INT , FAVOURITE BOOLEAN, "
			+ "RELIGION VARCHAR(256)) ";

	private static final String INSERT_SQL = "insert into USERS(DISPLAY_NAME, AGE, JOB_TITLE, HEIGHT_IN_CM, CITY_NAME, "
			+ "CITY_LAT, CITY_LON, MAIN_PHOTO_URL, COMPATIBILITY_SCORE, CONTACTS_EXCHANGED, "
			+ "FAVOURITE, RELIGION) VALUES(?,?,?,?,?,?,?,?,?,?,?,?) ";

	private static final String SELECT_ALL_USERS_SQL = "SELECT * FROM  USERS";

	private static final String DROP_TABLE_USERS = "DROP TABLE USERS";
	
	

	@Autowired
	@Qualifier("readJson")
	private IReadSourceData readJsonData;
	// TODO: separate reading JSON, creating DDL and inserting DDL

	public boolean initializeData() throws Exception {

		List<User> users = readJsonData.getUserListFromSpecifiedSource();
		boolean success = createDDL();
		if (success)
			success = insertUsers(users);
		return success;
	}

	private boolean createDDL() throws Exception {

		Statement st = con.createStatement();
		
		
		try {
			 st.executeQuery(SELECT_ALL_USERS_SQL);
			 return false;
		} catch (Exception ex) {
			 //table doesn't exist so go ahead and create below
		}
		st.executeUpdate(CREATE_TABLE_SQL);

		ResultSet rs  = st.executeQuery(SELECT_ALL_USERS_SQL);
		ResultSetMetaData rsmd = rs.getMetaData();
		return rsmd.getColumnCount() > 1;

	}

	private boolean insertUsers(List<User> users) throws Exception {

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
			ps.setBoolean(11, user.getFavourite());
			ps.setString(12, user.getReligion());
			ps.execute();
		}

		return true;

	}

	public void deleteTable() throws Exception {
		PreparedStatement ps = con.prepareStatement(DROP_TABLE_USERS);
		ps.execute();

	}

}
