package com.filteredmatches;
import com.filteredmatches.server.EmbeddedJettyServer;
import com.filteredmatches.service.InitialDataSetupService;

public class MainApp {
	
	public static void main(String[] args) {
		EmbeddedJettyServer thisServer = new EmbeddedJettyServer();
		try {
			if (args.length > 0) {

				if (args[0].equals("start")) {

					thisServer.startServer();
					InitialDataSetupService initialDataSetupService = new InitialDataSetupService();
					initialDataSetupService.loadDataFromJsonIntoDatabase();
					
				} else if (args[0].equals("stop")) {

					thisServer.stopServer();

				} else {
					System.out.println("invalid arguments. Either enter start or stop without spaces");
				}
			}
		} catch (Exception e) {

		}
	}

}
