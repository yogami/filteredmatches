# filteredmatches
filtered matches for dating app

In order to run this you need to have maven installed.

Then navigate to the location of the pom.xml and run 

# mvn clean install

Once the filtered matches jar file is created then run the command

# mvn exec:java -Dstart

This should start the app

Navigate to the url # <your_server_url>:8080/matches/caroline (eg: http://localhost:8080/matches/caroline)

and the matches page should load.

# mvn exec:java -Dstop 

from another window should stop the app
