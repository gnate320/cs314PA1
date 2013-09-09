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
	private Company owner;
	private TransportStation origin;
	private TransportStation destination;
	private Calendar date;
	
	// CONSTRUCTOR //
	
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

}
