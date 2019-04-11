package com.filteredmatches.dao;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.filteredmatches.model.User;
import com.filteredmatches.model.Users;
import com.google.gson.Gson;

@Component("readJson")
public class ReadJsonData implements IReadSourceData{
	
	//TODO: the file name is hard coded here. It should be passed to this class from elsewhere so that 
	//so that this class becomes rusable and is not bound to a particular json file.
	private static final String JSON_FILE_NAME = "users.json";
	public List<User> getUserListFromSpecifiedSource() throws Exception {

		StringBuilder result = new StringBuilder("");

		// Get file from resources folder
		ClassLoader classLoader = getClass().getClassLoader();

		File file = new File(classLoader.getResource(JSON_FILE_NAME).getFile());

		Scanner scanner = new Scanner(file);

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			result.append(line).append("\n");
		}

		scanner.close();

		return parseJsonIntoMatchObjects(result.toString());

	}

	private List<User> parseJsonIntoMatchObjects(String jsonData) {
		Users users = new Gson().fromJson(jsonData, Users.class);
		return users.getMatches();

	}

}
