import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Transportation {
	//Define the maximum and minimum number of ID characters,
	public final static int MAXIDCHARS = 6;
	public final static int MINIDCHARS = 3;
	
	//All flights must be scheduled after this day
	public final static int AFTERYEAR = 2012;
	public final static int AFTERMONTH = 12;
	public final static int AFTERDAY = 31;
	public final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	private String id;
	protected Company owner;
	protected TransportStation origin;
	protected TransportStation destination;
	protected Calendar date;
	protected LinkedList<Partition> myPartitions; 
	
	// CONSTRUCTOR //
	public Transportation(Company ownerArg, TransportStation originArg, TransportStation destinationArg, Calendar dateArg, String idArg) throws ManagementException
	{
		//System.out.print("INSIDE TRANSPORT CONSTER");
		//Check to see if the string is null
		if(idArg == null)
		{
			throw new ManagementException("You are attempting to create a "+ getClass().getName() + " " + "with a null ID. All " + getClass().getName() + "s " + "must have IDs.");
		}
		idArg = idArg.toUpperCase();
		boolean nameIsOK = checkId(idArg);
		
		if(!nameIsOK)
		{
			throw new ManagementException(getClass().getName() + " ID must have between " + MINIDCHARS + " and " + MAXIDCHARS + " alphanumeric characters, the length is " 
					+ idArg.length() + " for ID " + idArg);
		}

		//Assign id and owner so that calls to this.toString() can resolve.
		id = idArg;
		
		if(ownerArg == null)
		{
			throw new ManagementException("You are attempting to create "+ getClass().getName() + " " + idArg + ", but no owner was given.");
		}
		owner = ownerArg;
		
		//Check if airline, airport and date objects are null
		if(originArg == null)
		{
			throw new ManagementException("You are attempting to create " + this.toString() + ", but no origin was given.");
		}
		if(destinationArg == null)
		{
			throw new ManagementException("You are attempting to create " + this.toString() + ", but no destination was given.");
		}
		if(dateArg == null)
		{
			throw new ManagementException("You are attempting to create " + this.toString() + ", but no date was given.");
		}
		
		//Flights cannot have the same origin and destination.
		if(originArg == destinationArg)
		{
			throw new ManagementException(this.toString() + " has an origin and destination at " + originArg 
					+". Transportation cannot start and end at the same Location.");
		}
		
		//Check that the flight falls after the allowed start date
		Calendar earliestAllowedDate = Calendar.getInstance();
		earliestAllowedDate.set(AFTERYEAR, AFTERMONTH, AFTERDAY);
		if(!dateArg.after(earliestAllowedDate))
		{
			throw new ManagementException(getClass().getName()+"s must be scheduled after " + simpleDateFormat.format(earliestAllowedDate.getTime()) 
					+", " + this.toString() + " is scheduled to depart on " + simpleDateFormat.format(dateArg.getTime()));
		}
		
		//Assignments
		origin = originArg;
		destination = destinationArg;
		date = dateArg;	
		
		owner.addTransport(this);
	}
	
	
	
	
	// METHODS //
	//Performs a check on the ID.
	private boolean checkId(String idArg) throws ManagementException
	{
		boolean nameIsOK = true;
		
		//Use a regex to ensure that only alphanumeric characters are in the ID
		Pattern p = Pattern.compile(".*\\W+.*");
		Matcher m = p.matcher(idArg);
		boolean containsNonAlphanumeric = m.matches();
		
		if(idArg.length() < MINIDCHARS || idArg.length() > MAXIDCHARS)
		{
			nameIsOK = false;
		}
		if(containsNonAlphanumeric)
		{
			nameIsOK = false;		
		}
		
		return nameIsOK;
	}
	
	public String toString()
	{
		return owner.toString() + " "+ getClass().getName() + " " + id;
	}
	
	public String getId()
	{
		return id;
	}
	
	public Company getOwner(){
		return owner;
	}
	
	public Calendar getDate(){
		return date;
	}
	
	public String getDateString(){
		return simpleDateFormat.format(date.getTime());
	}
	
	public TransportStation getOrigin() {
		return origin;
	}

	public TransportStation getDestination() 
	{
		return destination;
	}
	
	public LinkedList<Partition> getPartitions(){
		return myPartitions;
	}

}
