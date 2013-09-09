import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Hashtable;
import java.util.LinkedList;

public class Company {

	// PRIVATE VARIABLES //
	public final static int MAXIDCHARS = 5;
	public final static int MINIDCHARS = 1;
	private String id;
	private Hashtable<String, Transportation> myTransports;
	
	// CONSTRUCTOR //
	public Company(String idArg) throws ManagementException
	{
		//Check to make sure the string is not null
		if(idArg == null)
		{
			throw new ManagementException("You have attempted to create an company, but the name is null.");
		}
		
		//Convert all names to upper case
		idArg = idArg.toUpperCase();

		//Do the name check
		boolean nameIsOK = checkId(idArg);
		
		if(!nameIsOK)
		{
			throw new ManagementException("Company name must be between " + MINIDCHARS + " and "
				+ MAXIDCHARS + " (inclusive) alphabetic characters, name is length "
				+ idArg.length() + " for " + idArg + ".");
		}

		//If all of the above went OK, assign values
		id = idArg;
		myTransports = new Hashtable<String, Transportaion>();
	}
	
	// METHODS //
	
	//Performs a check on the ID.
	private boolean checkId(String nameArg) throws ManagementException
	{
		boolean nameIsOK = true;
		
		//Check that length of name is in bounds
		if(nameArg.length() < MINIDCHARS || nameArg.length() > MAXIDCHARS )
		{
			nameIsOK = false;
		}
		
		//Use a regex to ensure that only alphabetic characters are in the name
		Pattern p = Pattern.compile("^A-Z");
		Matcher m = p.matcher(nameArg);
		boolean containsNonAlphabetic = m.matches();
		
		if(containsNonAlphabetic)
		{
			nameIsOK = false;
		}
		
		return nameIsOK;
	}
	
	public String toString()
	{
		return id;
	}
	
	public String getId() 
	{
		return id;
	}
}
