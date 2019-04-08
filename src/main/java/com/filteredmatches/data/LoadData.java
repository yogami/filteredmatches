package com.filteredmatches.data;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

import com.filteredmatches.model.User;
import com.filteredmatches.model.Users;
import com.google.gson.Gson;

public class LoadData {
	
	private static final String JSON_FILE_NAME = "users.json";

	private static final String CREATE_TABLE_SQL = "create table USERS(ID IDENTITY , DISPLAY_NAME NVARCHAR(64) , "
			+ "AGE INT , JOB_TITLE VARCHAR(256) , HEIGHT_IN_CM INT , CITY_NAME NVARCHAR(64) , "
			+ "CITY_LAT FLOAT , CITY_LON FLOAT , MAIN_PHOTO_URL VARCHAR(2048) , "
			+ "COMPATIBILITY_SCORE FLOAT , CONTACTS_EXCHANGED INT , FAVOURITE BOOLEAN, "
			+ "RELIGION VARCHAR(256)) ";

	private static final String INSERT_SQL = "insert into USERS(DISPLAY_NAME, AGE, JOB_TITLE, HEIGHT_IN_CM, CITY_NAME, "
			+ "CITY_LAT, CITY_LON, MAIN_PHOTO_URL, COMPATIBILITY_SCORE, CONTACTS_EXCHANGED, "
			+ "FAVOURITE, RELIGION) VALUES(?,?,?,?,?,?,?,?,?,?,?,?) ";

	private static final String SELECT_ALL_USERS_SQL = "SELECT * FROM  USERS";

	private Connection con;

	public LoadData(ConnectionPool cpool) {
		try {
			con = cpool.getConnection();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public boolean initialieData() {
		String jsonString = getFile(JSON_FILE_NAME);
		List<User> users = this.parseJsonIntoMatchObjects(jsonString);
		boolean success = createDDL();
		if(success)
		  success = this.insertUsers(users);
		return success;
	}

	public String getFile(String fileName) {

		StringBuilder result = new StringBuilder("");

		// Get file from resources folder
		ClassLoader classLoader = this.getClass().getClassLoader();

		try {

			File file = new File(classLoader.getResource(fileName).getFile());

			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}

			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result.toString();

	}

	public boolean createDDL() {

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

	public boolean insertUsers(List<User> users) {

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
			return false;

		}
		
		return true;

	}

}
