package accountManager.model;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import accountManager.view.AcctView;

/**
 * AcctListModel holds information and functionality for multiple accounts
 * @author Miguel Cazares
 *
 */
public class AcctListModel extends AbstractModel{
	private String ioFileName;
	public static final double EUR_RT = 0.79;
	public static final double YEN_RT = 94.1;
	private static List<AcctModel> accts = new ArrayList<AcctModel>();
	// used to check uniqueness of incoming account ID
	private static List<Integer> idList = new ArrayList<Integer>();
	
	public AcctModel getAccount(int index) { return accts.get(index);}
	public int getNumAccts(){ return accts.size(); }
	public String[] getNameAndIDList() { 
		String[] names = new String[getNumAccts()];
		for(int i=0; i < getNumAccts(); i++){
			names[i] = getAccount(i).getName() + " " + getAccount(i).getID();
		}
		return names;
	}

	/**
	 * Constructor reads in account data from inputfile
	 * @param inputFile
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws AcctException
	 * @throws CriticalException
	 */
	@SuppressWarnings("deprecation")
	public AcctListModel(String inputFile) throws FileNotFoundException, IOException, AcctException, CriticalException{
		ioFileName = inputFile;
	    File file = new File(inputFile);
	    FileInputStream fis = null;
	    BufferedInputStream bis = null;
	    DataInputStream dis = null;
	    double acctBalance;
	    int id;
	    String idStr;
	    String name;

	    try {
	      fis = new FileInputStream(file);

	      // Here BufferedInputStream is added for fast reading.
	      bis = new BufferedInputStream(fis);
	      dis = new DataInputStream(bis);
	      int lineNum = 0;
	      
	      System.out.println("Input/Output file: " + inputFile + "\n");
	      // dis.available() returns 0 if the file does not have more lines.
	      while (dis.available() != 0) {

	      // this statement reads the line from the file and print it to
	        // the console.
	    	String line = dis.readLine();
	    	if(line.length() < 4)
	    		break;
	    	String[] tokens = line.split("[;]");
	    	System.out.println("Account " + (lineNum+1) + "\n---------");
	    	for(int i=0; i < tokens.length; i++){
	    		System.out.println(tokens[i]);
	    	}

	    	String excMsg = "";
	    	if(!matchesNameFormat(tokens[0]))
	    		excMsg += "Account name " + tokens[0] + " must only contain letters!\n";
	    	if(!matchesIDFormat(tokens[1]))
	    		excMsg += "Account ID " + tokens[1] + " must only contain digits!\n";
	    	if(!matchesNumFormat(tokens[2].trim()))
	    		excMsg += "Initial Account Balance " + tokens[2] + " must only contain digits in format # or #.##";
	    	
	    	if(!excMsg.equals("")){
	    		throw new AcctException(excMsg);
	    	}
	    	
	    	name = tokens[0];
	    	idStr = tokens[1];
	    	id = Integer.parseInt(tokens[1]);
	    	acctBalance = Double.parseDouble(tokens[2]);
	  
	    	if(acctBalance >= 0.0){
	    		if(!idList.contains(id)){
	    			idList.add(id);
	    			this.insertAcct(new AcctModel(name, idStr, acctBalance));
	    		}
	    		else{
	    			System.out.println("An account with ID " + id + " already exists... ignoring account.");
	    		}
	    	}
	    	else{
	    		System.out.println("Account balance of " + acctBalance + " is less than 0");
	    		System.out.println("Ignoring account with Name and ID: " + name + " " + id);
	    		//throw new AcctException("Account balance of " + acctBalance + " is less than 0... ignoring account with ID " + id);
	    	}
	    	
	    	lineNum++;
	    	System.out.println();
	    	
	      }

	      // dispose all the resources after using them.
	      fis.close();
	      bis.close();
	      dis.close();

	    } catch (FileNotFoundException e) {
	      System.err.println("FileNotFoundException: " + e.getMessage());
	      System.err.println("File was not found, exiting system...");
	      throw new CriticalException("FileNotFoundException: " + e.getMessage());
	    } catch (IOException e) {
	      System.err.println("IOException: " + e.getMessage());
	      System.err.println("Input could not be read, exiting system...");
	      throw new CriticalException("IOException: " + e.getMessage());
	    }
	}
	
	/**
	 * This private helper method inserts an account in increasing order of ID to account list
	 * @param acct - account to be added to account list
	 */
	private void insertAcct(AcctModel acct){
		int acctID = Integer.parseInt(acct.getID());
		int compID;
		
		for(int i=0; i < getNumAccts(); i++){
			compID = Integer.parseInt(getAccount(i).getID());
			if(acctID < compID){
				accts.add(i, acct);
				return;
			}
		}
		// if you didn't add in the loop, means account ID is highest, append to list
		accts.add(acct);
	}
	
	/**
	 * method saves current state of model back to input file (ioFile)
	 */
	public void saveModel(){
		// this section of code writes results to output file specified as command-line argument
		File outFile = new File(ioFileName);
		try{
			PrintWriter output = new PrintWriter(outFile);
			DecimalFormat df = new DecimalFormat("0.00");
			
			for(int i=0; i < getNumAccts(); i++){
				output.println(getAccount(i).getName() + ";" + getAccount(i).getID() + ";" + df.format(getAccount(i).getBal(AcctView.DOL)));
			}
			// close output file
			output.close();
		} // end try-block
		// catches exception if generated
		catch(IOException exc){
			System.err.println(exc.getMessage());
		} // end catch-block
	}
	
	/**
	 * Helper method to determine if input name matches correct format
	 * @param name
	 * @return boolean
	 */
	private boolean matchesNameFormat(String name){
		Pattern patternName = Pattern.compile("^[a-zA-Z]+$ ^[a-zA-Z]+$");
		Matcher matcherName = patternName.matcher(name);
		
		return !matcherName.matches();
	}
	
	/**
	 * Helper method to determine if id matches correct format
	 * @param id
	 * @return boolean
	 */
	private boolean matchesIDFormat(String id){		
		Pattern patternID = Pattern.compile("([0-9]*)");
		Matcher matcherID = patternID.matcher(id);
		
		return matcherID.matches();
	}

	/**
	 * Helper method to determine if initial balance matches correct format
	 * @param amount
	 * @return boolean
	 */
	private boolean matchesNumFormat(String amount){
		Pattern patternDouble = Pattern.compile("([0-9]*)\\.([0-9]*)");
		Matcher matcherDouble = patternDouble.matcher(amount);
		
		Pattern patternInt = Pattern.compile("([0-9]*)");
		Matcher matcherInt = patternInt.matcher(amount);
		
		Pattern patternNegDouble = Pattern.compile("-([0-9]*)\\.([0-9]*)");
		Matcher matcherNegDouble = patternNegDouble.matcher(amount);
		
		Pattern patternNegInt = Pattern.compile("-([0-9]*)");
		Matcher matcherNegInt = patternNegInt.matcher(amount);
		
		return matcherDouble.matches() || matcherInt.matches() || matcherNegInt.matches() || matcherNegDouble.matches();
	}
}
