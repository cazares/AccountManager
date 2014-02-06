package accountManager.main;

import accountManager.controller.AcctListController;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This is the main class that includes the main method for the account manager project (hw # 2) in CS4354
 * 
 * @author Miguel Cazares
 */
public class Main {

	/**
	 * This is the main method that starts the acctManager
	 * @param inputFileString
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException{
		final int MIN_ARGS = 1;
		
		if (args.length >= MIN_ARGS) {
			String inputFile = args[0];
			new AcctListController(inputFile); 
			//AcctAgentStartView acctAgent = new AcctAgentStartView();
			//acctAgent.genAgentView();
		}
		else {
			System.err.println("Must provide at least " + MIN_ARGS + " argument(s)!");
			System.err.println("...Exiting application...");
			System.exit(1);
		}
	}
}
