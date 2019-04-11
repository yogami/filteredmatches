package com.filteredmatches.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.filteredmatches.dto.FilterDTO;
import com.filteredmatches.dto.MatchDTO;
import com.filteredmatches.model.User;


@Repository("filterData")
public class FilterData extends BaseData implements IFilterData {

	@Autowired
	@Qualifier("filtermatchesSqlCreator")
	private FilterMatchesSqlCreator filterMatchesSqlCreator;
	
	public List<MatchDTO> retrieveMatchesForCurrentUser(User currentUser,
			FilterDTO filterDTO) throws Exception {

		String selectSql = filterMatchesSqlCreator.createFilterSql(currentUser,
				filterDTO);
		return runMatchesQuery(selectSql);

	}

	private List<MatchDTO> runMatchesQuery(String selectSql)
			throws SQLException {
		List<MatchDTO> users = new ArrayList<MatchDTO>();

		PreparedStatement ps = con.prepareStatement(selectSql);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			MatchDTO match = populateMatch(rs);
			users.add(match);
		}

		return users;
	}

	private MatchDTO populateMatch(ResultSet rs) throws SQLException {
		MatchDTO match = new MatchDTO();

		match.setDisplay_name(rs.getString(2));
		match.setAge(rs.getShort(3));
		match.setJob_title(rs.getString(4));
		match.setHeight_in_cm(rs.getShort(5));
		match.setCity_name(rs.getString(6));

		match.setMain_photo(rs.getString(9));
		match.setCompatibility_score(
				Float.valueOf(100 * rs.getFloat(10)).intValue());
		match.setContacts_exchanged(rs.getShort(11));
		match.setFavourite(rs.getBoolean(12));
		match.setReligion(rs.getString(13));
		match.setDistanceInKms(Float.valueOf(rs.getFloat(14)).intValue());
		return match;
	}

}
