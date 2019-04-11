# filteredmatches
filtered matches for dating app

In order to run this you need to have maven installed.

Then navigate to the location of the pom.xml and run 

# mvn clean install

Once the filtered matches jar file is created then run the command. This will also run the tests before the jar is compiled

# mvn test

This will run the tests and generate a report

# mvn exec:java -Dexec.args="start"

This should start the app

Navigate to the url # <your_server_url>:8080/matches/<user_id> (eg: http://localhost:8080/matches/1)
where you can use 1 to 25 as one of the userIds

and the matches page should load.

It will load on your smart phone/ipad/etc. as long as it is in the same network ( for wifi you need to know your IP address and type that instead of localhost). The default results page loads without hte filter. YOu have to explicitly show/hide filter using the button on the top left corner

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

