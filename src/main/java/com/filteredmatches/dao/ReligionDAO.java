package com.filteredmatches.dao;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class ReligionDAO extends BaseDAO implements IReligionDAO {

	private static final String SELECT_RELIGION_SQL = "SELECT DISTINCT RELIGION FROM USERS";

	public List<String> getReligions() throws Exception {
		List<String> religions = new ArrayList<String>();
		try (PreparedStatement ps = con.prepareStatement(SELECT_RELIGION_SQL);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				religions.add(rs.getString(1));

			}
		}
		return religions;

	}

}
