import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Hashtable;
import java.util.LinkedList;

public class Company {

	// PRIVATE VARIABLES //
	public final static int MAXIDCHARS = 5;
	public final static int MINIDCHARS = 1;
	private String id;
	protected Hashtable<String, Transportation> myTransports;
	
	// CONSTRUCTOR //
	public Company(String idArg) throws ManagementException
	{
		//Check to make sure the string is not null
		if(idArg == null)
		{
			throw new ManagementException("You have attempted to create a " + getClass().getName() + ", but the name is null.");
		}
		
		//Convert all names to upper case
		idArg = idArg.toUpperCase();

		//Do the name check
		boolean nameIsOK = checkId(idArg);
		
		if(!nameIsOK)
		{
			throw new ManagementException(getClass().getName() + " name must be between " + MINIDCHARS + " and "
				+ MAXIDCHARS + " (inclusive) alphabetic characters, name is length "
				+ idArg.length() + " for " + idArg + ".");
		}

		//If all of the above went OK, assign values
		id = idArg;
		myTransports = new Hashtable<String, Transportation>();
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

	//Find available transportation
	public LinkedList<Transportation> findAvailableTransport(TransportStation origin, TransportStation destination) throws ManagementException
	{
		//Check if the Transports are null
		if(origin == null || destination == null)
		{
			throw new ManagementException("You are attempting to find flights from one airport to another, but either the destination or origin airport is null, or both. Origin: "
+ origin + ", Destination: " + destination);
		}

		//Create an empty list of acceptable transports and convert the transport hash to linked list for easy iteration
		LinkedList<Transportation> acceptableTransList = new LinkedList<Transportation>();
		LinkedList<Transportation> allTransList = SystemManager.hashtableToLinkedList(myTransports);

		for(Transportation currentTransport : allTransList)
		{
			//If the current flight matches the given criteria, add it into the list of acceptable flights
			if(currentTransport.getOrigin() == origin && currentTransport.getDestination() == destination /*&& currentTransport.hasAvailableBed()*/)
			{			
				acceptableTransList.add(currentTransport);
			}
		}

		return acceptableTransList;
	}
	
	public HashTable<String, Transportation> getTransports() {
		return myTransports;
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
