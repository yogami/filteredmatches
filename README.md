# filteredmatches
filtered matches for dating app

In order to run this you need to have maven installed.

Then navigate to the location of the pom.xml and run 

# mvn clean install

Once the filtered matches jar file is created then run the command

# mvn exec:java -Dexec.args="start"

This should start the app

Navigate to the url # <your_server_url>:8080/matches/<user_id> (eg: http://localhost:8080/matches/1)
where you can use 1 to 25 as one of the userIds

and the matches page should load.

# Cntrl C

on that window should stop the server


# TODO list
//get double range slider working

//clarify range requirement


//configure hibernate and then the appropriate design pattern for filtering (criteria + restrictions) This would be much cleaner than what is now

//configure logger

//load hardcoded values from properties file

//decouple db so that it is easily replaceable

//custom exceptions

