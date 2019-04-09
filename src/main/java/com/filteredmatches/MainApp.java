package com.filteredmatches;
import com.filteredmatches.server.EmbeddedJettyServer;
import com.filteredmatches.service.InitialDataSetupService;

public class MainApp {
	
	private static EmbeddedJettyServer thisServer = new EmbeddedJettyServer();
	
	public static void main(String[] args) {
		
		try {
			if (args.length > 0) {
				startOrStopApp(args[0]);
			}else 
			    System.out.println("invalid arguments. Either enter start or stop without spaces");
		} catch (Exception e) {

		}
	}

	public static void startOrStopApp(String arg) throws Exception {
		
		if (arg.equals("start")) {

			thisServer.startServer();
			InitialDataSetupService initialDataSetupService = new InitialDataSetupService();
			initialDataSetupService.loadDataFromJsonIntoDatabase();
			
		} else if (arg.equals("stop")) {

			thisServer.stopServer();

		} else {
			System.out.println("invalid arguments. Either enter start or stop without spaces");
		}
	}

	public static String getServerURI() {
		
		return thisServer.getServerURI();
	}

}
