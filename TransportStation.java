import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class TransportStation {
	// PRIVATE VARIABLES //
	public final static int MAXIDCHARS = 3;
	public final static int MINIDCHARS = 3;
	private String id;
	
	// CONSTRUCTOR //
	
	
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
